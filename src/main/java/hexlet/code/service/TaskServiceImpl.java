package hexlet.code.service;

import hexlet.code.Dto.TaskDto;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    EntityManager entityManager;

    private final UserService userService;

    TaskRepository taskRepository;
    private final TaskStatusRepository taskStatusRepository;

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


    @Override
    public List<Task> findByParams(Long taskStatusId, Long executorId, Long labelId, boolean myTaskOnly) {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);
        Root<Task> taskRoot = criteriaQuery.from(Task.class);

        List<Predicate> predicates = new ArrayList<>();

        if (taskStatusId != null) {
            predicates.add(criteriaBuilder.equal(taskRoot.get("taskStatusId"), taskStatusId));
        }

        if (executorId != null) {
            predicates.add(criteriaBuilder.equal(taskRoot.get("executorId"), executorId));
        }

        if (labelId != null) {
            predicates.add(criteriaBuilder.equal(taskRoot.get("labelId"), labelId));
        }

        if (myTaskOnly == true) {
            Authentication authentication = null;
            User currentUser = (User) authentication.getPrincipal();
            predicates.add(criteriaBuilder.equal(taskRoot.get("authorId"), currentUser.getId()));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
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

        return Task.builder()
                .name(taskDto.getName())
                .description(taskDto.getDescription())
                .taskStatus(taskStatus)
                .author(author)
                .executor(executor)
                .build();
    }
}
