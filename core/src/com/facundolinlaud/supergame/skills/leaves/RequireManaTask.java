package com.facundolinlaud.supergame.skills.leaves;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.facundolinlaud.supergame.behaviortree.Task;
import com.facundolinlaud.supergame.components.ManaComponent;
import com.facundolinlaud.supergame.skills.SkillBlackboard;
import com.facundolinlaud.supergame.utils.Mappers;

import static com.facundolinlaud.supergame.utils.events.Messages.REJECTED_SKILL_DUE_TO_NO_MANA;

/**
 * Pops: nothing
 * Pushes: nothing
 */
public class RequireManaTask extends Task<SkillBlackboard> {
    private static ComponentMapper<ManaComponent> mm = Mappers.mana;
    private MessageDispatcher messageDispatcher = MessageManager.getInstance();

    private int mana;

    public RequireManaTask(int mana) {
        this.mana = mana;
    }

    @Override
    public void activate() {
        Entity caster = getBlackboard().getCaster();
        ManaComponent manaComponent = mm.get(caster);

        if (manaComponent.canCast(mana)) {
            completed();
        } else {
            dispatchNoManaMessage();
            failed();
        }
    }

    private void dispatchNoManaMessage() {
        messageDispatcher.dispatchMessage(REJECTED_SKILL_DUE_TO_NO_MANA);
    }
}
