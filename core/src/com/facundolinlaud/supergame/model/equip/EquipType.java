package com.facundolinlaud.supergame.model.equip;

public enum EquipType {
    SPEAR("SPEAR"),
    DAGGER("DAGGER"),
    SWORD("SWORD"),
    BOW("BOW"),
    WAND("WAND");

    private String name;

    EquipType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
