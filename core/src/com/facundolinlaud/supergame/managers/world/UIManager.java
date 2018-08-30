package com.facundolinlaud.supergame.managers.world;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.facundolinlaud.supergame.managers.Renderable;
import com.facundolinlaud.supergame.systems.ui.*;
import com.facundolinlaud.supergame.ui.controller.*;
import com.facundolinlaud.supergame.ui.controller.impl.*;
import com.facundolinlaud.supergame.ui.view.*;
import com.facundolinlaud.supergame.ui.view.SkillCastingUI;
import com.facundolinlaud.supergame.utils.events.*;
import com.facundolinlaud.supergame.utils.mediator.Mediator;

/**
 * Created by facundo on 3/25/16.
 */
public class UIManager implements Renderable {
    private static final String SKIN_JSON_PATH = "ui/skin/uiskin.json";
    private static final String TEXTURE_ATLAS_PATH = "ui/skin/uiskin.atlas";
    private static final int MIN_DRAG_TIME_IN_MILLISECONDS = 10;

    private Skin skin;
    private Stage stage;
    private Mediator uiMediator;
    private DragAndDrop dragAndDrop;

    private OverlayUI hud;
    private InventoryUI inventoryUI;
    private AttributesUI attributesUI;
    private EquipmentUI equipmentUI;
    private SkillCastingUI skillCastingUI;

    private InventoryUIController inventoryUIController;
    private ProfileUIController profileUIController;
    private AttributesUIController attributesUIController;
    private EquipmentUIController equipmentUIController;
    private SkillCastingUIController skillCastingUIController;

    public UIManager(Stage stage) {
        this.skin = new Skin(Gdx.files.internal(SKIN_JSON_PATH));
        this.skin.addRegions(new TextureAtlas(Gdx.files.internal(TEXTURE_ATLAS_PATH)));
        this.stage = stage;

        initializeUIResources();
        initializeUI();
        initializeServices();
        addUIToStage();
        subscribeReceivers();
    }

    private void initializeUIResources(){
        this.uiMediator = new Mediator();
        this.dragAndDrop = new DragAndDrop();
        this.dragAndDrop.setDragTime(MIN_DRAG_TIME_IN_MILLISECONDS);
    }

    private void initializeUI() {
        this.hud = new OverlayUI(skin);
        this.inventoryUI = new InventoryUI(uiMediator, stage, skin, dragAndDrop, hud.getItemDropZone());
        this.attributesUI = new AttributesUI(uiMediator, stage, skin);
        this.equipmentUI = new EquipmentUI(uiMediator, stage, skin, dragAndDrop);
        this.skillCastingUI = new SkillCastingUI();
    }

    private void initializeServices() {
        this.profileUIController = new ProfileUIControllerImpl(this.hud);
        this.inventoryUIController = new InventoryUIControllerImpl(this.inventoryUI);
        this.attributesUIController = new AttributesUIControllerImpl(this.attributesUI);
        this.equipmentUIController = new EquipmentUIControllerImpl(this.equipmentUI);
        this.skillCastingUIController = new SkillCastingUIControllerImpl(this.skillCastingUI);
    }

    private void addUIToStage() {
        this.stage.addActor(this.hud.get());
        this.stage.addActor(this.skillCastingUI);
    }

    private void subscribeReceivers(){
        this.uiMediator.subscribe(ItemDroppedEvent.class, this.inventoryUIController);
        this.uiMediator.subscribe(ItemsPositionSwapEvent.class, this.inventoryUIController);
        this.uiMediator.subscribe(AttributeUpgradeEvent.class, this.attributesUIController);
        this.uiMediator.subscribe(UnequipItemEvent.class, this.equipmentUIController);
        this.uiMediator.subscribe(EquipItemEvent.class, this.equipmentUIController);
    }

    public void initializeSystems(Engine engine){
        engine.addSystem(new ProfileUISystem(this.hud));
        engine.addSystem(new InventoryUISystem(this.inventoryUIController));
        engine.addSystem(new AttributesUISystem(this.attributesUIController));
        engine.addSystem(new EquipmentUISystem(this.equipmentUIController));
        engine.addSystem(new SkillCastingUISystem(this.skillCastingUIController));
    }

    @Override
    public void render(){
        stage.act();
        stage.draw();
    }
}