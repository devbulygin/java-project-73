//package hexlet.code.controller;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import hexlet.code.Dto.TaskDto;
//
//import hexlet.code.config.SpringConfigForIT;
//import hexlet.code.model.Task;
//
//import hexlet.code.model.User;
//import hexlet.code.repository.TaskRepository;
//import hexlet.code.repository.TaskStatusRepository;
//import hexlet.code.repository.UserRepository;
//import hexlet.code.utils.TestUtils;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//
//import static hexlet.code.config.SpringConfigForIT.TEST_PROFILE;
//import static hexlet.code.controller.TaskController.TASK_CONTROLLER_PATH;
//import static hexlet.code.controller.TaskController.ID;
//import static hexlet.code.utils.TestUtils.TEST_TASK_STATUS;
//import static hexlet.code.utils.TestUtils.TEST_TASK_STATUS_2;
//import static hexlet.code.utils.TestUtils.asJson;
//import static hexlet.code.utils.TestUtils.fromJson;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
//import static org.springframework.http.MediaType.APPLICATION_JSON;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@AutoConfigureMockMvc
//@ActiveProfiles(TEST_PROFILE)
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringConfigForIT.class)
//public class TaskControllerIT {
//    private String fullPath = "/api" + TASK_CONTROLLER_PATH;
//
//    @Autowired
//    private TaskRepository taskRepository;
//
//    @Autowired
//    private TaskStatusRepository taskStatusRepository;
//
//    @Autowired
//    private TestUtils utils;
//    @Autowired
//    private UserRepository userRepository;
//
//    @BeforeEach
//    public void before() throws Exception {
//        utils.regDefaultUser();
//        utils.regDefaultTaskStatus();
//    }
//
//    @AfterEach
//    public void clear() {
//        utils.tearDown();
//    }
//
//
////    private TaskDto buildTask(final long authorId, final long executorId, final long taskStatusId) {
////        return new TaskDto(
////                "fix checkstyle error",
////        "",
////                taskStatusId,
////                authorId,
////                executorId
////                );
////    }
//
////    @Test
////    public void createNewTask() {
////        final long authorId =
////    }
//
////    private User createNewAuthor() {
////        return userRepository.save(User.builder()
////                        .firstName("John")
////                        .lastName("Cena")
////                        .email(utils.getUserByEmail())
////                        .password()
////                .build())
////    }
////
//
//
//
//
//
//
//
//}
