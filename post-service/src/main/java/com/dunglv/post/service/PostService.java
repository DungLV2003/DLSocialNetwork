package com.dunglv.post.service;

import com.dunglv.post.dto.request.PostRequest;
import com.dunglv.post.dto.response.PageResponse;
import com.dunglv.post.dto.response.PostResponse;
import com.dunglv.post.dto.response.UserProfileResponse;
import com.dunglv.post.entity.Post;
import com.dunglv.post.mapper.PostMapper;
import com.dunglv.post.repository.Interface.PostRepository;
import com.dunglv.post.repository.httpclient.ProfileClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {
    PostRepository postRepository;
    PostMapper postMapper;
    DateTimeFormatter dateTimeFormatter;
    ProfileClient profileClient;

    public PostResponse createPost(PostRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Post post = Post.builder()
                .content(request.getContent())
                .userId(authentication.getName())
                .createdDate(Instant.now())
                .modifiedDate(Instant.now())
                .build();

        post = postRepository.save(post);
        return postMapper.toPostResponse(post);
    }

    public PageResponse<PostResponse> getMyPosts(int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();


        UserProfileResponse userProfile = null;
        try{
            userProfile = profileClient.getProfile(userId).getResult();
        } catch (Exception e) {
            log.error("Error fetching user profile for userId: {}", userId, e);
        }


        Sort sort = Sort.by("createdDate").descending();

        Pageable pageable = PageRequest.of(page - 1, size, sort);

        var pageData = postRepository.findAllByUserId(userId, pageable);

        String username = userProfile != null ? userProfile.getUsername() : "Unknown User";


        var postList = pageData.getContent().stream().map(post -> {
            var postResponse = postMapper.toPostResponse(post);
            postResponse.setCreated(dateTimeFormatter.format(post.getCreatedDate()));
            postResponse.setUserName(username);
            return postResponse;
        }).toList();

        return PageResponse.<PostResponse>builder()
                .currentPage(page)
                .totalPages(pageData.getTotalPages())
                .pageSize(size)
                .totalElements(pageData.getTotalElements())
                .data(postList)
                .build();
    }
}
