package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("FROM Account WHERE accountId=:accId")
    public Account getAccountFromID(@Param("accId") int accId);

    @Query("FROM Account WHERE username=:username")
    public Account getAccountFromUsername(@Param("username")String username);
}
