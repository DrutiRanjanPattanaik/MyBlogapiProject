package com.blogapi.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private Long id;

    @NotEmpty(message = "Title should not be empty.")
    @Size(min = 2,message = "Title should be more than 2 char.")
    private String title;

    @NotEmpty(message = "Description should not be empty.")
    @Size(min = 4,message = "Description should be more than 4 char.")
    private String description;

    @NotBlank(message = "Content should not be empty.")
    private String content;
}