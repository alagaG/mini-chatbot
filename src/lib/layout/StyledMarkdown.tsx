import clsx from "clsx";
import Markdown from "react-markdown";

export function StyledMarkdown({ content }: { content: string }) {
    return (
        <div className={clsx("[&>h1]:text-lg", "[&>p]:mb-2", "[&>hr]:mb-2")}>
            <Markdown>{content}</Markdown>
        </div>
    );
}
