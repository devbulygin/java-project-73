package hexlet.code.service;

import hexlet.code.Dto.TaskStatusDto;
import hexlet.code.model.TaskStatus;

public interface TaskStatusService {

    TaskStatus createNewTaskStatus(TaskStatusDto taskStatusDto);
    TaskStatus updateTaskStatus(Long id, TaskStatusDto taskStatusDto);
}
