package com.facundolinlaud.supergame.components.ai;

import com.badlogic.ashley.core.Component;
import com.facundolinlaud.supergame.model.agent.NPCInformation;
import com.facundolinlaud.supergame.model.ai.BehaviorType;

import java.util.List;

public class AIComponent implements Component {
    private float viewDistance;
    private List<Integer> meleeSkills;
    private List<Integer> rangedSkills;
    private BehaviorType behaviorType;

    public AIComponent(NPCInformation model) {
        this.viewDistance = model.getViewDistance();
        this.meleeSkills = model.getMeleeSkills();
        this.rangedSkills = model.getRangedSkills();
        this.behaviorType = model.getBehaviorType();
    }

    public float getViewDistance() {
        return viewDistance;
    }

    public void setViewDistance(float viewDistance) {
        this.viewDistance = viewDistance;
    }

    public List<Integer> getMeleeSkills() {
        return meleeSkills;
    }

    public void setMeleeSkills(List<Integer> meleeSkills) {
        this.meleeSkills = meleeSkills;
    }

    public List<Integer> getRangedSkills() {
        return rangedSkills;
    }

    public void setRangedSkills(List<Integer> rangedSkills) {
        this.rangedSkills = rangedSkills;
    }

    public BehaviorType getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(BehaviorType behaviorType) {
        this.behaviorType = behaviorType;
    }
}
