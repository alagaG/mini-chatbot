import { ChatMessage, ChatRoles } from "@/src/lib/ai/chat";
import { StyledMarkdown } from "@/src/lib/layout/StyledMarkdown";
import clsx from "clsx";
import Markdown from "react-markdown";

interface RoleData {
    title: string;
    titleStyle: string;
    containerStyle: string;
    bubbleStyle: string;
}

export function ChatBubble({ message }: { message: ChatMessage }) {
    const { role, content } = message;
    const roleData = getAuthorStyle(role);
    return (
        <div className={clsx("flex mb-3", roleData.containerStyle)}>
            <div
                className={clsx(
                    "max-w-[75%] px-4 py-2 rounded-2xl shadow",
                    roleData.bubbleStyle
                )}
            >
                <h5
                    className={clsx(
                        "text-xs font-bold mb-1",
                        roleData.titleStyle
                    )}
                >
                    {roleData.title}
                </h5>
                <StyledMarkdown content={content} />
            </div>
        </div>
    );
}

function getAuthorStyle(author: ChatRoles): RoleData {
    switch (author) {
        case ChatRoles.USER:
            return {
                title: "Voce",
                titleStyle: "text-gray-100",
                containerStyle: "justify-end",
                bubbleStyle: "bg-blue-600 text-white rounded-tr-none",
            };
        case ChatRoles.ASSISTANT:
            return {
                title: "Assistente",
                titleStyle: "text-gray-500",
                containerStyle: "justify-start",
                bubbleStyle: "bg-gray-200 text-gray-900 rounded-tl-none",
            };
    }
}
