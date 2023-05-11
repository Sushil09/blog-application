package com.springboot.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;

    @Size(min = 2, message = "Post title should be atleast 2 characters")
    @NotEmpty
    private String title;

    @Size(min = 10, message = "Post description should be atleast 10 characters")
    @NotEmpty
    private String description;

    @NotEmpty
    private String content;

    private Set<CommentDto> comments;

    private Long categoryId;
}
