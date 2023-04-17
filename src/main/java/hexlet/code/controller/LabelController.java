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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static hexlet.code.controller.LabelController.LABEL_CONTROLLER_PATH;
import static org.springframework.http.HttpStatus.CREATED;

@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + LABEL_CONTROLLER_PATH)
public class LabelController {
    public static final String LABEL_CONTROLLER_PATH = "/labels";

    public static final String ID = "/{id}";
    private final LabelService labelService;
    private final LabelRepository labelRepository;

    @ApiResponses(@ApiResponse(responseCode = "200"))
    @Operation(summary = "Get label")
    @GetMapping(ID)
    public Label getLabelById(@PathVariable final Long id) {

        return labelRepository.getById(id);
    }

    @Operation(summary = "Get all labels")
    @ApiResponses(@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class))))
    @GetMapping
    public List<Label> getLabels() {
        return labelRepository.findAll()
                .stream()
                .toList();
    }

    @Operation(summary = "Create label")
    @ApiResponse(responseCode = "201", description = "Task Status created")
    @PostMapping
    @ResponseStatus(CREATED)
    public Label regNewLabel(@RequestBody @Valid LabelDto labelDto) {

        return labelService.createNewLabel(labelDto);
    }
    @Operation(summary = "Update label")
    @PutMapping(ID)
    public Label updateLabel(@RequestBody @Valid final LabelDto dto, @PathVariable long id) {
        return labelService.updateLabel(id, dto);
    }
    @Operation(summary = "delete label")
    @DeleteMapping(ID)
    public void deleteLabel(@PathVariable long id) {

        labelRepository.deleteById(id);
    }

}
