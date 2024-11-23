package com.example.jpademo;

import java.util.List;

public interface MusicService {

    List<MusicDTO> findAll();

    List<MusicDTO> sortedMusics(String sort);

    MusicDTO findById(long idx);

    void incrementHits(long idx);

    void save(MusicDTO music);

    void deleteById(long idx);

    Object searchByKeyword(String keyword);

    List<MusicDTO> searchMusicByKeyword(String keyword);

    List<MusicDTO> getMusicsByIdx(List<Long> musicIdx);

}
