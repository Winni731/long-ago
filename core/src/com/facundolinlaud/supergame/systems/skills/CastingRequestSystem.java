package com.facundolinlaud.supergame.systems.skills;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.facundolinlaud.supergame.components.ManaComponent;
import com.facundolinlaud.supergame.components.SkillsComponent;
import com.facundolinlaud.supergame.components.skills.SkillCastingComponent;
import com.facundolinlaud.supergame.components.skills.SkillCastingRequestComponent;
import com.facundolinlaud.supergame.components.skills.SkillClickComponent;
import com.facundolinlaud.supergame.model.skill.Skill;
import com.facundolinlaud.supergame.utils.Mappers;
import com.facundolinlaud.supergame.utils.events.Messages;

public abstract class CastingRequestSystem extends IteratingSystem {
    private ComponentMapper<SkillsComponent> sm = Mappers.skills;
    private ComponentMapper<ManaComponent> mm = Mappers.mana;
    private MessageDispatcher messageDispatcher;

    public CastingRequestSystem(Family family) {
        super(family);
        this.messageDispatcher = MessageManager.getInstance();
    }

    protected boolean canCast(Entity caster, Skill skill){
        if(isSkillAvailable(caster, skill)) {
            if (hasMana(caster, skill)) {
                return true;
            }

            messageDispatcher.dispatchMessage(Messages.REJECTED_SKILL_DUE_TO_NO_MANA);
        }else{
            messageDispatcher.dispatchMessage(Messages.REJECTED_SKILL_DUE_TO_NOT_READY);
        }

        return false;
    }

    protected void cast(Entity caster, Skill skill){
        ManaComponent manaComponent = mm.get(caster);
        System.out.println("sacando " + skill.getManaConsumption() + " a mana: " + manaComponent.getCurrentMana());
        manaComponent.cast(skill);

        caster.add(new SkillCastingComponent(skill));
        caster.remove(SkillCastingRequestComponent.class);
    }

    private boolean isSkillAvailable(Entity caster, Skill skill){
        SkillsComponent skillsComponent = sm.get(caster);
        return skillsComponent.canCast(skill);
    }

    private boolean hasMana(Entity caster, Skill skill){
        ManaComponent manaComponent = mm.get(caster);
        return manaComponent.canCast(skill);
    }

    protected void rejectRequest(Entity caster, Skill skill){
        caster.remove(SkillCastingRequestComponent.class);

        if(skill.getSkillType().isTwoClick())
            caster.remove(SkillClickComponent.class);
    }
}
