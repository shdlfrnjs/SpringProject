package com.example.jpademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/artist/{singer}")
    public String getArtist(
            @PathVariable("singer") String singer,
            @RequestParam(value = "sort", defaultValue = "releaseDate") String sort,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model) {

        System.out.println("검색할 가수: " + singer);

        Artist artist = artistService.getArtistBySinger(singer);

        if (artist == null) {
            model.addAttribute("message", "해당 아티스트를 찾을 수 없습니다.");
            return "error";
        }

        // 정렬된 목록 가져오기
        List<MusicDTO> sortedMusics = artistService.getMusicsBySinger(singer, sort);

        // 페이징 처리
        int totalItems = sortedMusics.size();
        model.addAttribute("totalItems", totalItems); // 곡 개수 모델에 추가
        int totalPages = (int) Math.ceil((double) totalItems / size);
        int fromIndex = Math.max((page - 1) * size, 0);  // 최소값을 0으로 제한
        int toIndex = Math.min(fromIndex + size, totalItems);

        // 현재 페이지에 해당하는 목록
        List<MusicDTO> musics = sortedMusics.subList(fromIndex, toIndex);

        model.addAttribute("artist", artist);
        model.addAttribute("musics", musics);
        model.addAttribute("selectedSort", sort); // 현재 선택된 정렬 기준 저장
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "artist";
    }

}


