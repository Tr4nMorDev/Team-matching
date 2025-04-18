import { useEffect, useRef, useState } from "react";
import { ChevronLeft, ChevronRight, Search } from "lucide-react";
import { useAuth } from "../context/useAuth";
import BoxChatMini from "./BoxChatMini";
import axios from "axios";

export default function FriendsSidebar({ isOpen, toggleSidebar }) {
  const { isLoggedIn, user } = useAuth();
  const sidebarRef = useRef(null);
  const [friendsList, setFriendsList] = useState([]);
  const [activeChat, setActiveChat] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    const fetchFriends = async () => {
      if (!user?.id) return;
      try {
        const token = localStorage.getItem("token");
        const res = await axios.get(`/api/friends/list/${user.id}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        console.log("res.data", res.data);

        const mapped = res.data.map((f) => {
          const friendUser = f.requester.id === user.id ? f.receiver : f.requester;
          const avatar = friendUser.profilePicture || "/imagedefault.jpg";

          return {
            id: friendUser.id,
            name: friendUser.fullName,
            user: friendUser.role || "user",
            avatar,
          };
        });

        setFriendsList(mapped);
      } catch (err) {
        console.error("Error loading friends:", err);
      }
    };

    if (isLoggedIn) fetchFriends();
  }, [isLoggedIn, user?.id]);

  useEffect(() => {
    function handleClickOutside(event) {
      if (sidebarRef.current && !sidebarRef.current.contains(event.target)) {
        toggleSidebar();
      }
    }
    if (isOpen) document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, [isOpen, toggleSidebar]);

  const filteredFriends = friendsList.filter((friend) =>
      friend.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
      <>
        <button
            onClick={toggleSidebar}
            disabled={!isLoggedIn}
            className={`fixed top-1/2 right-0 transform -translate-y-1/2 p-3 rounded-l-full shadow-lg ${
                isLoggedIn
                    ? "bg-blue-500 text-white"
                    : "bg-blue-300 text-gray-500 cursor-not-allowed"
            }`}
        >
          {isOpen ? <ChevronRight size={20} /> : <ChevronLeft size={20} />}
        </button>

        <div
            ref={sidebarRef}
            className={`fixed top-20 right-0 h-full bg-white shadow-lg w-72 transition-all duration-500 ease-in-out transform z-20 rounded-2xl ${
                isOpen
                    ? "translate-x-0 opacity-100 scale-100"
                    : "translate-x-full opacity-0 scale-95"
            }`}
        >
          <div className="p-4">
            <h2 className="text-lg font-semibold mb-4">Friends</h2>

            <div className="relative mb-4">
              <input
                  type="text"
                  placeholder="Search friends..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="w-full px-4 py-2 pl-10 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-300"
              />
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={18} />
            </div>

            <div className="space-y-3 max-h-[65vh] overflow-y-auto pr-1">
              {filteredFriends.length > 0 ? (
                  filteredFriends.map((friend, index) => (
                      <button
                          key={index}
                          onClick={() => setActiveChat(friend)}
                          className="flex items-center gap-3 p-3 border rounded-lg w-full text-left hover:bg-gray-100 transition cursor-pointer"
                      >
                        <img
                            src={friend.avatar}
                            alt={friend.name}
                            className="w-10 h-10 rounded-full object-cover"
                        />
                        <div>
                          <p className="font-medium text-gray-900">{friend.name}</p>
                          <p className="text-sm text-gray-500 capitalize">{friend.user}</p>
                        </div>
                      </button>
                  ))
              ) : (
                  <p className="text-sm text-gray-500 text-center mt-4">No friends found</p>
              )}
            </div>
          </div>
        </div>

        {activeChat && (
            <BoxChatMini user={activeChat} onClose={() => setActiveChat(null)} />
        )}
      </>
  );
}
