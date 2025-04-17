import { useEffect, useState } from "react";
import axios from "axios";

const FriendsList = ({ userId = 1 }) => {
    const [friends, setFriends] = useState([]);

    useEffect(() => {
        const fetchFriends = async () => {
            try {
                const response = await axios.get(`/api/friends/list/${userId}`);
                const mapped = response.data.map((f) => {
                    const isRequester = f.requesterId === userId;
                    const friendName = isRequester ? f.receiverName : f.requesterName;
                    const friendId = isRequester ? f.receiverId : f.requesterId;

                    return {
                        id: f.id,
                        name: friendName,
                        friendId: friendId,
                        friends: 0,
                        image: "/avata.jpg",
                    };
                });
                setFriends(mapped);
            } catch (error) {
                console.error("Failed to fetch friends:", error);
            }
        };

        fetchFriends();
    }, [userId]);

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
                            src={friend.image}
                            alt={friend.name}
                            className="w-16 h-16 rounded-lg object-cover"
                        />
                        <div className="ml-4 flex-1">
                            <h3 className="font-semibold text-gray-700">{friend.name}</h3>
                            <p className="text-sm text-gray-500">{friend.friends} friends</p>
                        </div>
                        <button className="px-4 py-2 bg-gray-200 rounded-lg text-gray-700 font-semibold">
                            ✓ Friend
                        </button>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default FriendsList;
