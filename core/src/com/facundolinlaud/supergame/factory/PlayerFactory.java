package com.facundolinlaud.supergame.factory;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.facundolinlaud.supergame.components.*;
import com.facundolinlaud.supergame.components.ai.AIComponent;
import com.facundolinlaud.supergame.components.items.EquipableComponent;
import com.facundolinlaud.supergame.components.items.ItemComponent;
import com.facundolinlaud.supergame.components.player.BagComponent;
import com.facundolinlaud.supergame.components.player.KeyboardComponent;
import com.facundolinlaud.supergame.components.player.WearComponent;
import com.facundolinlaud.supergame.components.sprite.AnimableSpriteComponent;
import com.facundolinlaud.supergame.components.sprite.RefreshSpriteRequirementComponent;
import com.facundolinlaud.supergame.components.sprite.StackableSpriteComponent;
import com.facundolinlaud.supergame.components.sprite.StackedSpritesComponent;
import com.facundolinlaud.supergame.model.RenderPriority;
import com.facundolinlaud.supergame.model.ai.EnemyModel;
import com.facundolinlaud.supergame.model.entity.ItemModel;
import com.facundolinlaud.supergame.model.entity.PlayerModel;
import com.facundolinlaud.supergame.model.equip.EquipSlot;
import com.facundolinlaud.supergame.strategies.renderposition.SpriteRenderPositionStrategyImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by facundo on 27/7/16.
 */
public class PlayerFactory {
    private static Body playerBody;

    public static void createPlayer(Engine engine){
        playerBody = PhysicsFactory.get().createItemBody();
        PlayerModel playerModel = ModelFactory.getPlayerModel();

        Entity player = new Entity()
                .add(new PositionComponent(playerModel.getX(), playerModel.getY()))
                .add(new KeyboardComponent())
                .add(new VelocityComponent(playerModel.getVelocity()))
                .add(new BodyComponent(playerBody))
                .add(new WearComponent(createWearablesEntities(engine, playerModel)))
                .add(new RenderComponent(new SpriteRenderPositionStrategyImpl()))
                .add(new StatusComponent())
                .add(new AnimableSpriteComponent())
                .add(new StackedSpritesComponent(ModelFactory.getDefaultAnimationModel()))
                .add(new RefreshSpriteRequirementComponent())
                .add(new HealthComponent())
                .add(new BagComponent(createItems(engine, playerModel.getInventory())))
                .add(new AttributesComponent());

        engine.addEntity(player);
    }

    private static Map<EquipSlot, Entity> createWearablesEntities(Engine engine, PlayerModel playerModel){
        Map<EquipSlot, ItemModel> wearablesModels = playerModel.getEquipment();
        Map<EquipSlot, String> bodyModels = playerModel.getBody();
        HashMap<EquipSlot, Entity> wearables = new HashMap<>();

        for(EquipSlot equipSlot : bodyModels.keySet()){
            String texture = bodyModels.get(equipSlot);
            Entity e = new Entity().add(new StackableSpriteComponent(TextureFactory.get(texture)));

            wearables.put(equipSlot, e);
            engine.addEntity(e);
        }

        for(EquipSlot equipSlot : wearablesModels.keySet()){
            Entity e = createItemEntity(wearablesModels.get(equipSlot));

            wearables.put(equipSlot, e);
            engine.addEntity(e);
        }

        return wearables;
    }

    private static List<Entity> createItems(Engine engine, List<ItemModel> itemsModels){
        List<Entity> itemsList = new ArrayList();

        for(ItemModel m : itemsModels){
            Entity item = createItemEntity(m);

            itemsList.add(item);
            engine.addEntity(item);
        }

        return itemsList;
    }

    private static Entity createItemEntity(ItemModel itemModel){
        Entity e = new Entity()
           .add(new ItemComponent(itemModel.getName(), itemModel.getPicture()))
           .add(new RenderComponent(new TextureRegion(TextureFactory.get(itemModel.getPicture())), new RenderPriority(1)));

        if(itemModel.isEquipable()) {
            e.add(new EquipableComponent(itemModel.getEquipSlot(), itemModel.getEquipType(), itemModel.getAttack(), itemModel.getDefense()))
             .add(new StackableSpriteComponent(TextureFactory.get(itemModel.getTexture())));
        }

        return e;
    }

    public static Entity createEnemy(Engine engine, float x, float y) {
        EnemyModel enemyModel = ModelFactory.getEnemyModel();

        Entity e = new Entity()
                .add(new RenderComponent(new SpriteRenderPositionStrategyImpl()))
                .add(new PositionComponent(x, y))
                .add(new HealthComponent(100, 99))
                .add(new BodyComponent(PhysicsFactory.get().createItemBody()))
                .add(new StatusComponent())
                .add(new VelocityComponent(enemyModel.getVelocity()))
                .add(new AnimableSpriteComponent())
                .add(new StackedSpritesComponent(ModelFactory.getDefaultAnimationModel()))
                .add(new RefreshSpriteRequirementComponent())
                .add(new WearComponent(createWearablesEntities(engine, enemyModel)))
                .add(new AIComponent(enemyModel.getViewDistance()));

        engine.addEntity(e);

        return e;
    }

    public static Body getPlayerBody() {
        return playerBody;
    }
}
