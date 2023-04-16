package hexlet.code.service;

import com.querydsl.core.types.Predicate;
import hexlet.code.Dto.TaskDto;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.NoSuchElementException;


@Service
@Transactional
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    EntityManager entityManager;

    private final UserService userService;

    TaskRepository taskRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final LabelRepository labelRepository;
    private final UserRepository userRepository;



    public Iterable<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Iterable<Task> getTasks(Predicate predicate) {
        return taskRepository.findAll(predicate);
    }
    @Override

    public Task createNewTask(final TaskDto taskDto) {
        final Task task = new Task();
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        final User author = userService.getCurrentUser();
        task.setAuthor(author);
        if (taskDto.getExecutorId() != null) {
            final User executor = userRepository.findById(taskDto.getExecutorId())
                    .orElseThrow(() -> new RuntimeException("Executor not found"));
            task.setExecutor(executor);
        }
        final TaskStatus taskStatus = taskStatusRepository.findById(taskDto.getTaskStatusId())
                .orElseThrow(() -> new RuntimeException("TaskStatus not found"));
        task.setTaskStatus(taskStatus);
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(final Long id, final TaskDto taskDto) {
        final Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found"));
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        final User author = userService.getCurrentUser();
        task.setAuthor(author);
        if (taskDto.getExecutorId() != null) {
            final User executor = userRepository.findById(taskDto.getExecutorId())
                    .orElseThrow(() -> new NoSuchElementException("Executor not found"));
            task.setExecutor(executor);
        }
        final TaskStatus taskStatus = taskStatusRepository.findById(taskDto.getTaskStatusId())
                .orElseThrow(() -> new NoSuchElementException("TaskStatus not found"));
        task.setTaskStatus(taskStatus);
        return taskRepository.save(task);
    }
}
