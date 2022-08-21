package com.personal.diplom.repository;

import com.personal.diplom.model.ModerationStatusType;
import com.personal.diplom.model.Post;
import com.personal.diplom.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post,Integer> {

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 AND moderation_status = 'ACCEPTED' AND date <= sysdate() ",
            countQuery = "SELECT count(*) FROM posts"+
                        " WHERE is_active = 1  " +
                        "AND moderation_status = 'ACCEPTED' AND date <= sysdate()  ",
            nativeQuery = true)
    Page<Post> findAllPostPagination(Pageable pageable);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            " AND moderation_status = :status "
            + " AND ( moderator_id is null or moderator_id = :mod_id )"+
            " AND date <= sysdate() ",
            countQuery = "SELECT count(*) FROM posts WHERE is_active = 1 "+
                    " AND moderation_status = :status " +
                    " AND ( moderator_id is null OR moderator_id = :mod_id ) " +
                    " AND date <= sysdate()  ",
            nativeQuery = true)
    Page<Post> findAllPostPaginationModerator(Pageable pageable, @Param("status")String status, @Param("mod_id") Integer mod_id);

    @Query(value = "SELECT * FROM posts WHERE (text like concat('%',:query,'%') or concat('%',:query,'%') is null) and is_active = 1 AND moderation_status = 'ACCEPTED' AND date <= sysdate() ",
            countQuery = "SELECT count(*) FROM posts"+
                    " WHERE is_active = 1  " +
                    "AND moderation_status = 'ACCEPTED' AND date <= sysdate()  ",
            nativeQuery = true)
    Page<Post> findSearchPostPagination(Pageable pageable, @Param("query") String query);

    @Query(value = "SELECT * FROM posts WHERE  user_id = :idUser AND date <= sysdate() " +
            "and ( (:status = 'inactive' and is_active = 0)" +
            " or( :status = 'pending' and is_active = 1 and moderation_status = 'NEW') " +
            " or (:status = 'declined' and is_active = 1 and moderation_status = 'DECLINED' )" +
            " or (:status = 'published' and is_active = 1 and moderation_status = 'ACCEPTED'))",
             nativeQuery = true)
    Page<Post> findSearchPostUserPagination(Pageable pageable, @Param("status") String status, @Param("idUser") long idUser);


    @Query(value = "SELECT p " +
            "FROM Post p "      +
              "LEFT JOIN p.postVotesCollection pvl  "+
              "WHERE p.isActive = 1  " +
              "AND p.moderationStatus = 'ACCEPTED' AND p.date <= current_timestamp()  " +
              "GROUP BY p.id " +
              "ORDER BY SUM(pvl.value_votes) DESC"
             )
    Page<Post> findAllPostPaginationSortVotes(Pageable pageable);

    @Query(value = "SELECT * FROM Posts " +
            "WHERE DATE_FORMAT(date, '%Y-%m-%d') = :query " +
            "and is_active = 1 AND moderation_status = 'ACCEPTED' AND date <= sysdate() ",
            countQuery = "SELECT count(*) FROM Posts"+
                    " WHERE is_active = 1  " +
                    "AND moderation_status = 'ACCEPTED' AND date <= sysdate()  ",
            nativeQuery = true)
    Page<Post> findPostByDate(Pageable pageable, @Param("query") String query);

    @Query(value = "SELECT p " +
            "FROM Post p "      +
            "LEFT JOIN p.commentCollection com  "+
            "WHERE p.isActive = 1  " +
            "AND p.moderationStatus = 'ACCEPTED' AND p.date <= current_timestamp()  " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(com) DESC",
            countQuery = "SELECT count(p) " +
                    "FROM Post p "      +
                    "WHERE p.isActive = 1  " +
                    "AND p.moderationStatus = 'ACCEPTED' AND p.date <= CURRENT_DATE()  "
    )
    Page<Post> findAllPostPaginationSortComment(Pageable pageable);

    @Query(value = "SELECT count(p) FROM Post p "+
            "WHERE p.isActive = 1  " +
            "AND p.moderationStatus = 'ACCEPTED' AND p.date <= CURRENT_DATE()  "
    )
    Integer countPost();

    @Query(value = "SELECT p " +
            "FROM Post p "      +
            "JOIN p.postTags t  "+
            "WHERE p.isActive = 1  " +
            "AND p.moderationStatus = 'ACCEPTED' AND p.date <= CURRENT_DATE() " +
            "and t.id = :tagId "
             )
    Page<Post> findPostByTag(Pageable pageable, @Param("tagId") int tagId);

    @Query(value = "SELECT * FROM Posts WHERE id = :query and is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' " +
            "AND date <= sysdate() ",
             nativeQuery = true)
    Post findPostByIdNew(@Param("query") int query);

    @Query(value = "SELECT count(p) FROM Post p "+
            "WHERE :user is null or p.user = :user  "
    )
    Integer countPostUser(@Param("user") User user);

    @Query(value = "SELECT min(p.date) FROM Post p "+
            "WHERE :user is null or p.user = :user  "
    )
    Optional<Date> firstPost(@Param("user")User user);

    @Query(value = "SELECT sum(p.viewCount) FROM Post p "+
            "WHERE :user is null or p.user = :user  "
    )
    Optional<Integer> viewCount(@Param("user")User user);

 }
