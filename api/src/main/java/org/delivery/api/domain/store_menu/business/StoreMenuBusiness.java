package org.delivery.api.domain.store_menu.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store_menu.controller.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.store_menu.controller.model.StoreMenuResponse;
import org.delivery.api.domain.store_menu.converter.StoreMenuConverter;
import org.delivery.api.domain.store_menu.service.StoreMenuService;

import java.util.List;

@RequiredArgsConstructor
@Business
public class StoreMenuBusiness {

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;


    public StoreMenuResponse register(StoreMenuRegisterRequest request) {
        var entity = storeMenuConverter.toEntity(request);
        var newEntity = storeMenuService.register(entity);
        return storeMenuConverter.toResponse(newEntity);
    }

    public List<StoreMenuResponse> search(Long storeId) {
        var list = storeMenuService.getStoreMenuByStoreId(storeId);

        return list.stream()
                .map(storeMenuConverter::toResponse)
                .toList();
    }


}
