package com.blogapi.controller;

import com.blogapi.payload.CommentDto;
import com.blogapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService)
    {
        this.commentService = commentService;
    }

//    http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId, @RequestBody CommentDto commentDto){
        CommentDto commentDtos = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(commentDtos, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> findCommentByPostId(@PathVariable("postId") long postId){
       return  this.commentService.findCommentByPostId(postId);
    }

//    http://localhost:8080/api/posts/1/comments/1
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable("postId") long postId,@PathVariable("id") long id){
        this.commentService.deleteCommentById(postId,id);
        return new ResponseEntity<>("Comment is deleted sucessfully !!",HttpStatus.OK);
    }

//    http://localhost:8080/api/posts/1/comments/1
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") long postId,@PathVariable("id") long id){
        CommentDto dto = this.commentService.getCommentById(postId,id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

//    http://localhost:8080/api/posts/1/comments/1
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") long postId,
                                                    @PathVariable("id") long id,
                                                    @RequestBody CommentDto commentDto){
        CommentDto updatedComment = this.commentService.updateComment(postId,id,commentDto);
        return new ResponseEntity<>(updatedComment,HttpStatus.OK);
    }
}
