package com.hampcode.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hampcode.model.entity.Status;
import com.hampcode.model.entity.Task;
import com.hampcode.service.TaskService;


@Controller
@RequestMapping({"/tasks", "/"})
public class TaskController {

	@Autowired
	private TaskService taskService;

	@GetMapping
	public String dashboard(Model model) {
		// display all Tasks
		List<Task> tasks = taskService.getAll();
		model.addAttribute("tasks", tasks);

		List<Status> statues = new ArrayList<Status>();
		Status.stream().forEach(statues::add);
		model.addAttribute("statues", statues);
		return "index";
	}

	@GetMapping("/{status}")
	public String displayByStatus(Model model, @PathVariable("status") String taskStatus) {
		if (taskStatus.equals(Status.OPEN.getTypeOfStatus())) {
			model.addAttribute("tasks", taskService.getTasksByStatus(Status.OPEN));
		} else if (taskStatus.equals(Status.CLOSED.getTypeOfStatus())) {
			model.addAttribute("tasks", taskService.getTasksByStatus(Status.CLOSED));
		} else if (taskStatus.equals(Status.REOPENED.getTypeOfStatus())) {
			model.addAttribute("tasks", taskService.getTasksByStatus(Status.REOPENED));
		}

		List<Status> statues = new ArrayList<Status>();
		Status.stream().forEach(statues::add);
		model.addAttribute("statues", statues);
		return "index";
	}

	@GetMapping("/task/{id}/{action}")
	public String handleStatus(@PathVariable("id") Long taskId, @PathVariable("action") String action,
			HttpServletRequest request) {
		Task task = taskService.getOne(taskId);
		if (action.equals("close")) {
			if (task.getStatus() == Status.OPEN) {
				taskService.closeTask(taskId);
			}
			if (task.getStatus() == Status.REOPENED) {
				taskService.closeTask(taskId);
			}
		}
		if (action.equals("reopen") && task.getStatus() == Status.CLOSED) {
			taskService.reopenTask(taskId);
		}
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@PostMapping("/create")
	public String createTask(Task task) {
		Task newTask = taskService.create(task);
		return "redirect:/";
	}

	@PostMapping("/update")
	public String updateTaskWithModal(Task task) {
		taskService.update(task.getId(), task);
		return "redirect:/";
	}

	@GetMapping("/findTask/{id}")
    @ResponseBody
    public Task findTask(@PathVariable("id") long taskId){
        return taskService.getOne(taskId);
    }
	
	@GetMapping("/task/{id}/delete")
    public String deleteTask(@PathVariable("id") long taskId, HttpServletRequest request) {
        taskService.deleteById(taskId);
        return "redirect:/";
    }


	
}
