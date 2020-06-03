package com.facundolinlaud.supergame.skills.leaves;

import com.badlogic.ashley.core.Entity;
import com.facundolinlaud.supergame.behaviortree.PoolableTask;
import com.facundolinlaud.supergame.services.AgentService;
import com.facundolinlaud.supergame.skills.SkillBlackboard;
import com.facundolinlaud.supergame.ui.controller.OverlayUIController;

public class DisplayCastBarTask extends PoolableTask<SkillBlackboard> {
    private OverlayUIController overlayUIController;
    private AgentService agentService;

    private String title;
    private float totalTime;
    private float elapsedTime;
    private boolean foundOutIfMainPlayer;

    public DisplayCastBarTask(String title, float totalTime) {
        this.title = title;
        this.elapsedTime = 0f;
        this.totalTime = totalTime;
        this.foundOutIfMainPlayer = false;
    }

    @Override
    protected void onBlackboardAvailable(SkillBlackboard blackboard) {
        overlayUIController = blackboard.getOverlayUIController();
        agentService = blackboard.getAgentService();
    }

    @Override
    public void tick(float delta) {
        if (!foundOutIfMainPlayer) {
            Entity caster = getBlackboard().getCaster();
            if (agentService.isPlayer(caster)) {
                foundOutIfMainPlayer = true;
            } else {
                completed();
                return;
            }
        }

        elapsedTime += delta;
        overlayUIController.updateCastingBar(title, elapsedTime / totalTime);

        if (elapsedTime >= totalTime) completed();
    }

    @Override
    public void reset() {
        elapsedTime = 0f;
        foundOutIfMainPlayer = false;
    }
}
