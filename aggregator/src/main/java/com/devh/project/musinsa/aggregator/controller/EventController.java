package com.devh.project.musinsa.aggregator.controller;

import com.devh.project.musinsa.aggregator.listener.ChangeEventListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aggregator/v1/api/event")
public class EventController {

    private final ChangeEventListener changeEventListener;

    public EventController(ChangeEventListener changeEventListener) {
        this.changeEventListener = changeEventListener;
    }

    @PostMapping("/change")
    public Boolean handleChangeEvent() {
        System.out.println("change!");
        changeEventListener.handleChangeEvent();
        return true;
    }

}
