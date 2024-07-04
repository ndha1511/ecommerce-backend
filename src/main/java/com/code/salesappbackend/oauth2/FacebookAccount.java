package com.code.salesappbackend.oauth2;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FacebookAccount extends SocialAccount{
    public FacebookAccount(String accountId, String name, String email) {
        super(accountId, name, email);
    }
}
