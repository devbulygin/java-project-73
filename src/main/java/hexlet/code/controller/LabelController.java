package hexlet.code.controller;

import hexlet.code.Dto.LabelDto;

import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.service.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static hexlet.code.controller.LabelController.LABEL_CONTROLLER_PATH;

@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + LABEL_CONTROLLER_PATH)
public class LabelController {
    public static final String LABEL_CONTROLLER_PATH = "/labels";

    public static final String ID = "/{id}";
    private final LabelService labelService;
    private final LabelRepository labelRepository;

    @Operation(summary = "get label by id")
    @ApiResponses(@ApiResponse(responseCode = "200"))
    @GetMapping(ID)
    public Label getLabelById(@PathVariable long id) {
        return labelRepository.findById(id).get();
    }

    @Operation(summary = "Get all labels")
    @ApiResponses(@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class))))
    @GetMapping
    public List<Label> getLabels() {
        return labelRepository.findAll()
                .stream()
                .toList();
    }

    @Operation(summary = "Update label")
    @PutMapping(ID)
    public Label updateLabel(@RequestBody @Valid final LabelDto dto, @PathVariable long id) {
        return labelService.updateLabel(id, dto);
    }

    @Operation(summary = "delete label")
    @DeleteMapping(ID)
    public void deleteLabel(@PathVariable long id) {
        Label label = labelRepository.getById(id);

        List<Task> tasks = label.getTasks();

        if (!tasks.isEmpty()) {
            throw new RuntimeException("label connected to task");
        }
        labelRepository.deleteById(id);
    }

}
