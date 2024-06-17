package org.delivery.storeadmin.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.storeadmin.common.annotation.Business;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.storeadmin.domain.storemenu.service.StoreMenuService;
import org.delivery.storeadmin.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.storeadmin.domain.userorder.converter.UserOrderConverter;
import org.delivery.storeadmin.domain.userorder.service.UserOrderService;
import org.delivery.storeadmin.domain.userordermenu.service.UserOrderMenuService;

@RequiredArgsConstructor
@Business
public class UserOrderBusiness {
    private final UserOrderService userOrderService;
    private final UserOrderConverter userOrderConverter;

    private final SseConnectionPool sseConnectionPool;
    private final UserOrderMenuService userOrderMenuService;
    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    /**
     * 1. 주문
     * 2. 주문 내역 찾기
     * 3. 스토어 찾기
     * 4. 연결된 세션 찾아 push
     * @param userOrderMessage
     */
    public void pushUserOrder(UserOrderMessage userOrderMessage) {

        // user order entity
        var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId()).orElseThrow(
                () -> new RuntimeException("UserOrder Not Found")
        );

        // user order menu
        var userOrderMenuList = userOrderMenuService.getUserOrderMenuList(userOrderEntity.getId());

        // user order menu -> store menu
        var storeMenuEntityList = userOrderMenuList.stream()
                .map(it -> storeMenuService.getStoreMenuWithThrow(it.getStoreMenuId()))
                .map(storeMenuConverter::toResponse)
                .toList();

        // response
        var userOrderResponse = userOrderConverter.toResponse(userOrderEntity);

        var push = UserOrderDetailResponse.builder()
                        .userOrderResponse(userOrderResponse)
                        .storeMenuResponseList(storeMenuEntityList)
                        .build();

        var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

        // push
        userConnection.sendMessage(push);
    }
}
