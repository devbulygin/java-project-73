package hexlet.code.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String description;


    @NotNull(message = "Task status is mandatory")
    private Long taskStatusId;

    @NotNull(message = "Author is mandatory")
    private Long authorId;

    private Long executorId;

    private Set<Long> labelIds;

}
