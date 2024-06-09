package org.delivery.api.account;

import lombok.RequiredArgsConstructor;
import org.delivery.api.account.model.AccountMeResponse;
import org.delivery.api.common.api.Api;
import org.delivery.db.acoount.AccountRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountApiController {

    private final AccountRepository accountRepository;

    @GetMapping("/me")
    public Api<AccountMeResponse> save() {
        var response = AccountMeResponse.builder()
                .name("함로운")
                .email("roundayy@gmail.com")
                .registeredAt(LocalDateTime.now())
                .build();

        var str = "helloworld";
        var age = Integer.parseInt(str);

        return Api.OK(response);
    }
}
