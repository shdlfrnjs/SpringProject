package com.example.jpademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                            // ranking 높은 순으로 정렬 (내림차순)
                            return Integer.compare(music2.getRanking(), music1.getRanking());
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

}
