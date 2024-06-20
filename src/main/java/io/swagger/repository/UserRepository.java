package io.swagger.repository;

import io.swagger.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, String> {
    @Query("from Users where userName = :userName")
    Users findByUserName(@Param("userName") String userName);

    @Query("from Users where emailId = :emailId")
    Users findByEmailId(@Param("emailId") String emailId);

    @Query("from Users where msisdn = :msisdn")
    Users findByMsisdn(@Param("msisdn") String msisdn);

    @Query("from Users where userRole = :userRole")
    List<Users> findByUserRole(@Param("userRole") String userRole);

    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.lastLogin = :lastLogin WHERE u.userName = :userName")
    int updateUserLoginTime(@Param("userName") String userName, @Param("lastLogin") String lastLogin);
}
