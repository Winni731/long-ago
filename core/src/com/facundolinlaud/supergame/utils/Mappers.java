package com.facundolinlaud.supergame.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.facundolinlaud.supergame.components.*;
import com.facundolinlaud.supergame.components.items.EquipableComponent;
import com.facundolinlaud.supergame.components.items.ItemComponent;
import com.facundolinlaud.supergame.components.player.BagComponent;
import com.facundolinlaud.supergame.components.player.KeyboardComponent;
import com.facundolinlaud.supergame.components.player.WearComponent;
import com.facundolinlaud.supergame.refactor.AnimableSpriteComponent;
import com.facundolinlaud.supergame.refactor.StackableSpriteComponent;
import com.facundolinlaud.supergame.refactor.StackedSpritesComponent;
import com.facundolinlaud.supergame.refactor.StatusComponent;
import com.facundolinlaud.supergame.refactorno.InputComponent;

/**
 * Created by facundo on 3/17/16.
 */
public class Mappers {
    public static final ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<VelocityComponent> velocity = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<RenderComponent> render = ComponentMapper.getFor(RenderComponent.class);
//    public static final ComponentMapper<InputComponent> input = ComponentMapper.getFor(InputComponent.class);
    public static final ComponentMapper<StatusComponent> status = ComponentMapper.getFor(StatusComponent.class);
    public static final ComponentMapper<KeyboardComponent> keyboard = ComponentMapper.getFor(KeyboardComponent.class);
    public static final ComponentMapper<BodyComponent> body = ComponentMapper.getFor(BodyComponent.class);
    public static final ComponentMapper<HealthComponent> health = ComponentMapper.getFor(HealthComponent.class);
    public static final ComponentMapper<ItemComponent> item = ComponentMapper.getFor(ItemComponent.class);
    public static final ComponentMapper<BagComponent> bag = ComponentMapper.getFor(BagComponent.class);
    public static final ComponentMapper<AttributesComponent> attributes = ComponentMapper.getFor(AttributesComponent.class);
    public static final ComponentMapper<WearComponent> wear = ComponentMapper.getFor(WearComponent.class);
    public static final ComponentMapper<EquipableComponent> equipable = ComponentMapper.getFor(EquipableComponent.class);

    public static final ComponentMapper<StackableSpriteComponent> stackableSprite = ComponentMapper.getFor(StackableSpriteComponent.class);
    public static final ComponentMapper<StackedSpritesComponent> stackedSprites = ComponentMapper.getFor(StackedSpritesComponent.class);
    public static final ComponentMapper<AnimableSpriteComponent> animableSprite = ComponentMapper.getFor(AnimableSpriteComponent.class);

//    public static final ComponentMapper<AnimationComponent> animation = ComponentMapper.getFor(AnimationComponent.class);
//    public static final ComponentMapper<SpriteComponent> sprite = ComponentMapper.getFor(SpriteComponent.class);
//    public static final ComponentMapper<SpriteStackableComponent> spriteStack = ComponentMapper.getFor(SpriteStackableComponent.class);
}
