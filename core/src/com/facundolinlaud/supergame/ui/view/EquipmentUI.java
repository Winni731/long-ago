package com.facundolinlaud.supergame.ui.view;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.facundolinlaud.supergame.ui.model.Item;
import com.facundolinlaud.supergame.ui.view.equipment.EquipmentWindow;
import com.facundolinlaud.supergame.ui.view.cross.DropAreaTarget;
import com.facundolinlaud.supergame.ui.view.utils.ToggleWindowListener;
import com.facundolinlaud.supergame.model.equip.EquipSlot;
import com.facundolinlaud.supergame.utils.mediator.Mediator;

import java.util.Map;

/**
 * Created by facundo on 4/2/16.
 */
public class EquipmentUI implements UI {
    private EquipmentWindow window;

    public EquipmentUI(Mediator uiMediator, Stage stage, Skin skin, DragAndDrop dragAndDrop, Table itemDropZone) {
        this.window = new EquipmentWindow(skin, uiMediator, dragAndDrop);

        stage.addActor(window);
        stage.addListener(new ToggleWindowListener(window, Input.Keys.C));
        dragAndDrop.addTarget(new DropAreaTarget(itemDropZone, uiMediator));
    }

    @Override
    public Table get() {
        return window;
    }

    public void update(Map<EquipSlot, Item> items) {
        this.window.update(items);
    }
}
