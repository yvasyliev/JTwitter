package com.github.yvasyliev.model.dto;

import com.github.yvasyliev.validation.AvailableEmail;
import jakarta.validation.constraints.Email;

public record UpdateEmailForm(@Email @AvailableEmail String email) {
}
