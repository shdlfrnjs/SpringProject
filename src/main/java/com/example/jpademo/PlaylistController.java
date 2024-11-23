package com.example.jpademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String playlistPage(Model model) {
        List<Long> musicIds = playlistService.getMusicIdxInPlaylist();
        List<MusicDTO> musics = musicService.getMusicsByIdx(musicIds);

        model.addAttribute("musics", musics);
        return "mymusic";
    }

    @PostMapping("/addMyMusic")
    @ResponseBody
    public Map<String, String> addToMyMusic(@RequestParam("musicId") Long musicId) {
        if (playlistService.isMusicAlreadyAdded(musicId)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "이미 담긴 노래입니다.");
            return response;
        }

        playlistService.addMusicToPlaylist(musicId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "나의 음악에 담겼습니다.");
        return response;
    }
    @PostMapping("/removeMyMusic")
    @ResponseBody
    public Map<String, String> removeMusicFromPlaylist(@RequestParam("musicId") Long musicId) {
        Map<String, String> response = new HashMap<>();
        try {
            playlistService.removeMusicFromPlaylist(musicId);
            response.put("message", "성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            response.put("message", "삭제 실패" + e.getMessage());
        }
        return response;
    }





}
