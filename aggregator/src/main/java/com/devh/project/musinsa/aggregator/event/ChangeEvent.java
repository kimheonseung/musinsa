package com.devh.project.musinsa.aggregator.event;

public class ChangeEvent implements Event {
    private ChangeEvent() {}

    public static ChangeEvent create() {
        return new ChangeEvent();
    }
}
