package com.example.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

        @Query("SELECT p.music.idx FROM Playlist p")
        List<Long> findMusicIdxInPlaylist();

        void deleteByMusicIdx(Long musicId);

        Optional<Playlist> findByMusicIdx(Long musicId);


        @Query("SELECT p.music.idx FROM Playlist p WHERE p.category = :category")
        List<Long> findMusicIdxByCategory(@Param("category") String category);

}
