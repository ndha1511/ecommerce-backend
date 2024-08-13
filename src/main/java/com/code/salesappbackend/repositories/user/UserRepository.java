package com.code.salesappbackend.repositories.user;

import com.code.salesappbackend.models.user.User;
import com.code.salesappbackend.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query(value = "select u from Order o " +
            "inner join User u on o.user = u group by u.id " +
            "order by sum(o.discountedAmount) desc " +
            "limit 10")
    List<User> selectTop10CustomerByTheMost();
}