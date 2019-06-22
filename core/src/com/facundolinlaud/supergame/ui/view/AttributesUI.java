package com.facundolinlaud.supergame.ui.view;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.facundolinlaud.supergame.ui.model.Attributes;
import com.facundolinlaud.supergame.ui.view.attributes.AttributesWindow;
import com.facundolinlaud.supergame.ui.view.utils.ToggleWindowListener;

/**
 * Created by facundo on 3/31/16.
 */
public class AttributesUI implements UI {

    private AttributesWindow window;

    public AttributesUI(Stage stage, Skin skin) {
        this.window = new AttributesWindow(skin);

        stage.addActor(window);
        stage.addListener(new ToggleWindowListener(window, Input.Keys.P));
    }

    public void update(Attributes attributes){
        this.window.update(attributes);
    }

    @Override
    public Table get() {
        return this.window;
    }
}
