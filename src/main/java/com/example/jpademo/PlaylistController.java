package com.example.jpademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private MusicService musicService;

    @GetMapping("/mymusic")
    public String playlistPage(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {

        List<Long> musicIds = playlistService.getMusicIdxByCategory("mymusic");

        // musicIds가 비어 있는 경우 예외 페이지로 이동
        if (musicIds == null || musicIds.isEmpty()) {
            return "mymusicexception"; // mymusicexception.html 반환
        }

        List<MusicDTO> allMusics = musicService.getMusicsByIdx(musicIds);

        int totalItems = allMusics.size();
        model.addAttribute("totalItems", totalItems);

        int totalPages = (int) Math.ceil((double) totalItems / size);
        model.addAttribute("totalPages", totalPages);

        int fromIndex = Math.max((page - 1) * size, 0);  // 최소값을 0으로 제한
        int toIndex = Math.min(fromIndex + size, totalItems);

        List<MusicDTO> paginatedMusics = allMusics.subList(fromIndex, toIndex);

        model.addAttribute("musics", paginatedMusics);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);

        String mostFrequentSinger = playlistService.getMostFrequentSinger();
        model.addAttribute("mostFrequentSinger", mostFrequentSinger);

        List<MusicDTO> artistAlbums = musicService.getAlbumsBySinger(mostFrequentSinger);

        if (artistAlbums == null) {
            artistAlbums = new ArrayList<>();
        }

        Collections.shuffle(artistAlbums);

        List<MusicDTO> randomAlbums = artistAlbums.stream().limit(4).collect(Collectors.toList());
        model.addAttribute("randomAlbums", randomAlbums);

        String mostFrequentGenre = playlistService.getMostFrequentGenre();
        model.addAttribute("mostFrequentGenre", mostFrequentGenre);


        List<MusicDTO> genreMusics = musicService.getMusicsByGenre(mostFrequentGenre);
        model.addAttribute("genreMusics", genreMusics);

        return "mymusic";
    }

    @PostMapping("/addMyMusic")
    @ResponseBody
    public Map<String, String> addToMyMusic(@RequestParam("musicId") Long musicId) {
        Map<String, String> response = new HashMap<>();
        try {
            // 'mymusic' 카테고리에 음악을 추가
            playlistService.addMusicToMyMusic(musicId);
            response.put("message", "나의 음악에 담겼습니다.");
        } catch (RuntimeException e) {
            response.put("message", e.getMessage()); // "이미 담긴 노래입니다." 메시지 처리
        }
        return response;
    }

    @PostMapping("/removeMyMusic")
    @ResponseBody
    public Map<String, String> removeMusicFromPlaylist(@RequestParam("musicId") Long musicId) {
        Map<String, String> response = new HashMap<>();
        try {
            // 'mymusic' 카테고리에서 음악을 삭제
            playlistService.removeMusicFromMyMusic(musicId);
            response.put("message", "성공적으로 삭제되었습니다.");
        } catch (RuntimeException e) {
            response.put("message", e.getMessage()); // "삭제 실패" 메시지 처리
        }
        return response;
    }


    @GetMapping("/playlist/{category}")
    public String playlistPageByCategory(
            @PathVariable("category") String category,
            Model model) {

        // 주어진 카테고리로 음악 ID 목록 가져오기
        List<Long> musicIds = playlistService.getMusicIdxByCategory(category);

        // 음악 ID에 해당하는 음악 정보를 가져오기
        List<MusicDTO> musics = musicService.getMusicsByIdx(musicIds);

        // 음악 리스트를 랜덤으로 섞기
        Collections.shuffle(musics);

        // model에 category와 musics를 추가
        model.addAttribute("category", category);
        model.addAttribute("musics", musics);

        // playlist.html 뷰를 반환
        return "playlist";
    }

}
