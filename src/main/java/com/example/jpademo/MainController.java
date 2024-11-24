package com.example.jpademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    MusicService musicService;

    @GetMapping("/main")
    public String mainPage(
            @RequestParam(value = "sort", defaultValue = "releaseDate") String sort,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {
        // 정렬된 목록 가져오기
        List<MusicDTO> sortedMusics = musicService.sortedMusics(sort);

        // 페이징 처리
        int totalItems = sortedMusics.size();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        int fromIndex = Math.max((page - 1) * size, 0);  // 최소값을 0으로 제한
        int toIndex = Math.min(fromIndex + size, totalItems);

        // 현재 페이지에 해당하는 목록
        List<MusicDTO> musics = sortedMusics.subList(fromIndex, toIndex);

        // 모델에 값 추가
        model.addAttribute("musics", musics);
        model.addAttribute("selectedSort", sort); // 현재 선택된 정렬 기준 저장
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startIndex", fromIndex + 1);

        return "main";
    }

}
