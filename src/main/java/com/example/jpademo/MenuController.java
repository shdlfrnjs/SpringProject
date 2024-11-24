package com.example.jpademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MenuController {

    @Autowired
    MusicService musicService;

    @GetMapping("/musics/popular")
    public String getPopularMusics(Model model) {

        List<MusicDTO> musics = musicService.getTopRankedMusics(10);
        model.addAttribute("musics", musics);
        return "popularmusics";

    }

    @GetMapping("/musics/new")
    public String getNewMusics(Model model) {

        List<MusicDTO> musics = musicService.getNewMusics(10);
        model.addAttribute("musics", musics);
        return "newmusics";

    }
}
