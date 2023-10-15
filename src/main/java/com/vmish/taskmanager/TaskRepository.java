package com.vmish.taskmanager;

import com.vmish.taskmanager.model.Task;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
}
