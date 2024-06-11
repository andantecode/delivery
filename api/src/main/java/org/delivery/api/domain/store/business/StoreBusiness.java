package org.delivery.api.domain.store.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.db.store.enums.StoreCategory;

import java.util.List;

@Business
@RequiredArgsConstructor
public class StoreBusiness {

    private final StoreService storeService;
    private final StoreConverter storeConverter;

    public StoreResponse register(StoreRegisterRequest registerRequest) {
        var entity = storeConverter.toEntity(registerRequest);

        var newEntity = storeService.register(entity);

        var response = storeConverter.toResponse(newEntity);

        return response;
    }

    public List<StoreResponse> searchCategory(StoreCategory category) {
        var storeList = storeService.searchByCategory(category);

        return storeList.stream()
                .map(storeConverter::toResponse)
                .toList();
    }
}
