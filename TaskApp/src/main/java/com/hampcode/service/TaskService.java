package com.hampcode.service;

import java.util.List;

import com.hampcode.model.entity.Status;
import com.hampcode.model.entity.Task;

public interface TaskService extends CrudService<Task, Long> {

	void closeTask(Long id);

	void reopenTask(Long id);

	List<Task> getTasksByStatus(Status status);
}
