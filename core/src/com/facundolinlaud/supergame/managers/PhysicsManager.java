package com.facundolinlaud.supergame.managers;

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
import com.facundolinlaud.supergame.factory.BodyFactory;
import com.facundolinlaud.supergame.helper.Dimensions;

import java.util.Iterator;

/**
 * Created by facundo on 3/22/16.
 */
public class PhysicsManager {
    public static final String COLLISION_LAYER = "collision";

    private Camera camera;
    private TiledMap map;

    private World world;
    private BodyFactory bodyFactory;
    private Box2DDebugRenderer physicsDebugRenderer;

    public PhysicsManager(Camera camera, TiledMap map) {
        this.camera = camera;
        this.map = map;

        initialize();
        populateObstacles();
    }

    private void initialize(){
        boolean doSleep = true;
        world = new World(new Vector2(0, 0), doSleep);
        physicsDebugRenderer = new Box2DDebugRenderer();
        bodyFactory = new BodyFactory(world);
    }

    private void populateObstacles(){
        MapLayer layer = map.getLayers().get(COLLISION_LAYER);
        MapObjects objects = layer.getObjects();
        Iterator<MapObject> objectIt = objects.iterator();

        while(objectIt.hasNext()) {
            RectangleMapObject object = (RectangleMapObject) objectIt.next();
            Rectangle rectangle = object.getRectangle();

            float width = rectangle.getWidth() / Dimensions.ONE_METER_IN_PIXELS;
            float height = rectangle.getHeight() / Dimensions.ONE_METER_IN_PIXELS;

            float x = (rectangle.getX() / Dimensions.ONE_METER_IN_PIXELS) + width / 2;
            float y = (rectangle.getY() / Dimensions.ONE_METER_IN_PIXELS) + height / 2;

            bodyFactory.createObstacle(x, y, width, height);
        }
    }

    public void render(){
        physicsDebugRenderer.render(world, camera.combined);
    }

    public World getWorld(){
        return this.world;
    }
}
