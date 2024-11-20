package com.example.jpademo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CrudController {

    @Autowired
    MusicService musicService;

    @RequestMapping("/music")
    public String list(Model model) {
        model.addAttribute("musics", musicService.findAll());
        return "list";
    }

    @RequestMapping("/music/addform")
    public String addform() {
        return "addform";
    }

    @RequestMapping("/music/add")
    public String add(@ModelAttribute MusicDTO music) {
        musicService.save(music);
        return "redirect:/music";
    }

    @RequestMapping("/music/{idx}")
    public String read(@PathVariable long idx, Model model) {
        model.addAttribute("music", musicService.findById(idx));
        return "read";
    }

    @RequestMapping("/music/delete/{idx}")
    public String delete(@PathVariable long idx) {
        musicService.deleteById(idx);
        return "redirect:/music";
    }

    @RequestMapping("/music/updateform/{idx}")
    public String updatemusic(@PathVariable Long idx, Model model) {
        MusicDTO music = musicService.findById(idx);
        System.out.println("Release Date: " + music.getReleaseDate()); // 로그 출력
        model.addAttribute("music", music);
        return "updateform";
    }

    @RequestMapping("/music/update")
    public String update(@ModelAttribute MusicDTO music) {
        musicService.save(music);
        return "redirect:/music";
    }

    @RequestMapping("/music/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        if (keyword == null || keyword.isEmpty()) {
            model.addAttribute("musics", musicService.findAll());
        } else {
            model.addAttribute("musics", musicService.searchByKeyword(keyword));
        }
        return "list";
    }

}