package com.facundolinlaud.supergame.managers.world;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.facundolinlaud.supergame.factory.PhysicsFactory;
import com.facundolinlaud.supergame.managers.Renderable;
import com.facundolinlaud.supergame.utils.Dimensions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by facundo on 3/22/16.
 */
public class PhysicsManager implements Renderable {
    public static final String COLLISION_LAYER = "collision";
    public static final boolean DEBUG = false;

    private Camera camera;
    private TiledMap map;

    private World world;
    private PhysicsFactory physicsFactory;
    private Box2DDebugRenderer physicsDebugRenderer;

    private List<Rectangle> obstacles;

    public PhysicsManager(Camera camera, TiledMap map) {
        this.camera = camera;
        this.map = map;
        this.obstacles = new ArrayList<>();

        initialize();
        populateObstacles();
    }

    private void initialize(){
        boolean doSleep = true;
        world = new World(new Vector2(0, 0), doSleep);
        physicsDebugRenderer = new Box2DDebugRenderer();
        physicsFactory = PhysicsFactory.get();
        physicsFactory.setWorld(world);
    }

    private void populateObstacles(){
        MapLayer layer = map.getLayers().get(COLLISION_LAYER);
        MapObjects objects = layer.getObjects();
        Iterator<MapObject> objectIt = objects.iterator();

        while(objectIt.hasNext()) {
            RectangleMapObject object = (RectangleMapObject) objectIt.next();
            Rectangle rectangle = object.getRectangle();

            float width = rectangle.getWidth() / Dimensions.PX_PER_METER;
            float height = rectangle.getHeight() / Dimensions.PX_PER_METER;

            float x = (rectangle.getX() / Dimensions.PX_PER_METER);
            float y = (rectangle.getY() / Dimensions.PX_PER_METER);

            obstacles.add(new Rectangle(x, y, width, height));

            float x_offset = x + width / 2;
            float y_offset = y + height / 2;

            physicsFactory.createObstacleBody(x_offset, y_offset, width, height);
        }
    }

    @Override
    public void render(){
        if(DEBUG)
            physicsDebugRenderer.render(world, camera.combined);
    }

    public World getWorld(){
        return this.world;
    }

    public List<Rectangle> getObstacles() {
        return obstacles;
    }
}
