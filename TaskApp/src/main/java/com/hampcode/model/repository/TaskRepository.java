package com.hampcode.model.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hampcode.model.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	@Transactional
	@Modifying
	@Query(value = "UPDATE tasks SET status='CLOSED' WHERE id = ?", nativeQuery = true)
	void closeTask(Long id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE tasks SET status='REOPENED' WHERE id = ?", nativeQuery = true)
	void reopenTask(Long id);
}
