package dev.alagag.iachat.service;


import java.util.List;

public interface IAiService<T> {

    String prompt(String content);

    String chat(String chatId, String content);

    List<String> getChatHistory(String chatId);

}
