package com.example.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

        @Query("SELECT p.music.idx FROM Playlist p")
        List<Long> findMusicIdxInPlaylist();

        void deleteByMusicIdx(Long musicId);

        Optional<Playlist> findByMusicIdx(Long musicId);


}
