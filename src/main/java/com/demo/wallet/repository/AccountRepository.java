package com.demo.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE account SET balance = balance - :amount " +
            "WHERE email = :email AND balance >= :amount", nativeQuery = true)
    int deductBalance(@Param("email") String email, @Param("amount") BigDecimal amount);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE account SET balance = balance + :amount " +
            "WHERE email = :email", nativeQuery = true)
    int addBalance(@Param("email") String email, @Param("amount") BigDecimal amount);

}
