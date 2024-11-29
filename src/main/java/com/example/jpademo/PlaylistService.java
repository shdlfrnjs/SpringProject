package com.example.jpademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class PlaylistService {

    @Autowired
    private MusicService musicService;


    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private MusicRepository musicRepository;



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


    public List<Long> getMusicIdxByCategory(String category) {
        return playlistRepository.findMusicIdxByCategory(category);
    }

    public String getMostFrequentSinger() {
        // "mymusic" 카테고리의 음악 ID를 가져옴
        List<Long> musicIds = getMusicIdxByCategory("mymusic");
        // 해당 음악 ID로 음악 정보를 가져옴
        List<MusicDTO> allMusics = musicService.getMusicsByIdx(musicIds);

        // 각 가수가 추가된 횟수를 세는 맵
        Map<String, Integer> singerCount = new HashMap<>();

        for (MusicDTO music : allMusics) {
            String singer = music.getSinger();
            singerCount.put(singer, singerCount.getOrDefault(singer, 0) + 1);
        }

        // 가장 많이 추가된 가수를 찾음
        String mostFrequentSinger = singerCount.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();

        return mostFrequentSinger;
    }


    public String getMostFrequentGenre() {
        // "mymusic" 카테고리의 음악 ID를 가져옴
        List<Long> musicIds = getMusicIdxByCategory("mymusic");
        // 해당 음악 ID로 음악 정보를 가져옴
        List<MusicDTO> allMusics = musicService.getMusicsByIdx(musicIds);

        // 각 장르가 등장한 횟수를 세는 맵
        Map<String, Integer> genreCount = new HashMap<>();

        for (MusicDTO music : allMusics) {
            String genre = music.getGenre();
            genreCount.put(genre, genreCount.getOrDefault(genre, 0) + 1);
        }

        // 가장 많이 등장한 장르를 찾음
        String mostFrequentGenre = genreCount.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();

        // 해당 장르에 랜덤 4곡 가져오기
        List<MusicDTO> genreMusics = musicService.getMusicsByGenre(mostFrequentGenre);

        return mostFrequentGenre;
    }

    // 'mymusic' 카테고리에 음악 추가
    public void addMusicToMyMusic(Long musicIdx) {
        // 'mymusic' 카테고리에서 이미 추가된 음악이 있는지 확인
        if (playlistRepository.existsByMusicIdxAndCategory(musicIdx, "mymusic")) {
            throw new RuntimeException("이미 담긴 노래입니다.");
        }

        // 'mymusic' 카테고리에 음악 추가
        Music music = musicRepository.findById(musicIdx)
                .orElseThrow(() -> new RuntimeException("음악을 찾을 수 없습니다."));

        Playlist playlist = Playlist.builder()
                .music(music)
                .category("mymusic")
                .build();

        playlistRepository.save(playlist);
    }

    // 'mymusic' 카테고리에서 음악 삭제
    public void removeMusicFromMyMusic(Long musicIdx) {
        Playlist playlist = playlistRepository.findByMusicIdxAndCategory(musicIdx, "mymusic")
                .orElseThrow(() -> new RuntimeException("이 음악은 'mymusic' 카테고리에 없습니다."));

        playlistRepository.delete(playlist);
    }
}
