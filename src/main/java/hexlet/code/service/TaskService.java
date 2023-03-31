package hexlet.code.service;

import hexlet.code.Dto.TaskDto;
import hexlet.code.Dto.TaskStatusDto;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;

public interface TaskService {
    Task createNewTask(TaskDto taskDto);

    Task updateTask(Long id, TaskDto taskDto);
}
