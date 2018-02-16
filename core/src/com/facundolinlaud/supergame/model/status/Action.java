package com.facundolinlaud.supergame.model.status;

/**
 * Created by facundo on 26/7/16.
 */
public enum Action {
    STANDING("STANDING"),
    SPELL_CASTING("SPELL_CASTING"),
    BLOCKING("BLOCKING"),
    WALKING("WALKING"),
    SWINGING("SWINGING"),
    DASHING("DASHING"),
    SHOOTING("SHOOTING"),
    FALLING("FALLING");

    private String name;

    Action(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Action fromString(String name){
        if(name != null){
            for (Action a : Action.values()) {
                if (name.startsWith(a.name)) {
                    return a;
                }
            }
        }

        return null;
    }

    public boolean isBusy(){
        return this == Action.DASHING || this == Action.BLOCKING ||
               this == Action.SHOOTING || this == Action.SPELL_CASTING ||
               this == Action.SWINGING || this == Action.FALLING;
    }
}
