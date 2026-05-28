package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Email
    @NotBlank(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Mobile Number cannot be empty")
    @Pattern(
        regexp = "^[0-9]{10}$",
        message = "Mobile Number must be 10 digits"
    )
    private String mobileNumber;
}
