package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import hexlet.code.Dto.TaskStatusDto;
import hexlet.code.Dto.UserDto;
import hexlet.code.config.SpringConfigForIT;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static hexlet.code.config.SpringConfigForIT.TEST_PROFILE;
import static hexlet.code.controller.TaskStatusController.ID;
import static hexlet.code.controller.TaskStatusController.TASK_STATUS_CONTROLLER_PATH;

import static hexlet.code.utils.TestUtils.TEST_TASK_STATUS;
import static hexlet.code.utils.TestUtils.TEST_TASK_STATUS_2;

import static hexlet.code.utils.TestUtils.asJson;
import static hexlet.code.utils.TestUtils.fromJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles(TEST_PROFILE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringConfigForIT.class)
public class TaskStatusControllerIT {
    private String fullPath = "/api" + TASK_STATUS_CONTROLLER_PATH;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private TestUtils utils;

    @AfterEach
    public void clear() {
        utils.tearDown();
    }

    @Test
    public void regTaskStatus() throws Exception {
        assertEquals(0, taskStatusRepository.count());
        utils.regDefaultTaskStatus().andExpect(status().isCreated());
        assertEquals(1, taskStatusRepository.count());
    }

    @Disabled("For now active only positive tests")
    @Test
    public void getTaskStatusByIdFails() throws Exception {
        utils.regDefaultTaskStatus();
        final TaskStatus expectedTaskStatus = taskStatusRepository.findAll().get(0);
        utils.perform(get(fullPath + ID, expectedTaskStatus.getId()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getAllTaskStatus() throws Exception {
        utils.regDefaultTaskStatus();
        final var response = utils.perform(get(fullPath))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final List<TaskStatus> taskStatuses = fromJson(response.getContentAsString(), new TypeReference<>() {
        });

        assertThat(taskStatuses).hasSize(1);
    }

    @Disabled("For now active only positive tests")
    @Test
    public void twiceRegTheSameTaskStatusFail() throws Exception {
        utils.regDefaultTaskStatus().andExpect(status().isCreated());
        utils.regDefaultTaskStatus().andExpect(status().isBadRequest());

        assertEquals(1, taskStatusRepository.count());
    }

    @Test
    public void updateTaskStatus() throws Exception {
        utils.regDefaultTaskStatus();

        final Long taskStatusId = taskStatusRepository.findByName(TEST_TASK_STATUS).get().getId();

        final var taskStatusDto = new TaskStatusDto(TEST_TASK_STATUS_2);

        final var updateRequest = put(fullPath + ID, taskStatusId)
                .content(asJson(taskStatusDto))
                .contentType(APPLICATION_JSON);

        utils.perform(updateRequest, TEST_TASK_STATUS).andExpect(status().isOk());

        assertTrue(taskStatusRepository.existsById(taskStatusId));
        assertEquals(null, taskStatusRepository.findByName(TEST_TASK_STATUS).orElse(null));
        assertNotNull(taskStatusRepository.findByName(TEST_TASK_STATUS_2).orElse(null));
    }

    @Test
    public void deleteTaskStatus() throws Exception {
        utils.regDefaultTaskStatus();

        final Long taskStatusId = taskStatusRepository.findByName(TEST_TASK_STATUS).get().getId();

        utils.perform(delete(fullPath + ID, taskStatusId), TEST_TASK_STATUS)
                .andExpect(status().isOk());

        assertEquals(0, taskStatusRepository.count());
    }

    @Disabled("For now active only positive tests")
    @Test
    public void deleteTaskStatusFails() throws Exception {
        utils.regDefaultTaskStatus();
        utils.regTaskStatus(new TaskStatusDto(
                TEST_TASK_STATUS_2
        ));

        final Long taskStatusId = taskStatusRepository.findByName(TEST_TASK_STATUS).get().getId();

        utils.perform(delete(fullPath + ID, taskStatusId), TEST_TASK_STATUS_2)
                .andExpect(status().isForbidden());

        assertEquals(2, taskStatusRepository.count());
    }
}
