package com.personal.diplom.repository;

import com.personal.diplom.model.Post;
import com.personal.diplom.model.PostVotes;
import com.personal.diplom.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VotesRepository extends CrudRepository<PostVotes,Integer> {

    Optional<PostVotes> findFirstByPostAndUser(Post post, User user);

    @Query(value = "SELECT count(p) FROM PostVotes p "+
            "WHERE (:user is null or p.user = :user)  " +
            " AND p.value_votes = :vote "
    )
    Integer countVotes(@Param("user") User user, @Param("vote")int vote);
}
