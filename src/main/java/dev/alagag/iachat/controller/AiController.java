package dev.alagag.iachat.controller;

import dev.alagag.iachat.dto.AiPromptDto;
import dev.alagag.iachat.dto.AiResultDto;
import dev.alagag.iachat.service.IAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final IAiService ai;

    @PostMapping()
    public ResponseEntity<AiResultDto> prompt(@RequestBody AiPromptDto promptDto) {
        System.out.println(promptDto.getMessage());
        String reply = ai.prompt(promptDto.getMessage());
        AiResultDto response = new AiResultDto();
        response.setResult(reply);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<List<String>> chatHistory(@PathVariable String chatId) {
        List<String> history = ai.getChatHistory(chatId);
        return ResponseEntity.ok(history);
    }

    @PostMapping("/{chatId}")
    public ResponseEntity<AiResultDto> chat(@PathVariable String chatId, @RequestBody AiPromptDto promptDto) {
        String reply = ai.chat(chatId, promptDto.getMessage());
        AiResultDto response = new AiResultDto();
        response.setResult(reply);
        return ResponseEntity.ok(response);
    }

}
