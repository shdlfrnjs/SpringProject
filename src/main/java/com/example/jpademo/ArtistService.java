package com.example.jpademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

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

}

