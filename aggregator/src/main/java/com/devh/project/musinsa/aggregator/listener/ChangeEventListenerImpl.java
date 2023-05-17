package com.devh.project.musinsa.aggregator.listener;

import com.devh.project.musinsa.aggregator.event.ChangeEvent;
import com.devh.project.musinsa.aggregator.event.Event;
import com.devh.project.musinsa.aggregator.executor.AggregationExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
public class ChangeEventListenerImpl implements ChangeEventListener {

    private final BlockingQueue<Event> events;
    private final AggregationExecutor aggregationExecutor;

    public ChangeEventListenerImpl(AggregationExecutor aggregationExecutor) {
        this.events = new ArrayBlockingQueue<>(100);
        this.aggregationExecutor = aggregationExecutor;

        new Thread(() -> {
            while (true) {
                try {
                    events.take();
                    events.clear();
                    this.aggregationExecutor.execute();
                } catch (InterruptedException ignored) {}
            }
        }).start();
    }

    @Override
    public void handleChangeEvent() {
        try {
            events.offer(ChangeEvent.create(), 3, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {}
    }
}
