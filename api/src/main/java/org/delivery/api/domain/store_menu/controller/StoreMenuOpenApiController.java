package org.delivery.api.domain.store_menu.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.store_menu.business.StoreMenuBusiness;
import org.delivery.api.domain.store_menu.controller.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.store_menu.controller.model.StoreMenuResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/open-api/store-menu")
@RequiredArgsConstructor
public class StoreMenuOpenApiController {

    private final StoreMenuBusiness storeMenuBusiness;

    /**
     * register store-menu
     * @param request
     * @return
     */
    @PostMapping("/register")
    public Api<StoreMenuResponse> register(
            @Valid
            @RequestBody Api<StoreMenuRegisterRequest> request
    ) {
        var req = request.getBody();
        var response = storeMenuBusiness.register(req);

        return Api.OK(response);
    }
}
