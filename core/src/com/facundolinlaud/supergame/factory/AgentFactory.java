package com.facundolinlaud.supergame.factory;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.facundolinlaud.supergame.builder.AgentBuilder;
import com.facundolinlaud.supergame.components.sprite.StackableSpriteComponent;
import com.facundolinlaud.supergame.model.agent.Agent;
import com.facundolinlaud.supergame.model.equip.EquipSlot;
import com.facundolinlaud.supergame.model.particle.ParticleType;

import java.util.*;

public class AgentFactory {
    private static final Integer MAIN_PLAYER_ID = 0;

    private Engine engine;
    private ItemFactory itemFactory;
    private ParticleFactory particleFactory;
    private Map<Integer, Agent> agents;

    public AgentFactory(Engine engine, ItemFactory itemFactory, ParticleFactory particleFactory) {
        this.engine = engine;
        this.agents = ModelFactory.getAgentsModel();
        this.itemFactory = itemFactory;
        this.particleFactory = particleFactory;
    }

    private AgentBuilder getDummyAgent(Agent agent){
        Map<EquipSlot, Entity> equipment = buildEquipment(agent.getBody(), agent.getEquipment());

        AgentBuilder builder = new AgentBuilder(agent.getVelocity())
                .withAttributes(agent.getAttributes())
                .withEquipment(equipment);

        return builder;
    }

    public AgentBuilder getAI(int id){
        Agent agent = agents.get(id);
        return getDummyAgent(agent)
                .withAI(agent.getNpcInformation())
                .withParticles(particleFactory.create(ParticleType.SPAWN));
    }

    public AgentBuilder getPlayer(){
        Agent agent = agents.get(MAIN_PLAYER_ID);

        return getDummyAgent(agent)
                .withBag(buildBag(agent.getBag()))
                .withKeyboardControl();
    }

    private Map<EquipSlot, Entity> buildEquipment(Map<EquipSlot, String> body, Map<EquipSlot, Integer> model) {
        Map<EquipSlot, Entity> equipment = new HashMap();

        for(Map.Entry<EquipSlot, String> entry : body.entrySet()){
            Entity overlay = new Entity()
                    .add(new StackableSpriteComponent(TextureFactory.getTexture(entry.getValue())));

            equipment.put(entry.getKey(), overlay);
        }

        for(Map.Entry<EquipSlot, Integer> entry : model.entrySet()){
            Entity item = itemFactory.getItem(entry.getValue()).build();
            equipment.put(entry.getKey(), item);
            engine.addEntity(item);
        }

        return equipment;
    }

    private List<Entity> buildBag(List<Integer> model) {
        List<Entity> bag = new LinkedList();

        for(Integer id : model) {
            Entity item = itemFactory.getItem(id).build();
            engine.addEntity(item);
            bag.add(item);
        }

        return bag;
    }
}
