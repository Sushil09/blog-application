package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Post Dto entity")
public class PostDto {
    private Long id;

    @Schema(description = "Blog post title")
    @Size(min = 2, message = "Post title should be atleast 2 characters")
    @NotEmpty
    private String title;

    @Schema(description = "Blog post description")
    @Size(min = 10, message = "Post description should be atleast 10 characters")
    @NotEmpty
    private String description;

    @Schema(description = "Blog post content")
    @NotEmpty
    private String content;

    private Set<CommentDto> comments;

    @Schema(description = "Blog post category")
    private Long categoryId;
}
