package com.example.jpademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public Artist getArtistBySinger(String singer) {
        System.out.println("검색할 가수: " + singer);
        Artist artist = artistRepository.findBySingerIgnoreCase(singer);
        if (artist == null) {
            System.out.println("해당 아티스트를 찾을 수 없습니다.");
        } else {
            System.out.println("찾은 아티스트: " + artist);
        }
        return artist;
    }

    public List<MusicDTO> getMusicsBySinger(String singer, String sort) {
        return musicRepository.findBySinger(singer).stream()
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

    public List<Artist> searchArtistByKeyword(String keyword) {
        return artistRepository
                .findBySingerContainingIgnoreCaseOrderByRelevance(keyword); // `relevance` 기준 정렬
    }

}
