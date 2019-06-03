package com.facundolinlaud.supergame.builder;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.facundolinlaud.supergame.components.*;
import com.facundolinlaud.supergame.components.ai.AIComponent;
import com.facundolinlaud.supergame.components.player.BagComponent;
import com.facundolinlaud.supergame.components.player.KeyboardComponent;
import com.facundolinlaud.supergame.components.player.WearComponent;
import com.facundolinlaud.supergame.components.sprite.AnimableSpriteComponent;
import com.facundolinlaud.supergame.components.sprite.RefreshSpriteRequirementComponent;
import com.facundolinlaud.supergame.components.sprite.StackedSpritesComponent;
import com.facundolinlaud.supergame.factory.ModelFactory;
import com.facundolinlaud.supergame.factory.PhysicsFactory;
import com.facundolinlaud.supergame.model.agent.Attributes;
import com.facundolinlaud.supergame.model.equip.EquipSlot;
import com.facundolinlaud.supergame.model.particle.ParticleType;
import com.facundolinlaud.supergame.strategies.renderposition.SpriteRenderPositionStrategyImpl;

import java.util.List;
import java.util.Map;

public class AgentBuilder {
    public static final int HEALTH_PER_STAMINA_POINT = 10;

    private Entity entity;

    public AgentBuilder(float velocity) {
        this.entity = new Entity();

        this.entity.add(new RenderComponent(new SpriteRenderPositionStrategyImpl()))
            .add(new BodyComponent(PhysicsFactory.get().createItemBody(), this.entity))
            .add(new StatusComponent())
            .add(new AnimableSpriteComponent())
            .add(new StackedSpritesComponent(ModelFactory.getDefaultAnimationModel()))
            .add(new RefreshSpriteRequirementComponent())
            .add(new VelocityComponent(velocity));
    }

    public AgentBuilder withAI(float viewDistance){
        this.entity.add(new AIComponent(viewDistance));
        return this;
    }

    public AgentBuilder withHealth(float total, float current){
        this.entity.add(new HealthComponent(total, current));
        return this;
    }

    public AgentBuilder withEquipment(Map<EquipSlot, Entity> equipment){
        this.entity.add(new WearComponent(equipment));
        return this;
    }

    public AgentBuilder withAttributes(Attributes attr){
        int stamina = attr.getStamina();
        float health = stamina * HEALTH_PER_STAMINA_POINT;

        this.entity.add(new AttributesComponent(
                attr.getAgility(),
                attr.getStrength(),
                attr.getIntelligence(),
                stamina));

        return this.withHealth(health, health);
    }

    public AgentBuilder withBag(List<Entity> bag){
        this.entity.add(new BagComponent(bag));
        return this;
    }

    public AgentBuilder withKeyboardControl(){
        this.entity.add(new KeyboardComponent());
        return this;
    }

    public AgentBuilder at(float x, float y){
        this.entity.add(new PositionComponent(x, y));
        return this;
    }

    public AgentBuilder withParticles(ParticleEffectPool.PooledEffect pooledEffect){
        this.entity.add(new ParticleComponent(pooledEffect, false));
        return this;
    }

    public Entity build(){
        return this.entity;
    }

}
