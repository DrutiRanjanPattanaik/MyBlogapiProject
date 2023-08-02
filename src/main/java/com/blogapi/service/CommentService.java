package com.blogapi.service;

import com.blogapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    public List<CommentDto> findCommentByPostId(long postid);

   void deleteCommentById(long postId, long id);

    CommentDto getCommentById(long postId, long id);

    CommentDto updateComment(long postId, long id, CommentDto commentDto);
}
// for a perticular id we are saving comments, thats why we need id.
