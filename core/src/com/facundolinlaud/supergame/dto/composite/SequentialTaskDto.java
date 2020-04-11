package com.facundolinlaud.supergame.dto.composite;

import com.facundolinlaud.supergame.behaviortree.Task;
import com.facundolinlaud.supergame.behaviortree.SequentialTask;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
public class SequentialTaskDto extends CompositeTaskDto {
    @Override
    public Task build() {
        return new SequentialTask(buildChildren());
    }
}
