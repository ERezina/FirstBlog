package com.personal.diplom.controller;

import com.personal.diplom.Servise.*;
import com.personal.diplom.api.request.AddPostRequest;
import com.personal.diplom.api.request.CommentRequest;
import com.personal.diplom.api.request.VotesRequest;
import com.personal.diplom.api.response.PostsCountResponse;
import com.personal.diplom.api.response.ResponseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class ApiPostController {

    private final PostServise postServise;
    private final PostSearchServise postSearchServise;
    private final PostSearchByDateServise postSearchByDateServise;
    private final PostSearchByTagService postSearchByTagService;
    private final UserService userService;
    private final CommentService commentService;
    private final LikeService likeService;

    public ApiPostController(PostServise postServise, PostSearchServise postSearchServise, PostSearchByDateServise postSearchByDateServise, PostSearchByTagService postSearchByTagService, UserService userService, CommentService commentService, LikeService likeService) {
        this.postServise = postServise;
        this.postSearchServise = postSearchServise;
        this.postSearchByDateServise = postSearchByDateServise;
        this.postSearchByTagService = postSearchByTagService;
        this.userService = userService;
        this.commentService = commentService;
        this.likeService = likeService;
    }

    @RequestMapping(value = "/api/post", method = RequestMethod.GET)
    public PostsCountResponse setPost(@RequestParam("offset") int offset,@RequestParam("limit") int limit, @RequestParam("mode") String mode){
    //    PostsCountResponse postsCountResponse = new PostsCountResponse();
        return postServise.getPostsCount(offset,limit, mode);
    }

    @RequestMapping(value = "/api/post", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ResponseResult> setPost(@RequestBody AddPostRequest addPostRequest
                                                ,Principal principal){
        if (principal == null){
            return ResponseEntity.ok(new ResponseResult());
        }
         return ResponseEntity.ok(postServise.addPost(addPostRequest, principal));
    }

    @RequestMapping(value = "/api/comment", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ResponseResult> addCommentToPost(@RequestBody CommentRequest commentRequest
            ,Principal principal){
        if (principal == null){
            return ResponseEntity.ok(new ResponseResult());
        }
        return ResponseEntity.ok(commentService.addComment(commentRequest, principal));

    }

    @RequestMapping(value = "/api/post/search", method = RequestMethod.GET)
  //  @PreAuthorize("hasAuthority('user:moderate')")
    public PostsCountResponse postSearch(@RequestParam("offset") int offset,@RequestParam("limit") int limit, @RequestParam(value = "query",required = false) String query){
        return postSearchServise.getPostsCount(offset,limit,query);
    }

    @RequestMapping(value = "/api/post/byDate", method = RequestMethod.GET)
    public PostsCountResponse postSearchByDate(@RequestParam("offset") int offset,@RequestParam("limit") int limit, @RequestParam(value = "date",required = false) String query){
        return postSearchByDateServise.getPostsCount(offset,limit,query);
    }

    @RequestMapping(value = "/api/post/byTag", method = RequestMethod.GET)
    public PostsCountResponse postSearchByTag(@RequestParam("offset") int offset,@RequestParam("limit") int limit, @RequestParam(value = "tag",required = false) String tag){

        return postSearchByTagService.getPostsCount(offset,limit,tag);
    }


    @RequestMapping(value = "/api/post/{ID}", method = RequestMethod.GET)
    public ResponseEntity postSearchByID(@PathVariable("ID") String id){

        return postServise.getPostById(Integer.valueOf(id));
    }

    @RequestMapping(value = "/api/post/{ID}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseResult> postEdit(@PathVariable("ID") int id
                                ,@RequestBody AddPostRequest addPostRequest
                                ,Principal principal){
        ResponseResult responseResult = new ResponseResult();
        responseResult = postServise.editPost(id,addPostRequest,principal);
        return ResponseEntity.ok(responseResult);
    }
    @RequestMapping(value = "/api/post/moderation", method = RequestMethod.GET)
    public int postListModeration(){
        return 45;
    }

    @RequestMapping(value = "/api/post/my", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<PostsCountResponse> postListMy(@RequestParam("offset") int offset
                                                        ,@RequestParam("limit") int limit
                                                        ,@RequestParam("status") String status
                                                        , Principal principal){
        if (principal == null){
            return ResponseEntity.ok(new PostsCountResponse());
        }
        return ResponseEntity.ok(postSearchServise.getPostsUser(offset,limit,status,userService.getUserId(principal.getName())));
    }

    @RequestMapping(value = "/api/post/like", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ResponseResult> postLike(@RequestBody VotesRequest votesRequest, Principal principal){

        return ResponseEntity.ok(likeService.addLike(votesRequest.getPost_id(),principal,1));
    }



    @RequestMapping(value = "/api/post/dislike", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ResponseResult> postDislike(@RequestBody VotesRequest votesRequest, Principal principal){
        return ResponseEntity.ok(likeService.addLike(votesRequest.getPost_id(),principal,-1));
    }

}