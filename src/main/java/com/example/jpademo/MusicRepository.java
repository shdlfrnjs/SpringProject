package com.example.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Long> {
    List<Music> findByTitleContainingIgnoreCaseOrSingerContainingIgnoreCase(String title, String singer);

}
