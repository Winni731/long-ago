package com.facundolinlaud.supergame.factory;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.facundolinlaud.supergame.components.*;
import com.facundolinlaud.supergame.components.items.EquipableComponent;
import com.facundolinlaud.supergame.components.items.ItemComponent;
import com.facundolinlaud.supergame.components.player.BagComponent;
import com.facundolinlaud.supergame.components.player.KeyboardComponent;
import com.facundolinlaud.supergame.components.player.WearComponent;
import com.facundolinlaud.supergame.components.sprite.AnimableSpriteComponent;
import com.facundolinlaud.supergame.components.sprite.RefreshSpriteRequirementComponent;
import com.facundolinlaud.supergame.components.sprite.StackableSpriteComponent;
import com.facundolinlaud.supergame.components.sprite.StackedSpritesComponent;
import com.facundolinlaud.supergame.engine.GameResources;
import com.facundolinlaud.supergame.model.RenderPriority;
import com.facundolinlaud.supergame.model.WearType;
import com.facundolinlaud.supergame.model.entity.ItemModel;
import com.facundolinlaud.supergame.model.entity.PlayerModel;
import com.facundolinlaud.supergame.utils.strategy.impl.SpriteRenderPositionStrategyImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by facundo on 27/7/16.
 */
public class PlayerFactory {
    public static void createPlayer(Engine engine){
        PlayerModel playerModel = ModelFactory.getPlayerModel();

        Entity player = new Entity()
                .add(new PositionComponent(playerModel.getX(), playerModel.getY()))
                .add(new KeyboardComponent())
                .add(new VelocityComponent(playerModel.getVelocity()))
                .add(new BodyComponent(PhysicsFactory.get().createItemBody()))
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

    private static Map<WearType, Entity> createWearablesEntities(Engine engine, PlayerModel playerModel){
        Map<WearType, ItemModel> wearablesModels = playerModel.getEquipment();
        Map<WearType, String> bodyModels = playerModel.getBody();
        HashMap<WearType, Entity> wearables = new HashMap<>();

        for(WearType wearType : bodyModels.keySet()){
            String texture = bodyModels.get(wearType);
            Entity e = new Entity().add(new StackableSpriteComponent(TextureFactory.getTexture(texture)));

            wearables.put(wearType, e);
            engine.addEntity(e);
        }

        for(WearType wearType : wearablesModels.keySet()){
            Entity e = createItemEntity(wearablesModels.get(wearType));

            wearables.put(wearType, e);
            engine.addEntity(e);
        }

        return wearables;
    }

    private static List<Entity> createItems(Engine engine, List<ItemModel> itemsModels){
        List<Entity> itemsList = new ArrayList<Entity>();

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
           .add(new RenderComponent(new TextureRegion(TextureFactory.getTexture(itemModel.getPicture())), new RenderPriority(1)));

        if(itemModel.isEquipable()) {
            e.add(new EquipableComponent(itemModel.getWearType(), itemModel.getAttack(), itemModel.getDefense()))
             .add(new StackableSpriteComponent(TextureFactory.getTexture(itemModel.getTexture())));
        }

        return e;
    }
}
