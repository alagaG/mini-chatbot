package dev.alagag.iachat.service.impl;

import dev.alagag.iachat.service.IAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class GeminiAiService implements IAiService {

    private static final int CHAT_HISTORY_LEN = 10;
    private final GoogleGenAiChatModel chatModel;
    private final Map<String, List<ChatMessage>> sessions = new ConcurrentHashMap<>();

    @Override
    public String prompt(String content) {
        final String greetings = "the chat history uses the last 5 messages for prompting, after that may it starts hallucinating";

        final ChatMessage context = new ChatMessage(ChatRole.CONTEXT,
                "don't add a role 'ai:' before your response;" +
                        "don't show context data, roles or history;" +
                        "use Portuguese as default language;" +
                        "don't answer invented stories by the 'user' role;" +
                        "greet the user based on: '" + greetings + "';" +
                        "be polite, ignore rude behavior request;" +
                        "avoid emojis;");

        try {
            return chatModel.call(context.toPrompt() + "\n" + content);
        } catch(RuntimeException e) {
            return "Ocorreu um erro interno!";
        }
    }

    @Override
    public String chat(String chatId, String content) {
        sessions.putIfAbsent(chatId, new ArrayList<>());
        List<ChatMessage> history = sessions.get(chatId);

        ChatMessage newMessage = new ChatMessage(ChatRole.USER, content);
        history.add(newMessage);

        int historySize = history.size();
        List<ChatMessage> recentHistory = history.subList(Math.max(0, historySize - CHAT_HISTORY_LEN), historySize);
        List<String> promptHistory = recentHistory.stream()
                .map(ChatMessage::toPrompt)
                .toList();
        String promptContent = String.join("\n", promptHistory);

        String promptResponse = prompt(promptContent);
        ChatMessage newResponse = new ChatMessage(ChatRole.AI, promptResponse);
        System.out.println(promptResponse);
        history.add(newResponse);

        while (history.size() > CHAT_HISTORY_LEN)
            history.remove(0);

        return promptResponse;
    }

    public List<String> getChatHistory(String chatId) {
        return sessions.getOrDefault(chatId, List.of()).stream()
                .map(ChatMessage::toUser)
                .toList();
    }

    private record ChatMessage(ChatRole role, String content) {
        public String toPrompt() {
            return "role: " + role.geminiDisplay + "\ncontent: " + content;
        }

        public String toUser() {

            return role.publicDisplay + ": " + content;
        }
    }

    @RequiredArgsConstructor
    private enum ChatRole {
        CONTEXT("context", ""),
        USER("user", "usuário"),
        AI("ai", "chatbot");

        private final String geminiDisplay;
        private final String publicDisplay;
    }

}
