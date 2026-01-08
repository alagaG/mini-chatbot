"use client";

import { useState } from "react";
import ChatInput from "./ChatInput";
import { ChatBubble } from "./ChatBubble";
import { ChatMessage, ChatRoles } from "@/src/lib/ai/chat";

export default function ChatWindow() {
    const [messages, setMessages] = useState<ChatMessage[]>([]);
    const [loading, setLoading] = useState(false);

    const handleSend = async (message: string) => {
        setMessages((prev) => [
            ...prev,
            { role: ChatRoles.USER, content: message },
        ]);
        setLoading(true);

        try {
            const response = await fetch("http://localhost:8080/api/ai", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ message }),
            });

            const data = await response.json();
            setMessages((prev) => [
                ...prev,
                {
                    role: ChatRoles.ASSISTANT,
                    content: data.result ?? "Sem resposta.",
                },
            ]);
        } catch (e) {
            setMessages((prev) => [
                ...prev,
                {
                    role: ChatRoles.ASSISTANT,
                    content: "Erro ao conectar ao servidor.",
                },
            ]);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="flex flex-col h-[80vh] w-1/3 min-w-120 mx-auto border border-gray-300 rounded-lg overflow-hidden shadow-md">
            <div className="flex-1 p-4 overflow-y-auto bg-white">
                {messages.length === 0 && (
                    <p className="text-gray-400 text-center mt-10">
                        Comece uma conversa 👋
                    </p>
                )}
                {messages.map((m, i) => (
                    <ChatBubble key={i} message={m} />
                ))}
                {loading && (
                    <ChatBubble
                        message={{
                            role: ChatRoles.ASSISTANT,
                            content: "Gerando resposta...",
                        }}
                    />
                )}
            </div>

            <ChatInput onSend={handleSend} disabled={loading} />
        </div>
    );
}
