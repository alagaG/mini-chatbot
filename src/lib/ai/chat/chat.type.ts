export enum ChatRoles {
    USER,
    ASSISTANT,
}

export interface ChatMessage {
    role: ChatRoles;
    content: string;
}
