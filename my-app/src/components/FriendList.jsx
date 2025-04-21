import { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "../context/useAuth.jsx";

const FriendsList = () => {
    const { user: currentUser } = useAuth();
    const [friends, setFriends] = useState([]);

    useEffect(() => {
        const fetchFriends = async () => {
            const token = localStorage.getItem("token");
            try {
                const response = await axios.get(`/api/friends/list/${currentUser.id}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                setFriends(response.data);
            } catch (error) {
                console.error("Failed to fetch friends:", error);
            }
        };

        fetchFriends();
    }, [currentUser.id]);

    const handleUnfriend = async (friendId) => {
        if (!window.confirm("Are you sure you want to unfriend this person?")) return;

        const token = localStorage.getItem("token");
        try {
            await axios.delete(`/api/friends/${friendId}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            // Cập nhật lại danh sách bạn bè sau khi gỡ kết bạn
            setFriends((prev) => prev.filter((friend) => friend.id !== friendId));
        } catch (error) {
            console.error("Failed to unfriend:", error);
        }
    };

    return (
        <div className="p-6 bg-white shadow-md rounded-lg w-full max-w-4xl mx-auto">
            <h2 className="text-2xl font-semibold mb-4 text-cyan-900">Friends</h2>
            <div className="flex space-x-4 border-b pb-2 text-gray-500">
                <span className="text-blue-500 cursor-pointer">All Friends</span>
            </div>
            <div className="grid grid-cols-2 gap-4 mt-4">
                {friends.map((friend) => (
                    <div
                        key={friend.id}
                        className="flex items-center p-4 border rounded-lg shadow-sm bg-white"
                    >
                        <img
                            src={friend.receiver?.profilePicture || "/avata.jpg"}
                            alt={friend.receiver?.fullName}
                            className="w-16 h-16 rounded-lg object-cover"
                        />
                        <div className="ml-4 flex-1">
                            <h3 className="font-semibold text-gray-700">
                                {friend.receiver?.fullName}
                            </h3>
                            <p className="text-sm text-gray-500">
                                Friend since: {new Date(friend.createdAt).toLocaleDateString()}
                            </p>
                        </div>
                        <button
                            onClick={() => handleUnfriend(friend.id)}
                            className="px-4 py-2 bg-red-100 hover:bg-red-200 text-red-600 rounded-lg text-sm"
                        >
                            Unfriend
                        </button>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default FriendsList;
