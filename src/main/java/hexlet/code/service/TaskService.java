package hexlet.code.service;

import com.querydsl.core.types.Predicate;
import hexlet.code.Dto.TaskDto;

import hexlet.code.model.Task;


public interface TaskService {
    Task createNewTask(TaskDto taskDto);


    Task updateTask(Long id, TaskDto taskDto);

    Iterable<Task> getTasks(Predicate predicate);
    Iterable<Task> getTasks();
}
