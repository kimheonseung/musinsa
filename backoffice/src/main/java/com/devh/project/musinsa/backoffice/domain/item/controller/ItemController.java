package com.devh.project.musinsa.backoffice.domain.item.controller;

import com.devh.project.musinsa.backoffice.domain.common.dto.ApiResponse;
import com.devh.project.musinsa.backoffice.domain.item.dto.ItemAddRequest;
import com.devh.project.musinsa.backoffice.domain.item.dto.ItemDTO;
import com.devh.project.musinsa.backoffice.domain.item.dto.ItemDeleteRequest;
import com.devh.project.musinsa.backoffice.domain.item.dto.ItemUpdateRequest;
import com.devh.project.musinsa.backoffice.domain.item.service.ItemService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/backoffice/api/v1/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PutMapping
    public ApiResponse<ItemDTO> addItem(@RequestBody @Valid ItemAddRequest itemAddRequest) {
        return ApiResponse.create(this.itemService.add(itemAddRequest));
    }

    @PostMapping
    public ApiResponse<ItemDTO> updateItem(@RequestBody @Valid ItemUpdateRequest itemUpdateRequest) {
        return ApiResponse.create(this.itemService.update(itemUpdateRequest));
    }

    @DeleteMapping
    public ApiResponse<ItemDTO> deleteItem(@RequestBody @Valid ItemDeleteRequest itemDeleteRequest) {
        return ApiResponse.create(this.itemService.delete(itemDeleteRequest));
    }

}
