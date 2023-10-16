package com.vmish.taskmanager.repository;

import com.vmish.taskmanager.model.MyUser;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {
}
