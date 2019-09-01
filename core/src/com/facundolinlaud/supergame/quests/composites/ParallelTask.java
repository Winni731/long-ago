package com.facundolinlaud.supergame.quests.composites;

import com.facundolinlaud.supergame.quests.Task;

import java.util.LinkedList;

public class ParallelTask<T> extends CompositeTask<T> {
    public ParallelTask(Task...children) {
        super(children);
    }

    @Override
    public void childCompleted(Task child) {
        completed.add(child);
        children.remove(child);

        if(children.isEmpty())
            completed();
    }

    @Override
    public void activate() {
        children.forEach(task -> task.activate());
    }
}
