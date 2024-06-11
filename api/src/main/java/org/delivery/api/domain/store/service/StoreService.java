package org.delivery.api.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;

    /**
     * 유효한 스토어 가져오기
     * @param id
     * @return
     */
    public StoreEntity getStoreWithThrow(Long id) {
        return storeRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreStatus.REGISTERED)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    /**
     * 스토어 등록하기
     * @param storeEntity
     * @return
     */
    public StoreEntity register(StoreEntity storeEntity) {
        return Optional.ofNullable(storeEntity)
                .map(it -> {
                    it.setStar(0);
                    it.setStatus(StoreStatus.REGISTERED);
                    // TODO 등록일시 추가하기
                    return storeRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    /**
     * 카테고리로 검색
     * @param category
     * @return
     */
    public List<StoreEntity> searchByCategory(StoreCategory category) {
        var list = storeRepository.findAllByStatusAndCategoryOrderByStarDesc(
                StoreStatus.REGISTERED, category
        );

        return list;
    }

    public List<StoreEntity> registerList() {
        var list = storeRepository.findAllByStatusOrderByIdDesc(StoreStatus.REGISTERED);

        return list;
    }



}
