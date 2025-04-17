import { useEffect, useRef, useState } from "react";
import { motion } from "framer-motion";
import { X } from "lucide-react";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

export default function ChatSocket({ userId, user, onClose }) {
  const stompClient = useRef(null);
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState("");

  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/ws");
    stompClient.current = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      onConnect: () => {
        stompClient.current.subscribe(`/user/queue/private`, (message) => {
          const newMsg = JSON.parse(message.body);
          setMessages((prev) => [...prev, {
            sender: newMsg.senderName ?? "Người gửi",
            text: newMsg.content,
            avatar: newMsg.senderAvatar ?? "/default.jpg",
          }]);
        });
      },
    });
    stompClient.current.activate();
    return () => stompClient.current.deactivate();
  }, [userId]);

  const sendMessage = () => {
    if (input.trim()) {
      const newMsg = {
        sender: "Bạn",
        text: input,
        avatar: user.avatar,
      };
      setMessages((prev) => [...prev, newMsg]);

      stompClient.current.publish({
        destination: "/app/chat.send",
        body: JSON.stringify({
          receiverId: 2, // hoặc teamId
          content: input,
        }),
      });

      setInput("");
    }
  };

  return (
      <motion.div
          initial={{ opacity: 0, y: 50 }}
          animate={{ opacity: 1, y: 0 }}
          exit={{ opacity: 0, y: 50 }}
          transition={{ duration: 0.3 }}
          className="fixed bottom-10 right-75 w-80 bg-white shadow-lg rounded-lg border border-gray-700"
      >
        <div className="flex items-center justify-between p-3 bg-blue-500 text-white rounded-t-lg">
          <div className="flex items-center gap-2">
            <img src={user.avatar} alt="Avatar" className="w-8 h-8 rounded-full" />
            <span className="font-semibold text-gray-900">{user.name}</span>
          </div>
          <button onClick={onClose} className="hover:bg-blue-600 p-1 rounded-full cursor-pointer">
            <X size={20} />
          </button>
        </div>

        <div className="p-3 h-60 overflow-y-auto space-y-2">
          {messages.map((msg, index) => (
              <motion.div
                  key={index}
                  initial={{ opacity: 0, x: msg.sender === "Bạn" ? 30 : -30 }}
                  animate={{ opacity: 1, x: 0 }}
                  transition={{ duration: 0.2 }}
                  className={`flex items-center gap-2 ${msg.sender === "Bạn" ? "justify-end" : ""}`}
              >
                {msg.sender !== "Bạn" && (
                    <img src={msg.avatar} alt="Avatar" className="w-8 h-8 rounded-full" />
                )}
                <div className={`p-2 rounded-lg w-fit max-w-[80%] text-amber-700 ${msg.sender === "Bạn" ? "bg-blue-100" : "bg-gray-200"}`}>
                  <p className="text-sm">{msg.text}</p>
                </div>
                {msg.sender === "Bạn" && (
                    <img src={msg.avatar} alt="Avatar" className="w-8 h-8 rounded-full" />
                )}
              </motion.div>
          ))}
        </div>

        <div className="p-2 border-t flex">
          <input
              type="text"
              value={input}
              onChange={(e) => setInput(e.target.value)}
              onKeyDown={(e) => e.key === "Enter" && sendMessage()}
              className="flex-1 text-gray-800 p-2 text-sm border rounded-l-lg focus:outline-none"
              placeholder="Nhập tin nhắn..."
          />
          <button
              onClick={sendMessage}
              className="bg-blue-500 text-white px-3 rounded-r-lg hover:bg-blue-600 cursor-pointer"
          >
            Gửi
          </button>
        </div>
      </motion.div>
  );
}
