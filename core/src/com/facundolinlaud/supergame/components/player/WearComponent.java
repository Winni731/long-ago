package com.facundolinlaud.supergame.components.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.facundolinlaud.supergame.model.equip.EquipSlot;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by facundo on 4/1/16.
 */
public class WearComponent implements Component {
    public ObservableMap<EquipSlot, Entity> wearables;

    public WearComponent(Map<EquipSlot, Entity> wearables) {
        this.wearables = FXCollections.observableMap(wearables);
    }

    public WearComponent(Map<EquipSlot, Entity> wearables,
                         MapChangeListener<? super EquipSlot, ? super Entity> listener) {
        this(wearables);
        this.wearables.addListener(listener);
    }

    public List<Entity> asList(){
        return wearables.keySet().stream().sorted((e1, e2) -> e1.getRenderPriority() - e2.getRenderPriority()).map(e -> wearables.get(e)).collect(Collectors.toList());
    }

    public List<Entity> getEquipmentAsList(){
        List<Entity> equipment = new ArrayList();

        for(EquipSlot slot : EquipSlot.values()){
            if((slot.hasAttackComponent() || slot.hasDefenseComponent()) && wearables.containsKey(slot))
                equipment.add(wearables.get(slot));
        }

        return equipment;
    }
}
