package hexlet.code.controller;

import hexlet.code.Dto.UserDto;
import hexlet.code.model.Task;
import hexlet.code.model.User;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.UserService;
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
import java.util.Optional;

import static hexlet.code.controller.UserController.USER_CONTROLLER_PATH;
import static org.springframework.http.HttpStatus.CREATED;

@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + USER_CONTROLLER_PATH)
public class UserController {
    public static final String USER_CONTROLLER_PATH = "/users";
    public static final String ID = "/{id}";

    private final UserService userService;
    private final UserRepository userRepository;

    private final TaskRepository taskRepository;


    @Operation(summary = "Get user by id")
    @ApiResponses(@ApiResponse(responseCode = "200"))
    @GetMapping(ID)
    public User getUserById(@PathVariable long id) {
        return userRepository.getById(id);
    }

    @Operation(summary = "Get all users")
    @ApiResponses(@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class))))
    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll()
                .stream()
                .toList();
    }

    @Operation(summary = "Create user")
    @ApiResponse(responseCode = "201", description = "User created")
    @PostMapping
    @ResponseStatus(CREATED)
    public User registerNewUser(@RequestBody @Valid final UserDto dto) {
        return userService.createNewUser(dto);
    }


    @Operation(summary = "Update user")
    @PutMapping(ID)
    public User updateUser(@RequestBody @Valid final UserDto dto, @PathVariable long id) {
        return userService.updateUser(id, dto);
    }

    @Operation(summary = "delete user")
    @DeleteMapping(ID)
    public void deleteUser(@PathVariable long id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();

        List<Task> tasksAuthor = user.getTasksAuthor();
        List<Task> tasksExecutor = user.getTasksExecutor();
        if (tasksAuthor == null) {
            throw new RuntimeException("User is task author, cannot delete");
        }

        if (tasksExecutor == null) {
            throw new RuntimeException("User has task, cannot delete");
        }

        userRepository.deleteById(id);

    }

}
