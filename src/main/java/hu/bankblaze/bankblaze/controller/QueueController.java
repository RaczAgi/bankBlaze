package hu.bankblaze.bankblaze.controller;

import hu.bankblaze.bankblaze.service.DeskService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class QueueController {

    @Autowired
    private DeskService deskService;

    @GetMapping("/queueCall")
    public String getQueue(Model model) {
        model.addAttribute("desks", deskService.getAllDesks());
        return "queue";
    }
}
