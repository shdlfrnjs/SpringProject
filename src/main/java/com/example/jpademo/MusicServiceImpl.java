package com.example.jpademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private MusicRepository musicRepository;

    @Override
    public List<MusicDTO> findAll() {
        return musicRepository.findAll().stream()
                .map(Utils::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MusicDTO> sortedMusics(String sort) {
        return musicRepository.findAll().stream()
                .map(Utils::toDTO) // Music -> MusicDTO 변환
                .sorted((music1, music2) -> {
                    switch (sort) {
                        case "releaseDate":
                            // releaseDate 최신순으로 정렬 (내림차순)
                            return music2.getReleaseDate().compareTo(music1.getReleaseDate());
                        case "ranking":
                            // ranking 순으로 정렬 (오름차순)
                            return Integer.compare(music1.getRanking(), music2.getRanking());
                        case "hits":
                            // hits 높은 순으로 정렬 (내림차순)
                            return Integer.compare(music2.getHits(), music1.getHits());
                        case "title":
                            // title 알파벳 순으로 정렬 (오름차순)
                            return music1.getTitle().compareTo(music2.getTitle());
                        default:
                            // 기본 정렬: releaseDate 최신순
                            return music2.getReleaseDate().compareTo(music1.getReleaseDate());
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MusicDTO> getTopRankedMusics(int limit) {
        return musicRepository.findAll().stream()
                .sorted((music1, music2) -> Integer.compare(music1.getRanking(), music2.getRanking())) // 오름차순
                .limit(limit) // 상위 limit개의 음악만 추출
                .map(Utils::toDTO) // DTO로 변환
                .collect(Collectors.toList());
    }

    @Override
    public List<MusicDTO> getNewMusics(int limit) {
        return musicRepository.findAll().stream()
                .sorted((music1, music2) -> music2.getReleaseDate().compareTo(music1.getReleaseDate())) // releaseDate 기준 내림차순 정렬
                .limit(limit) // 상위 limit개의 음악만 추출
                .map(Utils::toDTO) // DTO로 변환
                .collect(Collectors.toList());
    }

    // 장르별 음악을 가져오는 메서드
    @Override
    public List<MusicDTO> getMusicsByGenre(String sort, String genre) {
        // Genre에 해당하는 음악 리스트를 가져옴
        List<Music> musics = musicRepository.findByGenre(genre);

        // Music 객체를 DTO로 변환하여 반환
        return musics.stream()
                .map(Utils::toDTO) // Music -> MusicDTO 변환
                .sorted((music1, music2) -> {
                    switch (sort) {
                        case "releaseDate":
                            // releaseDate 최신순으로 정렬 (내림차순)
                            return music2.getReleaseDate().compareTo(music1.getReleaseDate());
                        case "ranking":
                            // ranking 순으로 정렬 (오름차순)
                            return Integer.compare(music1.getRanking(), music2.getRanking());
                        case "hits":
                            // hits 높은 순으로 정렬 (내림차순)
                            return Integer.compare(music2.getHits(), music1.getHits());
                        case "title":
                            // title 알파벳 순으로 정렬 (오름차순)
                            return music1.getTitle().compareTo(music2.getTitle());
                        default:
                            // 기본 정렬: releaseDate 최신순
                            return music2.getReleaseDate().compareTo(music1.getReleaseDate());
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public MusicDTO findById(long idx) {
        return musicRepository.findById(idx)
                .map(Utils::toDTO)
                .orElse(null);
    }

    @Override
    public void incrementHits(long idx) {
        musicRepository.incrementHits(idx);
    }

    @Override
    public void save(MusicDTO musicDTO) {
        Music music = Utils.toEntity(musicDTO);
        musicRepository.save(music);
    }

    @Override
    public void deleteById(long idx) {
        musicRepository.deleteById(idx);
    }

    @Override
    public List<MusicDTO> searchByKeyword(String keyword) {
        return musicRepository.findByTitleContainingIgnoreCaseOrSingerContainingIgnoreCase(keyword, keyword).stream()
                .map(Utils::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MusicDTO> searchMusicByKeyword(String keyword) {
        return musicRepository.findByTitleContainingIgnoreCaseOrderByRelevance(keyword).stream()
                .map(Utils::toDTO) // `Utils::toDTO`는 Entity -> DTO 변환 로직
                .collect(Collectors.toList());
    }

    @Override
    public List<MusicDTO> getMusicsByIdx(List<Long> musicIdx) {
        // ID 목록에 해당하는 음악들을 DB에서 찾고, MusicDTO로 변환하여 반환
        return musicRepository.findAllByIdxIn(musicIdx).stream()
                .map(Utils::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MusicDTO> getAlbumsBySinger(String mostFrequentSinger) {
        // Music 엔티티 리스트를 가져온 후, MusicDTO로 변환하여 반환
        return musicRepository.findBySinger(mostFrequentSinger).stream()
                .map(Utils::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MusicDTO> getMusicsByGenre(String genre) {
        List<Music> musics = musicRepository.findByGenre(genre);

        if (musics.isEmpty()) {
            System.out.println("해당 장르에 음악이 없습니다: " + genre);
            return Collections.emptyList();
        }

        Collections.shuffle(musics);

        // 랜덤으로 섞은 후, 상위 4곡만 선택해서 MusicDTO로 변환
        return musics.stream()
                .limit(4)
                .map(Utils::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MusicDTO> getPlaylistByGenres(List<String> genres) {
        // 장르로 엔티티 검색
        List<Music> musicList = musicRepository.findByGenresRandomly(genres);

        Collections.shuffle(musicList);

        // 곡 개수가 10개 이하라면 그대로 반환
        List<Music> selectedSongs;
        if (musicList.size() <= 10) {
            selectedSongs = musicList;
        } else {
            selectedSongs = musicList.subList(0, 10);
        }

        return selectedSongs.stream()
                .map(Utils::toDTO)
                .collect(Collectors.toList());
    }



}
