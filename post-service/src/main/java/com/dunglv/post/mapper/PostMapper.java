package com.dunglv.post.mapper;

import com.dunglv.post.dto.response.PostResponse;
import com.dunglv.post.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostResponse toPostResponse(Post post);
}