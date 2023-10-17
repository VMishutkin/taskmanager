package com.vmish.taskmanager.repository;

import com.vmish.taskmanager.model.MyUser;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByUsername(String username);
    @Query(value = "SELECT username FROM my_user", nativeQuery = true)
    List<String> getAllUserNames();
}
