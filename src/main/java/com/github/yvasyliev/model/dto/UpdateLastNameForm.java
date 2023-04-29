package com.github.yvasyliev.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UpdateLastNameForm(
        @NotEmpty
        @Size(min = 1, max = 32)
        String lastName
) {
}
