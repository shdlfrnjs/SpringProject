package com.example.jpademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MusicController {

    @Autowired
    MusicService musicService;

    @GetMapping("/music/{idx}")
    public String getMusic(@PathVariable long idx, Model model) {
        // 조회수 증가
        musicService.incrementHits(idx);

        // 음악 정보 가져오기
        MusicDTO music = musicService.findById(idx);

        model.addAttribute("music", music);

        return "music";
    }

    @Controller
    @RequestMapping("/myplaylist")
    public class PlaylistController {

        @Autowired
        private MusicService musicService;

        @GetMapping
        public String getMyPlaylistPage() {
            return "myplaylist";
        }

        @GetMapping("/make")
        public ResponseEntity<List<MusicDTO>> getMyPlaylist(@RequestParam List<String> genres) {

            List<MusicDTO> playlist = musicService.getPlaylistByGenres(genres);
            return ResponseEntity.ok(playlist);
        }

    }

}


