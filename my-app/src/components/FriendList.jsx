import { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "../context/useAuth.jsx";
import { useParams } from "react-router-dom";

const FriendsList = () => {
  const { userId } = useParams();
  const { user: currentUser } = useAuth();
  const [friends, setFriends] = useState([]);
  const [loading, setLoading] = useState(true); // Theo dõi trạng thái tải
  const [isFriend, setIsFriend] = useState(false); // Theo dõi trạng thái kết bạn với userId

  useEffect(() => {
    const idToUse = userId || currentUser?.id;
    if (!idToUse) return; // Ngừng gọi API nếu không có userId hoặc currentUser hợp lệ

    const fetchFriends = async () => {
      const token = localStorage.getItem("token");
      if (!token) return; // Đảm bảo có token trước khi thực hiện yêu cầu

      try {
        const response = await axios.get(`/api/friends/list/${idToUse}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setFriends(response.data);

        // Kiểm tra nếu currentUser đã là bạn với userId (nếu có userId)
        if (userId) {
          const friend = response.data.find(
            (friend) =>
              friend.requester.id === currentUser.id ||
              friend.receiver.id === currentUser.id
          );
          setIsFriend(!!friend); // Nếu tìm thấy bạn bè, set isFriend = true
        }
      } catch (error) {
        console.error("Không thể tải danh sách bạn bè:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchFriends();
  }, [userId, currentUser?.id]);

  const handleUnfriend = async (friendId) => {
    if (!window.confirm("Bạn có chắc muốn hủy kết bạn với người này?")) return;

    const token = localStorage.getItem("token");
    if (!token) return; // Đảm bảo có token trước khi thực hiện yêu cầu

    try {
      await axios.delete(`/api/friends/${friendId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      // Cập nhật lại danh sách bạn bè sau khi gỡ kết bạn
      setFriends((prev) => prev.filter((friend) => friend.id !== friendId));
    } catch (error) {
      console.error("Không thể hủy kết bạn:", error);
    }
  };

  const handleSendFriendRequest = async () => {
    const token = localStorage.getItem("token");
    if (!token) return; // Đảm bảo có token trước khi thực hiện yêu cầu

    try {
      // Gửi yêu cầu kết bạn (thay thế bằng endpoint thực tế)
      await axios.post(
        `/api/friends/request/${userId}`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      alert("Yêu cầu kết bạn đã được gửi!");
    } catch (error) {
      console.error("Không thể gửi yêu cầu kết bạn:", error);
    }
  };

  if (loading) {
    return <div>Đang tải...</div>; // Hiển thị trạng thái tải trong khi đang lấy danh sách bạn bè
  }

  // Tiêu đề hiển thị tùy thuộc vào việc có `userId` hay không
  const title = userId
    ? "Danh sách bạn bè của người này"
    : "Danh sách bạn bè của bạn";

  return (
    <div className="p-6 bg-white shadow-md rounded-lg w-full max-w-4xl mx-auto">
      <h2 className="text-2xl font-semibold mb-4 text-cyan-900">{title}</h2>
      <div className="flex space-x-4 border-b pb-2 text-gray-500">
        <span className="text-blue-500 cursor-pointer">Tất cả bạn bè</span>
      </div>
      <div className="grid grid-cols-2 gap-4 mt-4">
        {friends.map((friend) => {
          // Chọn người bạn để hiển thị dựa trên ngữ cảnh (userId hoặc currentUser)
          const displayFriend = userId ? friend.requester : friend.receiver;

          return (
            <div
              key={friend.id}
              className="flex items-center p-4 border rounded-lg shadow-sm bg-white"
            >
              <img
                src={displayFriend?.profilePicture || "/avata.jpg"}
                alt={displayFriend?.fullName}
                className="w-16 h-16 rounded-lg object-cover"
              />
              <div className="ml-4 flex-1">
                <h3 className="font-semibold text-gray-700">
                  {displayFriend?.fullName}
                </h3>
                <p className="text-sm text-gray-500">
                  Bạn bè từ: {new Date(friend.createdAt).toLocaleDateString()}
                </p>
              </div>

              {/* Hiển thị ký hiệu 'Bạn bè' nếu là bạn của currentUser */}
              {userId ? (
                isFriend ? (
                  <span className="text-green-600 font-semibold">Bạn bè</span>
                ) : (
                  <button
                    onClick={handleSendFriendRequest}
                    className="px-4 py-2 bg-blue-100 hover:bg-blue-200 text-blue-600 rounded-lg text-sm"
                  >
                    Gửi yêu cầu kết bạn
                  </button>
                )
              ) : (
                <button
                  onClick={() => handleUnfriend(friend.id)}
                  className="px-4 py-2 bg-red-100 hover:bg-red-200 text-red-600 rounded-lg text-sm"
                >
                  Hủy kết bạn
                </button>
              )}
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default FriendsList;
