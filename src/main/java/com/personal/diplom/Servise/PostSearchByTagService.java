package com.personal.diplom.Servise;

import com.personal.diplom.Servise.util.PostResponseWork;
import com.personal.diplom.api.response.PostResponse;
import com.personal.diplom.api.response.PostsCountResponse;
import com.personal.diplom.model.Post;
import com.personal.diplom.model.Tag;
import com.personal.diplom.repository.PostRepository;
import com.personal.diplom.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PostSearchByTagService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TagRepository tagRepository;

    private ArrayList<PostResponse> getAllPosts(int offset,int limit, String query ){
        Pageable elem = PageRequest.of(offset,limit);
        Page<Post> allProductsSortedByName;
        elem  = PageRequest.of(offset,limit,Sort.by("date").descending());
        allProductsSortedByName = postRepository.findSearchPostPagination(elem,query);

        ArrayList<PostResponse> postResponses = new ArrayList<PostResponse>();
        PostResponseWork postResponseWork = new PostResponseWork();
        for(Post post: allProductsSortedByName.getContent()){
            postResponses.add(postResponseWork.copyToPostResponse(post));
        }
        return postResponses;
    }

    public PostsCountResponse getPostsCount(int offset,int limit, String query  ){
        PostsCountResponse postsCountResponse = new PostsCountResponse();
        try {
            postsCountResponse.addPost(getAllPosts(offset,limit,query));
            postsCountResponse.setCount(postsCountResponse.getPosts().size());
        }
        catch (Exception e){

        }

        return postsCountResponse;
    }
    private ArrayList<PostResponse> getAllPostsByTag(int offset,int limit, Tag tag ){
        Pageable elem = PageRequest.of(offset,limit);
        Page<Post> allProductsSortedByName;
        elem  = PageRequest.of(offset,limit,Sort.by("date").descending());
        allProductsSortedByName = postRepository.findPostByTag(elem,tag.getId());

        ArrayList<PostResponse> postResponses = new ArrayList<PostResponse>();
        PostResponseWork postResponseWork = new PostResponseWork();
        for(Post post: allProductsSortedByName.getContent()){
            postResponses.add(postResponseWork.copyToPostResponse(post));
        }
        return postResponses;
    }
    public PostsCountResponse getPostsCountByTag(int offset,int limit, String tagName  ){
        PostsCountResponse postsCountResponse = new PostsCountResponse();
        Tag tag = tagRepository.findFirstByName(tagName).get();
        try {
            postsCountResponse.addPost(getAllPostsByTag(offset,limit,tag));
            postsCountResponse.setCount(postsCountResponse.getPosts().size());
        }
        catch (Exception e){

        }

        return postsCountResponse;
    }

}