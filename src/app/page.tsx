import { ChatBot } from "../components/chat";

export default function HomePage() {
    return (
        <main className="flex justify-center items-center min-h-screen bg-gray-50">
            <ChatBot.Window />
        </main>
    );
}
