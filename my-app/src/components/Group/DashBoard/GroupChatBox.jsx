import { useState, useEffect, useRef } from "react";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

const GroupChatBox = ({ groupName, teamId }) => {
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState("");
    const stompClient = useRef(null);

    useEffect(() => {
        const socket = new SockJS("http://localhost:8080/ws");
        stompClient.current = new Client({
            webSocketFactory: () => socket,
            reconnectDelay: 5000,
            onConnect: () => {
                // Subscribe to the group chat (team) channel
                stompClient.current.subscribe(`/topic/team.${teamId}`, (message) => {
                    const newMessage = JSON.parse(message.body);
                    setMessages((prev) => [...prev, newMessage]);
                });
            },
        });
        stompClient.current.activate();

        return () => stompClient.current.deactivate(); // Cleanup on unmount
    }, [teamId]);

    const handleSendMessage = () => {
        if (input.trim()) {
            const newMessage = { sender: "You", text: input };
            setMessages([...messages, newMessage]);

            stompClient.current.publish({
                destination: "/app/chat.send",
                body: JSON.stringify({
                    teamId: teamId,
                    content: input,
                }),
            });

            setInput("");
        }
    };

    return (
        <div className="bg-gray-50 p-3 rounded-lg shadow mt-4">
            <h3 className="text-lg font-semibold mb-2">Chat nhóm: {groupName}</h3>
            <div className="h-64 overflow-y-auto p-2 bg-white rounded-lg mb-2">
                {messages.map((message, index) => (
                    <div key={index} className="mb-1 p-2 rounded-lg bg-blue-100 text-gray-700">
                        <span className="font-semibold">{message.sender}: </span>
                        {message.text}
                    </div>
                ))}
            </div>
            <div className="flex gap-2">
                <input
                    type="text"
                    placeholder="Nhập tin nhắn..."
                    value={input}
                    onChange={(e) => setInput(e.target.value)}
                    className="flex-1 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-300"
                />
                <button
                    onClick={handleSendMessage}
                    className="px-4 py-1 bg-blue-500 text-white rounded-md hover:bg-blue-600"
                >
                    Gửi
                </button>
            </div>
        </div>
    );
};

export default GroupChatBox;
