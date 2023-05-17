package com.devh.project.musinsa.backoffice.domain.item.service;

import com.devh.project.musinsa.backoffice.domain.item.dto.ItemAddRequest;
import com.devh.project.musinsa.backoffice.domain.item.dto.ItemDTO;
import com.devh.project.musinsa.backoffice.domain.item.dto.ItemDeleteRequest;
import com.devh.project.musinsa.backoffice.domain.item.dto.ItemUpdateRequest;

public interface ItemService {
    ItemDTO add(ItemAddRequest itemAddRequest);
    ItemDTO update(ItemUpdateRequest itemUpdateRequest);
    ItemDTO delete(ItemDeleteRequest itemDeleteRequest);
}
