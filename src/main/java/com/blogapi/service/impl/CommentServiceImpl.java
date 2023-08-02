package com.blogapi.service.impl;

import com.blogapi.entity.Comment;
import com.blogapi.entity.Post;
import com.blogapi.exceptions.ResourceNotFoundException;
import com.blogapi.payload.CommentDto;
import com.blogapi.repository.CommentRepository;
import com.blogapi.repository.PostRepository;
import com.blogapi.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );
        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        CommentDto dto = new CommentDto();
        dto.setId(savedComment.getId());
        dto.setName(savedComment.getName());
        dto.setEmail(savedComment.getEmail());
        dto.setBody(savedComment.getBody());
        return dto;
    }

    public List<CommentDto> findCommentByPostId(long postid){
        Post post = this.postRepository.findById(postid).orElseThrow(
                () -> new ResourceNotFoundException(postid)
        );
        List<Comment> comments = this.commentRepository.findByPostId(postid);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public void deleteCommentById(long postId, long id) {
        Post post = this.postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );
        Comment comment = this.commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        this.commentRepository.deleteById(id);

    }

    @Override
    public CommentDto getCommentById(long postId, long id) {
        Post post = this.postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );
        Comment comment = this.commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
        Post post = this.postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );
        Comment comment = this.commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = this.commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    CommentDto mapToDto(Comment comment){
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        return dto;
    }
}
