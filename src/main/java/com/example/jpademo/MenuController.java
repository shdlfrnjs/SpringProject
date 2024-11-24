package com.example.jpademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/musics/list/{genre}")
    public String getGenreMusics(
            @RequestParam(value = "sort", defaultValue = "releaseDate") String sort,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @PathVariable("genre") String genre,
            Model model) {

        List<MusicDTO> sortedGenreMusics = musicService.getMusicsByGenre(sort, genre);

        // 페이징 처리
        int totalItems = sortedGenreMusics.size();
        model.addAttribute("totalItems", totalItems);
        int totalPages = (int) Math.ceil((double) totalItems / size);
        int fromIndex = Math.max((page - 1) * size, 0);  // 최소값을 0으로 제한
        int toIndex = Math.min(fromIndex + size, totalItems);

        // 현재 페이지에 해당하는 목록
        List<MusicDTO> musics = sortedGenreMusics.subList(fromIndex, toIndex);

        // 모델에 값 추가
        model.addAttribute("musics", musics);
        model.addAttribute("selectedSort", sort); // 현재 선택된 정렬 기준 저장
        model.addAttribute("genre", genre);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startIndex", fromIndex + 1);

        return "genremusics";

    }
}
