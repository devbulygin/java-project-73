package hexlet.code.controller;

import com.querydsl.core.types.Predicate;
import hexlet.code.Dto.TaskDto;
import hexlet.code.model.Task;
import hexlet.code.repository.TaskRepository;

import hexlet.code.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.security.access.prepost.PreAuthorize;

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

import static hexlet.code.controller.TaskController.TASK_CONTROLLER_PATH;
import static org.springframework.http.HttpStatus.CREATED;

@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + TASK_CONTROLLER_PATH)
public class TaskController {
    public static final String TASK_CONTROLLER_PATH = "/tasks";

    public static final String ID = "/{id}";

    private final TaskRepository taskRepository;

    private final TaskService taskService;

    private static final String ONLY_OWNER_BY_ID = """
            @userRepository.findById(#id).get().getEmail() == authentication.getName()
        """;
    private static final String TASK_OWNER =
            "@taskRepository.findById(#id).get().getAuthor().getEmail() == authentication.getName()";

    @Operation(summary = "Get task by id")
    @ApiResponses(@ApiResponse(responseCode = "200"))
    @GetMapping(ID)
    public Task getTaskByID(@PathVariable long id) {
        return taskRepository.getById(id);
    }

    @ApiResponses(@ApiResponse(responseCode = "200", content =
        @Content(schema = @Schema(implementation = Task.class))))
    @Operation(summary = "Get all tasks")
    @GetMapping
    public  Iterable<Task> getAllTask(@Parameter(description = "Predicate based on query params")
                                      @QuerydslPredicate(root = Task.class) Predicate predicate) {
        return predicate == null ? taskRepository.findAll() : taskRepository.findAll(predicate);
    }



    @Operation(summary = "Create task")
    @ApiResponse(responseCode = "201", description = "Task  created")
    @PostMapping
    @ResponseStatus(CREATED)
    public Task registerNewTask(@RequestBody @Valid final TaskDto taskDto) {
        return taskService.createNewTask(taskDto);
    }

    @Operation(summary = "update task")
    @PutMapping(ID)
    public Task updateTask(@RequestBody @Valid final TaskDto taskDto, @PathVariable long id) {
        return taskService.updateTask(id, taskDto);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Task delete"),
        @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @Operation(summary = "Delete task")
    @PreAuthorize(TASK_OWNER)
    @DeleteMapping(ID)
    public void deleteTaskById(@PathVariable final Long id) {
        taskRepository.deleteById(id);
    }

}
