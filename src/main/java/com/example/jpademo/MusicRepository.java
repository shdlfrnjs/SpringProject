package com.example.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Long> {

    List<Music> findByTitleContainingIgnoreCaseOrSingerContainingIgnoreCase(String title, String singer);

    List<Music> findBySinger(String singer);

    @Modifying
    @Transactional
    @Query("UPDATE Music m SET m.hits = m.hits + 1 WHERE m.idx = :idx")
    void incrementHits(@Param("idx") long idx);

}
