package com.back.p13202.domain.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ArticleRepository extends JpaRepository<Article,Integer> {

    @Query("select distinct a " +
            "from Article a " +
            "left join a.author u " +
            "where " +
            "   a.title like %:keyword% " +
            "   or a.content like %:keyword% " +
            "   or u.username like %:keyword% ")
    List<Article> findAllByKeyword(@Param("keyword") String keyword);

    Optional<Article> findById(Integer id);
}
