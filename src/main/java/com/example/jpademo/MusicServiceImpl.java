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
}
