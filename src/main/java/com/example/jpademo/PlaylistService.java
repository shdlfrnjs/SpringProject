package com.example.jpademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private MusicRepository musicRepository;

    public void addMusicToPlaylist(Long musicId) {
        // 음악 객체를 찾아서 플레이리스트에 추가
        Music music = musicRepository.findById(musicId).orElseThrow(() -> new RuntimeException("Music not found"));

        Playlist playlist = new Playlist();
        playlist.setMusic(music);
        playlist.setCategory("mymusic");


        playlistRepository.save(playlist);
    }

    public List<Long> getMusicIdxInPlaylist() {
        return playlistRepository.findMusicIdxInPlaylist();
    }

    public boolean isMusicAlreadyAdded(Long musicId) {
        List<Long> musicIdsInPlaylist = playlistRepository.findMusicIdxInPlaylist();
        return musicIdsInPlaylist.contains(musicId);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)

    public void removeMusicFromPlaylist(Long musicId) {
        try {
            Optional<Playlist> playlist = playlistRepository.findByMusicIdx(musicId);
            if (playlist.isPresent()) {
                playlistRepository.deleteByMusicIdx(musicId);  // 삭제 처리
                System.out.println("삭제 성공: 음악 ID - " + musicId);
            } else {
                throw new RuntimeException("플레이리스트에 해당 음악이 없습니다.");
            }
        } catch (Exception e) {
            System.err.println("삭제 실패, 오류: " + e.getMessage());
            throw new RuntimeException("음악 삭제 실패: " + e.getMessage());
        }
    }








}
