package com.blogapi.controller;

import com.blogapi.payload.PostDto;
import com.blogapi.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

//    http://localhost:8080/api/posts
    @PreAuthorize("hasRole('ADMIN')")
@PostMapping
public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult){ //at the time of post/insert/save status code 201
    if(bindingResult.hasErrors()){
        return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    PostDto savedDto = postService.createPost(postDto);
    return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
}


//    http://localhost:8080/api/posts?id=1
//@PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){ // at fetching time status code 200
//        System.out.println(id);
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // url for pagination http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=title&sortDir=desc
//    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<PostDto> getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    )
    {
        // at fetching time status code 200
        //MissingServletRequestParameterException(required = true)
        List<PostDto> allPosts = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
//        int size = postdtos.size();
//        System.out.println("size of dto : "+size);
        return allPosts;
    }

//     http://localhost:8080/api/posts/1
//    @DeleteMapping("/{id}")
//@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "/api/posts/{id}", method = RequestMethod.DELETE)
public ResponseEntity<String> deletePost(@PathVariable("id") long id){
    postService.deletePost(id);
    return new ResponseEntity<>("Post is deleted...",HttpStatus.OK);
}

//    http://localhost:8080/api/posts/1
//    @PreAuthorize("hasRole('ADMIN')")
@PutMapping("/{id}")
public ResponseEntity<PostDto> updatePost(@PathVariable("id") long id,@RequestBody PostDto postDto){ // status = 200
    PostDto dto = postService.updatePost(id, postDto);
    return new ResponseEntity<>(dto,HttpStatus.OK);
}
}


// for query parameter, we will use @RequestParam
// for path parameter, we will use @PathVariable
