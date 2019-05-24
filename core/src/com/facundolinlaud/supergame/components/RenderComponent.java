package com.facundolinlaud.supergame.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.facundolinlaud.supergame.model.RenderPriority;
import com.facundolinlaud.supergame.strategies.renderposition.RenderPositionStrategy;
import com.facundolinlaud.supergame.strategies.renderposition.DefaultRenderPositionStrategyImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by facundo on 3/18/16.
 */
public class RenderComponent implements Component {
    private List<TextureRegion> regions;
    private RenderPriority priority;
    private RenderPositionStrategy renderPositionStrategy;

    {
        this.priority = RenderPriority.createNormalRenderPriority();
        this.renderPositionStrategy = new DefaultRenderPositionStrategyImpl();
    }

    public RenderComponent(TextureRegion region, RenderPriority priority) {
        this.regions = new ArrayList();
        this.regions.add(region);
        this.priority = priority;
    }

    public RenderComponent(TextureRegion region) {
        this.regions = new ArrayList();
        this.regions.add(region);
    }

    public RenderComponent(RenderPositionStrategy renderPositionStrategy) {
        this.renderPositionStrategy = renderPositionStrategy;
        this.regions = new ArrayList();
    }

    public List<TextureRegion> getRegions() {
        return regions;
    }

    public RenderPriority getPriority() {
        return priority;
    }

    public RenderPositionStrategy getRenderPositionStrategy() {
        return renderPositionStrategy;
    }

    public void clear() {
        this.regions.clear();
    }

    public void add(TextureRegion region){
        this.regions.add(region);
    }
}
