package com.depromeet.ahmatda.item.service.impl;

import com.depromeet.ahmatda.domain.item.Item;
import com.depromeet.ahmatda.domain.item.adaptor.ItemAdaptor;
import com.depromeet.ahmatda.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeviceItemService implements ItemService {

    private final ItemAdaptor itemAdaptor;

    @Override
    public void createItem(Item item) {
        itemAdaptor.createItem(item);
    }

    @Override
    public void deleteItem(Item item) {
        itemAdaptor.deleteItem(item);
    }
}
