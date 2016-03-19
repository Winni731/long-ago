package com.facundolinlaud.supergame.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.facundolinlaud.supergame.components.InputComponent;
import com.facundolinlaud.supergame.components.PositionComponent;
import com.facundolinlaud.supergame.components.VelocityComponent;
import com.facundolinlaud.supergame.helper.InputHelper;
import com.facundolinlaud.supergame.helper.Mappers;

/**
 * Created by facundo on 3/17/16.
 */
public class InputMovementSystem extends IteratingSystem  {
    private ComponentMapper<PositionComponent> position = Mappers.position;
    private ComponentMapper<VelocityComponent> velocity = Mappers.velocity;
    private ComponentMapper<InputComponent> input = Mappers.input;


    public InputMovementSystem() {
        super(Family.all(InputComponent.class, PositionComponent.class, VelocityComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position= this.position.get(entity);
        VelocityComponent velocity = this.velocity.get(entity);
        InputComponent input = this.input.get(entity);

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            input.direction.x = InputHelper.LEFT;
        }else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            input.direction.x = InputHelper.RIGHT;
        }else{
            input.direction.x = InputHelper.NONE;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            input.direction.y = InputHelper.DOWN;
        }else if(Gdx.input.isKeyPressed(Input.Keys.W)){
            input.direction.y = InputHelper.UP;
        }else{
            input.direction.y = InputHelper.NONE;
        }

        position.x += input.direction.x * (velocity.velocity * (deltaTime + 1));
        position.y += input.direction.y * (velocity.velocity * (deltaTime + 1));
    }
}
