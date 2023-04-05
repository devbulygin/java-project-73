package hexlet.code.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import hexlet.code.Dto.TaskStatusDto;
import hexlet.code.Dto.UserDto;
import hexlet.code.model.User;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import hexlet.code.component.JWTHelper;

import java.util.Map;

import static hexlet.code.controller.TaskStatusController.TASK_STATUS_CONTROLLER_PATH;
import static hexlet.code.controller.UserController.USER_CONTROLLER_PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
public class TestUtils {
    private String userPath = "/api" + USER_CONTROLLER_PATH;

    private String taskStatusPath = "/api" + TASK_STATUS_CONTROLLER_PATH;

    public static final String TEST_USERNAME = "email@email.com";
    public static final String TEST_USERNAME_2 = "email2@email.com";

    public static final String TEST_TASK_STATUS = "new task";
    public static final String TEST_TASK_STATUS_2 = "in work";

    public static final String TEST_TASK_NAME = "fix checkstyle error";
    public static final String TEST_TASK = "in work";



    private final UserDto testRegistrationUserDto = new UserDto(
            TEST_USERNAME,
            "fname",
            "lname",
            "pwd"
    );

    private final TaskStatusDto testRegistrationTaskStatusDto = new TaskStatusDto(
            TEST_TASK_STATUS);

//    private final TaskDto testRegistrationTaskDto = new TaskDto(
//            TEST_TASK_NAME,
//
//
//    );

    public UserDto getTestRegistrationDto() {
        return testRegistrationUserDto;
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTHelper jwtHelper;

    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email).get();
    }

    public ResultActions regDefaultUser() throws Exception {
        return regUser(testRegistrationUserDto);
    }

    public ResultActions regDefaultTaskStatus() throws Exception {
        return regTaskStatus(testRegistrationTaskStatusDto);
    }

//    public ResultActions regDefaultTask() throws Exception {
//        return regTask(testRegistrationTaskDto);
//    }

    public ResultActions regTaskStatus(final TaskStatusDto taskStatusDto) throws Exception {
        final var request = post(taskStatusPath)
                .content(asJson(taskStatusDto))
                .contentType(APPLICATION_JSON);

        return perform(request);
    }


    public ResultActions regUser(final UserDto userDto) throws Exception {
        final var request = post(userPath)
                .content(asJson(userDto))
                .contentType(APPLICATION_JSON);



        return perform(request);
    }

    public ResultActions perform(final MockHttpServletRequestBuilder request, final String byUser) throws Exception {
        final String token = jwtHelper.expiring(Map.of("username", byUser));
        request.header(AUTHORIZATION, token);

        return perform(request);
    }

    public ResultActions perform(final MockHttpServletRequestBuilder request) throws Exception {
        return mockMvc.perform(request);
    }

    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();
    @Autowired
    private TaskRepository taskRepository;

    public static String asJson(final Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }

    public static <T> T fromJson(final String json, final TypeReference<T> to) throws JsonProcessingException {
        return MAPPER.readValue(json, to);
    }

    public void tearDown() {
        taskStatusRepository.deleteAll();
//        postRepository.deleteAll();
        userRepository.deleteAll();
        taskRepository.deleteAll();
    }
}
