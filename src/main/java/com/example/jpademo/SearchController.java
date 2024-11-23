package com.example.jpademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    ArtistService artistService;

    @Autowired
    MusicService musicService;

    @GetMapping("/search")
    public String search(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model) {

        // 1. 아티스트 검색
        List<Artist> artists = artistService.searchArtistByKeyword(keyword);
        int totalArtistItems = artists.size();
        model.addAttribute("totalArtistItems", totalArtistItems); // 아티스트 개수 모델에 추가

        // 2. 음악 검색
        List<MusicDTO> sortedMusics = musicService.searchMusicByKeyword(keyword);

        // 페이징 처리
        int totalItems = sortedMusics.size();
        model.addAttribute("totalMusicItems", totalItems); // 곡 개수 모델에 추가
        int totalPages = (int) Math.ceil((double) totalItems / size);
        int fromIndex = Math.max((page - 1) * size, 0);  // 최소값을 0으로 제한
        int toIndex = Math.min(fromIndex + size, totalItems);

        // 현재 페이지에 해당하는 목록
        List<MusicDTO> musics = sortedMusics.subList(fromIndex, toIndex);

        // 3. 모델에 검색 결과 추가
        model.addAttribute("keyword", keyword);
        model.addAttribute("artists", artists);
        model.addAttribute("musics", musics);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        // 검색 결과 페이지 반환
        return "search"; // `search.html` 뷰로 이동

    }

    @GetMapping("/getSearchSuggestions")
    @ResponseBody
    public List<String> getSearchSuggestions(@RequestParam("term") String term) {
        // 아티스트 이름(singer)과 음악 제목(title)을 기준으로 검색
        List<String> suggestions = new ArrayList<>();

        // 아티스트 이름(singer) 검색
        List<Artist> artists = artistService.searchArtistByKeyword(term);  // 검색어로 아티스트 검색
        for (Artist artist : artists) {
            suggestions.add(artist.getSinger());  // 아티스트 이름 추가
        }

        // 음악 제목(title) 검색
        List<MusicDTO> musics = musicService.searchMusicByKeyword(term);  // 검색어로 음악 제목 검색
        for (MusicDTO music : musics) {
            suggestions.add(music.getTitle());  // 음악 제목 추가
        }

        return suggestions;  // 모든 추천 검색어 반환
    }
}
