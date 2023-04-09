package hexlet.code.service;

import hexlet.code.Dto.TaskDto;

import hexlet.code.model.Task;

import java.util.List;


public interface TaskService {
    Task createNewTask(TaskDto taskDto);

    Task updateTask(Long id, TaskDto taskDto);

    List<Task> findByParams(Long taskStatusId, Long executorId, Long labelId, boolean myTaskOnly);
}
