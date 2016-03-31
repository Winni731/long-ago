package com.facundolinlaud.supergame.ui.view.attributes;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.facundolinlaud.supergame.ui.model.Attributes;
import com.facundolinlaud.supergame.utils.mediator.Mediator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by facundo on 3/31/16.
 */
public class Grid extends Table {
    private static final int DEFAULT_VALUE = 1;

    private List<Row> attributesRows;

    public Grid(Mediator uiMediator, Skin skin) {
        super(skin);

        this.attributesRows = new ArrayList<>();

        for(String attr : Attributes.availableAttributes){
            Row row = new Row(uiMediator, skin, attr, DEFAULT_VALUE);

            attributesRows.add(row);
            add(row).expandX().fillX();
            row();
        }
    }

    public void update(Attributes attributes) {
        List<Integer> values = attributes.getAsList();

        for(int i = 0; i < attributesRows.size(); i++){
            attributesRows.get(i).setAttributeValue(values.get(i));
        }
    }
}
