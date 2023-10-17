package com.vmish.taskmanager.repository;

import com.vmish.taskmanager.model.Task;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByUsername(String name);
}
