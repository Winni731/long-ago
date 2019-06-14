package com.facundolinlaud.supergame.model.sprite;

import com.facundolinlaud.supergame.model.status.Status;

import java.util.Map;

/**
 * Created by facundo on 7/5/16.
 */
public class RawAnimationModel {
    private float frameDuration;
    private Map<Status, SubAnimationModel> subAnimations;

    public RawAnimationModel() {}

    public void setSubAnimations(Map<Status, SubAnimationModel> subAnimations) {
        this.subAnimations = subAnimations;
    }

    public void setFrameDuration(float frameDuration) {
        this.frameDuration = frameDuration;
    }

    public Map<Status, SubAnimationModel> getSubAnimations() {
        return subAnimations;
    }

    public float getFrameDuration() {
        return frameDuration;
    }

    @Override
    public String toString() {
        return "RawAnimationModel{" +
                "frameDuration=" + frameDuration +
                ", subAnimations=" + subAnimations +
                '}';
    }
}
