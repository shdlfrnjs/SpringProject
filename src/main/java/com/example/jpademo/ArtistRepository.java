package com.example.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Artist findBySingerIgnoreCase(String singer);

    @Query("SELECT a FROM Artist a WHERE LOWER(a.singer) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY " +
            "CASE WHEN LOWER(a.singer) LIKE LOWER(:keyword) THEN 1 ELSE 2 END, a.singer ASC")
    List<Artist> findBySingerContainingIgnoreCaseOrderByRelevance(@Param("keyword") String keyword);

}
