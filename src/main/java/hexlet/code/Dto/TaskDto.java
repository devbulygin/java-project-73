package hexlet.code.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    @NotBlank
    @Size(min = 1)
    private String name;

    private String description;

    @NotBlank
    @Size(min = 1)
    private Long taskStatusId;

    @NotBlank
    @Size(min = 1)
    private Long authorId;

    private Long executorId;

    private List<Long> labelIds;

}
