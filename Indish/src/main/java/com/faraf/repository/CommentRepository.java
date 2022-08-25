package com.faraf.repository;


import com.faraf.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findAllByPost_Id(Long postId);

    List<Comment> findAllByUser_Id(Long userId);

}
