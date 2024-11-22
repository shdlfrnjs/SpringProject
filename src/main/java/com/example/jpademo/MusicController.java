package com.example.jpademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

}


