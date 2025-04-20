import React, { useEffect, useState } from "react";
import { useGroupContext } from "../../context/GroupContext";
import { useNavigate } from "react-router-dom";
import CreateGroupForm from "./CreateGroupForm"; // Import CreateGroupForm component

const MyGroups = () => {
  const { username } = useGroupContext() || {};
  const navigate = useNavigate();
  const [myGroups, setMyGroups] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showCreateGroupForm, setShowCreateGroupForm] = useState(false); // State to control visibility of CreateGroupForm

  useEffect(() => {
    const localUsername = username || localStorage.getItem("username");
    if (!localUsername) {
      console.error("Username không tồn tại");
      setLoading(false);
      return;
    }

    const fetchGroups = async () => {
      const token = localStorage.getItem("token");
      if (!token) {
        console.error("Token không tồn tại");
        setLoading(false);
        return;
      }

      try {
        const response = await fetch(`/api/teams/user/${localUsername}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        if (!response.ok) throw new Error("Không thể tải danh sách nhóm");
        const data = await response.json();
        setMyGroups(data);
      } catch (error) {
        console.error("Lỗi khi tải nhóm:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchGroups();
  }, [username]);

  const handleCreateGroup = (newGroup) => {
    const formattedGroup = {
      teamName: newGroup.teamName,
      description: newGroup.description,
      photo: newGroup.teamPicture,  // Đảm bảo rằng trường này khớp với API trả về
      members: newGroup.members ? newGroup.members.length : 1, // Đảm bảo số lượng thành viên đúng
    };

    setMyGroups((prevGroups) => [...prevGroups, formattedGroup]);
  };

  const renderGroupList = (groupList) => (
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-8 max-w-5xl mx-auto">
        {groupList.map((group, index) => {
          return (
              <div key={index} className="bg-white p-6 rounded-2xl shadow-lg text-center">
                <img
                    src={group.photo || "/path/to/default-image.jpg"} // Thêm hình ảnh mặc định
                    alt={group.teamName}
                    className="w-24 h-24 mx-auto rounded-full object-cover mb-4"
                />
                <h2 className="text-lg font-semibold text-blue-600">
                  {group.teamName}
                </h2>
                <p className="text-gray-500 text-sm">{group.description}</p>
                <div className="flex justify-around text-sm text-gray-700 mt-4">
                  <span>Member: {group.members}</span>
                </div>
                <button
                    className="mt-4 py-2 px-4 rounded-full bg-blue-500 text-white cursor-pointer"
                    onClick={() => {
                      navigate(`/group/${group.teamName}`); // Đảm bảo sử dụng đúng tên nhóm
                    }}
                >
                  Enroll
                </button>
              </div>
          );
        })}
      </div>
  );

  return (
      <div>
        <h2 className="text-2xl font-bold mb-4 text-center">Nhóm của tôi</h2>
        {loading ? (
            <p className="text-center">Đang tải nhóm...</p> // Hiển thị khi đang tải dữ liệu
        ) : myGroups.length > 0 ? (
            renderGroupList(myGroups)
        ) : (
            <p className="text-center">Bạn chưa tham gia nhóm nào.</p> // Thông báo nếu không có nhóm
        )}

        {/* Button to show Create Group Form */}
        <div className="text-center mt-6">
          {showCreateGroupForm && (
              <CreateGroupForm
                  onCreate={handleCreateGroup}
                  onClose={() => setShowCreateGroupForm(false)}
              />
          )}
        </div>
      </div>
  );
};

export default MyGroups;
