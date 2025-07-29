package com.vighnesh.library.pojo;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Member {
	public enum Gender {
        MALE,
        FEMALE
    }
	private Integer memberId;
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
    private String name;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Email cannot exceed 255 characters")
    private String email;
    @NotNull(message = "Mobile number cannot be empty")
    @Positive(message = "Mobile number must be positive")
    @Digits(integer = 10, fraction = 0, message = "Mobile number must be a 10-digit number")
    private Long mobile;
    @NotBlank(message = "Address cannot be empty")
    @Size(min = 5, max = 500, message = "Address must be between 5 and 500 characters")
    private String address;
    @NotNull(message = "Gender cannot be empty")
    private Gender gender;
}
