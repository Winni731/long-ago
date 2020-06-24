package com.facundolinlaud.supergame.ai.decisionmaking;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import com.facundolinlaud.supergame.components.ai.BehaviorComponent;
import com.facundolinlaud.supergame.utils.Distances;
import com.facundolinlaud.supergame.utils.Mappers;

public class PlayerSeenTask extends LeafTask<Blackboard> {
    private ComponentMapper<BehaviorComponent> aim = Mappers.ai;

    @Override
    public Status execute() {
        Blackboard blackboard = super.getObject();
        Entity agent = blackboard.getAgent();
        BehaviorComponent aiComponent = aim.get(agent);
        float viewDistance = aiComponent.getViewDistance();

        if(isPlayerNear(blackboard.getPlayerPosition(), blackboard.getAgentPosition(), viewDistance))
            return Status.SUCCEEDED;
        else
            return Status.FAILED;
    }

    private boolean isPlayerNear(Vector2 playerPosition, Vector2 agentPosition, float minimumDistance){
        float distanceToPlayer = Distances.calculateEuclideanDistanceBetween(playerPosition, agentPosition);
        return distanceToPlayer < minimumDistance;
    }


    @Override
    protected Task<Blackboard> copyTo(Task<Blackboard> task) {
        return new PlayerSeenTask();
    }
}
