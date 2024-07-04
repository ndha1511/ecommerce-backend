package com.code.salesappbackend.oauth2;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public abstract class SocialAccount {
    private String accountId;
    private String name;
    private String email;

    protected SocialAccount(String accountId, String name, String email) {
        this.accountId = accountId;
        this.name = name;
        this.email = email;
    }
}
