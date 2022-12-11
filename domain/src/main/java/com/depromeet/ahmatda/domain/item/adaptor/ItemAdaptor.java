package com.depromeet.ahmatda.domain.item.adaptor;

import com.depromeet.ahmatda.domain.item.Item;
import com.depromeet.ahmatda.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemAdaptor {

    private final ItemRepository itemRepository;

    public void createItem(Item item) {
        itemRepository.save(item);
    }

    public void deleteItem(Item item) {
        itemRepository.delete(item);
    }

}
