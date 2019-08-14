package com.facundolinlaud.supergame.quests;

import com.facundolinlaud.supergame.utils.Debugger;

import java.util.LinkedList;
import java.util.List;

public abstract class CompositeTask extends Task {
    protected LinkedList<Task> children;
    protected List<Task> completed;

    public CompositeTask(LinkedList<Task> children) {
        this.children = children;
        this.completed = new LinkedList();

        children.forEach(child -> child.setParent(this));
    }

    void childFailed(Task child) {
        Debugger.debug("Task " + child + " failed");
        children.addAll(0, completed);
        completed.clear();

        activate();
    }

    abstract void childCompleted(Task child);
}
