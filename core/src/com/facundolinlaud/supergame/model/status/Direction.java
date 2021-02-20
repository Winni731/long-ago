package com.facundolinlaud.supergame.model.status;

/**
 * Created by facundo on 26/7/16.
 */
public enum Direction {
    UP("UP"),
    LEFT("LEFT"),
    DOWN("DOWN"),
    RIGHT("RIGHT");
    private String name;

    Direction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Direction fromString(String name){
        if(name != null){
            for (Direction d : Direction.values()) {
                if (name.endsWith(d.name)) {
                    return d;
                }
            }
        }

        return null;
    }

    public boolean isVertical(){
        return this == UP || this == DOWN;
    }

    public boolean isHorizontal(){
        return this == LEFT || this == RIGHT;
    }
}
