package com.facundolinlaud.supergame.quests.leaves;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.facundolinlaud.supergame.components.player.BagComponent;
import com.facundolinlaud.supergame.behaviortree.Task;
import com.facundolinlaud.supergame.quests.QuestBlackboard;
import com.facundolinlaud.supergame.utils.Mappers;

public class GoldRewardTask extends Task<QuestBlackboard> {
    private ComponentMapper<BagComponent> bm = Mappers.bag;

    private int gold;

    public GoldRewardTask(int gold) {
        this.gold = gold;
    }

    @Override
    public void activate() {
        reward();
        completed();
    }

    @Override
    public void completed() {
        super.completed();
    }

    public void reward() {
        Entity player = getBlackboard().getPlayer();
        bm.get(player).addGold(this.gold);
    }
}
