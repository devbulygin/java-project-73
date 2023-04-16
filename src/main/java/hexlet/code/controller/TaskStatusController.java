package hexlet.code.controller;


import hexlet.code.Dto.TaskStatusDto;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.TaskStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static hexlet.code.controller.TaskStatusController.TASK_STATUS_CONTROLLER_PATH;
import static org.springframework.http.HttpStatus.CREATED;

@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + TASK_STATUS_CONTROLLER_PATH)
public class TaskStatusController {

    public static final String TASK_STATUS_CONTROLLER_PATH = "/statuses";

    public static final String ID = "/{id}";

    private final TaskStatusRepository taskStatusRepository;

    private final TaskStatusService taskStatusService;
    private final UserRepository userRepository;

    private final TaskRepository taskRepository;


    @ApiResponses(@ApiResponse(responseCode = "200"))
    @Operation(summary = "Get status")
    @GetMapping(ID)
    public TaskStatus getTaskStatusById(@PathVariable Long id) {
        return taskStatusRepository.getById(id);
    }

    @Operation(summary = "Get all Task Status")
    @ApiResponses(@ApiResponse(responseCode = "200", content = @Content(
            schema = @Schema(implementation = TaskStatus.class))))
    @GetMapping
    public List<TaskStatus> getAll() {
        return taskStatusRepository.findAll()
                .stream()
                .toList();
    }

    @Operation(summary = "Create task status")
    @ApiResponse(responseCode = "201", description = "Task Status created")
    @PostMapping
    @ResponseStatus(CREATED)
    public TaskStatus registerNewTaskStatus(@RequestBody @Valid final TaskStatusDto dto) {
        return taskStatusService.createNewTaskStatus(dto);
    }

    @Operation(summary = "Update task status")
    @PutMapping(ID)
    public TaskStatus updateTaskStatus(@RequestBody @Valid final TaskStatusDto dto, @PathVariable long id) {
        return taskStatusService.updateTaskStatus(id, dto);
    }

    @Operation(summary = "delete task status")
    @DeleteMapping(ID)
    public void deleteTaskStatus(@PathVariable long id) {
        TaskStatus taskStatus = taskStatusRepository.getById(id);

        Set<Task> tasks = taskStatus.getTasks();
        if (tasks != null) {
            throw new RuntimeException("Task status is connected to task, cannot delete");
        }

        taskStatusRepository.deleteById(id);
    }

}
