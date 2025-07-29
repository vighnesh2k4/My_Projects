package com.vighnesh.library.pojo;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class Book {
	public enum Status {
        ACTIVE,
        INACTIVE
    }
    public enum Availability {
        AVAILABLE,
        ISSUED
    }
    private Integer bookid;
    @NotBlank(message = "Title cannot be empty")
    @Size(min = 2, max = 255, message = "Title must be between 2 and 255 characters")
    private String title;
    @NotBlank(message = "Author cannot be empty")
    @Size(min = 2, max = 255, message = "Author must be between 2 and 255 characters")
    private String author;
    @NotBlank(message = "Category cannot be empty")
    @Size(min = 2, max = 100, message = "Category must be between 2 and 100 characters")
    private String category;
    private Status status;
    private Availability availability;
}
