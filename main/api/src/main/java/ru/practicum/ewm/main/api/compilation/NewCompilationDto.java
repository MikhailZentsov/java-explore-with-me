package ru.practicum.ewm.main.api.compilation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class NewCompilationDto {
    @NotBlank
    @Size(max = 50)
    private String title;
    private Boolean pinned = false;
    private Set<Long> events;
}
