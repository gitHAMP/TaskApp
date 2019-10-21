package com.hampcode.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hampcode.model.entity.Status;
import com.hampcode.model.entity.Task;
import com.hampcode.model.repository.TaskRepository;
import com.hampcode.service.TaskService;


@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Transactional
	@Override
	public Task create(Task entity) {
		return taskRepository.save(entity);
	}

	@Override
	public List<Task> getAll() {
		return taskRepository.findAll();
	}

	@Override
	public Task getOne(Long id) {
		Optional<Task> taskOptional = taskRepository.findById(id);
		if (!taskOptional.isPresent()) {
			throw new RuntimeException("Task Not Found!");
		}
		return taskOptional.get();
	}

	@Transactional
	@Override
	public void deleteById(Long id) {
		taskRepository.delete(getOne(id));
	}

	@Override
	public void closeTask(Long id) {
		taskRepository.closeTask(id);
	}

	@Override
	public void reopenTask(Long id) {
		taskRepository.reopenTask(id);
	}

	@Transactional
	@Override
	public Task update(Long id, Task entity) {
		Task task = getOne(id);
		task.setTitle(entity.getTitle());
		task.setContent(entity.getContent());
		task.setStatus(entity.getStatus());
		taskRepository.save(task);
		return task;
	}

	@Override
	public List<Task> getTasksByStatus(Status status) {
		return getAll()
				.stream()
				.filter(task-> task.getStatus().equals(status))
				.collect(Collectors.toList());
	}

}
