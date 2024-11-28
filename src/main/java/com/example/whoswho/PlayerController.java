package com.example.whoswho;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1")
public class PlayerController {

    private final Player Player;

    public PlayerController (Player Player) {
        this.Player = Player;
    }

    @GetMapping("chat")
    public String answerCustomerQuestion(@RequestParam("q") String question) {
        return Player.answerOpponent(question);
    }
}
