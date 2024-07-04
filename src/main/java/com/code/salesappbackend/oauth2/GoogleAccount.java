package com.code.salesappbackend.oauth2;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class GoogleAccount extends SocialAccount {
    private String pictureUrl;

    public GoogleAccount(String accountId, String name, String email, String pictureUrl) {
        super(accountId, name, email);
        this.pictureUrl = pictureUrl;
    }
}
