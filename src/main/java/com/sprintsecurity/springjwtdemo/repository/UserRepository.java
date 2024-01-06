package com.sprintsecurity.springjwtdemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprintsecurity.springjwtdemo.modle.Role;
import com.sprintsecurity.springjwtdemo.modle.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findByEmail(String username);

    User findByRole(Role admin);
}
