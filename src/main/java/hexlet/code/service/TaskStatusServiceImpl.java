package hexlet.code.service;

import hexlet.code.Dto.TaskStatusDto;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor

public class TaskStatusServiceImpl implements TaskStatusService {
    private final TaskStatusRepository taskStatusRepository;
    @Override
    public TaskStatus createNewTaskStatus(TaskStatusDto taskStatusDto) {
        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setName(taskStatusDto.getName());
        return taskStatusRepository.save(taskStatus);
    }

    @Override
    public TaskStatus updateTaskStatus(Long id, TaskStatusDto taskStatusDto) {
        TaskStatus taskStatus = taskStatusRepository.getById(id);
        taskStatus.setName(taskStatusDto.getName());
        return taskStatusRepository.save(taskStatus);
    }
}
