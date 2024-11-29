package com.example.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Long> {

    List<Music> findByTitleContainingIgnoreCaseOrSingerContainingIgnoreCase(String title, String singer);

    List<Music> findAllByIdxIn(List<Long> musicIds);

    List<Music> findBySinger(String singer);

    List<Music> findByGenre(String genre);

    @Modifying
    @Transactional
    @Query("UPDATE Music m SET m.hits = m.hits + 1 WHERE m.idx = :idx")
    void incrementHits(@Param("idx") long idx);

    @Query("SELECT m FROM Music m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY " +
            "CASE WHEN LOWER(m.title) LIKE LOWER(:keyword) THEN 1 ELSE 2 END, m.hits DESC")
    List<Music> findByTitleContainingIgnoreCaseOrderByRelevance(@Param("keyword") String keyword);

    @Query("SELECT m FROM Music m WHERE m.genre IN :genres ORDER BY FUNCTION('RAND')")
    List<Music> findByGenresRandomly(List<String> genres);
}
