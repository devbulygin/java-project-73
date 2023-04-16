package hexlet.code.service;

import hexlet.code.Dto.TaskDto;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    EntityManager entityManager;

    private final UserService userService;

    TaskRepository taskRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final LabelRepository labelRepository;

    @Override
    public Task createNewTask(TaskDto taskDto) {
        final Task newTask = fromDto(taskDto);
        return taskRepository.save(newTask);
    }

    @Override
    public Task updateTask(Long id, TaskDto taskDto) {
        final Task task = taskRepository.findById(id).get();
        merge(task, taskDto);
        return taskRepository.save(task);
    }


    private void merge(final Task task, final TaskDto taskDto) {
        final Task newTask = fromDto(taskDto);
        task.setName(newTask.getName());
        task.setDescription(newTask.getDescription());
        task.setTaskStatus(newTask.getTaskStatus());
        task.setAuthor(newTask.getAuthor());
        task.setExecutor(newTask.getExecutor());
    }


    private Task fromDto(final TaskDto taskDto) {
        final User author = userService.getUserById(taskDto.getAuthorId());
        final User executor = userService.getUserById(taskDto.getExecutorId());
        final TaskStatus taskStatus = taskStatusRepository.getById(taskDto.getTaskStatusId());
        final Set<Label> labels = taskDto.getLabelIds().stream()
                .map(id -> labelRepository.getById(id))
                .collect(Collectors.toSet());

        return Task.builder()
                .name(taskDto.getName())
                .description(taskDto.getDescription())
                .taskStatus(taskStatus)
                .author(author)
                .executor(executor)
                .labels(labels)
                .build();
    }
}
