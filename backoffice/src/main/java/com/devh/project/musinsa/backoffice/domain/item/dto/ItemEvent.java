package com.devh.project.musinsa.backoffice.domain.item.dto;

import lombok.Getter;

@Getter
public class ItemEvent {

    private ItemEvent() {}

    public static ItemEvent create() {
        return new ItemEvent();
    }
}
