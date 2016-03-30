package com.facundolinlaud.supergame.ui.services.impl;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.facundolinlaud.supergame.components.*;
import com.facundolinlaud.supergame.ui.model.Item;
import com.facundolinlaud.supergame.ui.services.InventoryUIService;
import com.facundolinlaud.supergame.ui.view.InventoryUI;
import com.facundolinlaud.supergame.utils.Mappers;
import com.facundolinlaud.supergame.utils.events.Event;
import com.facundolinlaud.supergame.utils.events.ItemDroppedEvent;
import com.facundolinlaud.supergame.utils.events.ItemsPositionSwapEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by facundo on 3/27/16.
 */
public class InventoryUIServiceImpl implements InventoryUIService {
    private ComponentMapper<ItemComponent> itc = Mappers.item;
    private ComponentMapper<BagComponent> bm = Mappers.bag;
    private ComponentMapper<PositionComponent> pm = Mappers.position;
    private ComponentMapper<RenderComponent> rm = Mappers.render;

    private InventoryUI ui;
    private Entity gatherer;

    public InventoryUIServiceImpl(InventoryUI ui) {
        this.ui = ui;
    }

    @Override
    public void setGatherer(Entity gatherer) {
        this.gatherer = gatherer;
    }

    @Override
    public void update(Entity entity, BagComponent bag) {
        setGatherer(entity);

        List<Item> items = new ArrayList<>();

        for(int i = 0; i < bag.items.size(); i++){
            Entity e = bag.items.get(i);

            ItemComponent itemComponent = itc.get(e);
            RenderComponent renderComponent = rm.get(e);

            items.add(new Item(itemComponent.name, itemComponent.weight, i, renderComponent.texture));
        }

        ui.updateItems(items);
    }

    @Override
    public void handle(Event event) {
        if(event instanceof ItemDroppedEvent){
            itemDropped((ItemDroppedEvent) event);
        }else if(event instanceof ItemsPositionSwapEvent){
            itemsPositionSwapped((ItemsPositionSwapEvent) event);
        }
    }

    private void itemDropped(ItemDroppedEvent event){
        PositionComponent gathererPosition = pm.get(gatherer);

        Entity item = bm.get(gatherer).items.remove(event.getItem().getPositionInBag());

        item.add(new PositionComponent(gathererPosition));
        item.add(new PickupableComponent());

        System.out.println("Item " + event.getItem().getName() + " dropped");
    }

    private void itemsPositionSwapped(ItemsPositionSwapEvent event) {
        BagComponent bag = bm.get(gatherer);

        Entity a = bag.items.get(event.getFirstItem().getPositionInBag());
        Entity b = bag.items.get(event.getSecondItem().getPositionInBag());

        bag.items.set(event.getSecondItem().getPositionInBag(), a);
        bag.items.set(event.getFirstItem().getPositionInBag(), b);

        System.out.println(event.getFirstItem().getName() + " and " + event.getSecondItem().getName() + " swapped");
    }
}