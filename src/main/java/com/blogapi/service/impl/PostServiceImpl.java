package com.blogapi.service.impl;

import com.blogapi.entity.Post;
import com.blogapi.exceptions.ResourceNotFoundException;
import com.blogapi.payload.PostDto;
import com.blogapi.repository.PostRepository;
import com.blogapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepo, ModelMapper modelMapper)
    {
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        // we are calling a method to convert dto to entity
        Post post = mapToEntity(postDto);

        // now send entity to repo layer, because repo will send that data to db
        Post savedPost = postRepo.save(post);

        // we are calling a method to convert entity to dto
        PostDto dto = mapToDto(post);
        return dto;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException(id)
        );

        PostDto dto = mapToDto(post);

        return dto;
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepo.findAll(pageable);
        List<Post> content = posts.getContent();
        List<PostDto> postDtos = content.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        return postDtos;

//        List<Post> posts = postRepo.findAll();
//        System.out.println(posts);
//        List<PostDto> postDtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
//        return postDtos;
    }

    @Override
    public void deletePost(long id) {
        // before delete check whether id is exists or not
        Post post = postRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException(id)
        );
        postRepo.deleteById(post.getId());
//        postRepo.deleteById(id);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        // before update check whether id is exists or not
        Post post = postRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException(id)
        );

        Post updatedContent = mapToEntity(postDto);
        updatedContent.setId(post.getId());
        System.out.println("updated content : "+updatedContent);
        Post updatedPostInfo = postRepo.save(updatedContent);
        PostDto dto = mapToDto(updatedPostInfo);

        return dto;
    }

    PostDto mapToDto(Post post){

        PostDto dto = modelMapper.map(post, PostDto.class);
        return dto;
        
//   in this method,convert entity obj into dto obj
//      create obj for postdto
//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
//        return dto;
    }

    Post mapToEntity(PostDto postDto){

        Post post = modelMapper.map(postDto, Post.class);
        return post;

//         in this method,convert dto obj into entity obj
//         create obj for entity class
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
//        return post;
    }
}
