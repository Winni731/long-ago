package com.facundolinlaud.supergame.ui.view.overlay.skillsbar;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.facundolinlaud.supergame.model.skill.Skill;
import com.facundolinlaud.supergame.ui.view.cross.draganddrop.SkillSlotSource;
import com.facundolinlaud.supergame.ui.view.cross.draganddrop.SlotType;

import java.util.List;
import java.util.Map;

public class SkillBar extends Table {
    private static final int SIZE = 10;

    private Array<SkillBarSlot> slots;

    public SkillBar(Skin skin, Map<Integer, Skill> skills, DragAndDrop skillsDAD) {
        super(skin);
        this.setBackground("spellbar");
        this.slots = new Array<>(false, SIZE);
        this.align(Align.left);
        this.padLeft(3);

        for(int i = 0; i < SIZE; i++){
            SkillBarSlot slot = new SkillBarSlot(skin, String.valueOf(i + 1), i);
            this.slots.add(slot);
            this.add(slot).width(32).height(32).padRight(4).left();
            skillsDAD.addTarget(new SkillBarSlotTarget(slot));
            skillsDAD.addSource(new SkillSlotSource(slot, SlotType.SKILL_BAR_SLOT, i));

            if(skills.containsKey(i)){
                slot.setContent(skills.get(i));
            }
        }
    }

    public void beginCooldown(Skill skill) {
        float cooldown = skill.getCooldown();
        for (SkillBarSlot s : slots) {
            if (skill == s.getContent()) {
                s.beginCooldown(cooldown);
            }
        }
    }

    public void update(Map<Integer, Skill> buttonsToSkills) {
        for(int i = 0; i < slots.size; i++){
            if(buttonsToSkills.containsKey(i)){
                Skill skill = buttonsToSkills.get(i);
                slots.get(i).setContent(skill);
            }else{
                slots.get(i).clearContent();
            }
        }
    }
}
