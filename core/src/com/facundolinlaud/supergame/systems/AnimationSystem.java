package com.facundolinlaud.supergame.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.facundolinlaud.supergame.components.InputComponent;
import com.facundolinlaud.supergame.components.RenderComponent;
import com.facundolinlaud.supergame.components.AnimationComponent;
import com.facundolinlaud.supergame.components.SpriteComponent;
import com.facundolinlaud.supergame.components.player.WearComponent;
import com.facundolinlaud.supergame.utils.AnimationHelper;
import com.facundolinlaud.supergame.utils.AnimationType;
import com.facundolinlaud.supergame.utils.Mappers;
import com.facundolinlaud.supergame.utils.strategy.impl.SpriteRenderPositionStrategyImpl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by facundo on 3/31/16.
 */
public class AnimationSystem extends IteratingSystem {
    private static final float FRAME_DURATION = 0.1f;

    private ComponentMapper<AnimationComponent> am = Mappers.animation;
    private ComponentMapper<RenderComponent> rm = Mappers.render;
    private ComponentMapper<InputComponent> im = Mappers.input;
    private ComponentMapper<WearComponent> wm = Mappers.wear;
    private ComponentMapper<SpriteComponent> sm = Mappers.sprite;

    public AnimationSystem() {
        super(Family.all(AnimationComponent.class, WearComponent.class, InputComponent.class, RenderComponent.class).get());
    }

    private void initialize(Entity entity) {
        long start = System.currentTimeMillis();

        WearComponent wearComponent = wm.get(entity);
        AnimationComponent animationComponent = am.get(entity);

        List<Texture> wearablesTextures = wearComponent
            .asList()
            .stream()
            .map(e -> sm.get(e).texture)
            .collect(Collectors.toList());

        animationComponent.setAnimations(AnimationHelper.texturesToFrames(wearablesTextures, FRAME_DURATION));
        animationComponent.setCurrentType(AnimationType.WALKING_RIGHT);

        System.out.println("Sprite rendered in " + (System.currentTimeMillis() - start) + " ms");
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent animation  = am.get(entity);

        if(!animation.isInitialized()) initialize(entity);

        RenderComponent render = rm.get(entity);
        InputComponent input = im.get(entity);

        boolean shouldAnimate = true;

        if(input.direction.x > 0){
            animation.setCurrentType(AnimationType.WALKING_RIGHT);
        }else if(input.direction.x < 0){
            animation.setCurrentType(AnimationType.WALKING_LEFT);
        }else if(input.direction.y > 0){
            animation.setCurrentType(AnimationType.WALKING_UP);
        }else if(input.direction.y < 0){
            animation.setCurrentType(AnimationType.WALKING_DOWN);
        }else{
            shouldAnimate = false;
        }

        animation.animate(shouldAnimate);
        render.texture = animation.getCurrentTextureAndTick(deltaTime);
    }
}
