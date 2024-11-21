package com.example.jpademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/artist/{singer}")
    public String getArtist(@PathVariable("singer") String singer, Model model) {
        System.out.println("검색할 가수: " + singer);

        Artist artist = artistService.getArtistBySinger(singer);

        if (artist == null) {
            model.addAttribute("message", "해당 아티스트를 찾을 수 없습니다.");
            return "error";
        }

        model.addAttribute("artist", artist);
        return "artist";
    }

}


