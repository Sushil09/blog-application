package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private List<PostDto> postList;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private long totalPages;
    private boolean isLast;
}
