package com.facundolinlaud.supergame.behaviortree;

import com.facundolinlaud.supergame.utils.Debugger;

import java.util.LinkedList;
import java.util.List;

public abstract class CompositeTask<T extends Blackboard> extends Task<T> {
    protected LinkedList<Task> children;
    protected List<Task> completed;

    public CompositeTask(LinkedList<Task> children) {
        this.children = children;
        this.completed = new LinkedList();

        children.forEach(child -> child.setParent(this));
    }

    public void childFailed(Task child) {
        Debugger.debug("Task " + child + " failed");
        children.addAll(0, completed);
        completed.clear();

        activate();
    }

    @Override
    protected void onBlackboardAvailable(Blackboard blackboard) {
        children.forEach(child -> child.setBlackboard(blackboard));
    }

    public abstract void childCompleted(Task child);
}
