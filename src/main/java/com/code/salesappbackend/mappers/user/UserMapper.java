package com.code.salesappbackend.mappers.user;

import com.code.salesappbackend.dtos.requests.user.UserDto;
import com.code.salesappbackend.mappers.address.AddressMapper;
import com.code.salesappbackend.models.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final AddressMapper addressMapper;

    public User userDto2User(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .address(addressMapper.addressDto2Address(userDto.getAddress()))
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .gender(userDto.getGender())
                .dateOfBirth(userDto.getDateOfBirth())
                .phoneNumber(userDto.getPhoneNumber())
                .password(userDto.getPassword())
                .build();
    }
}
