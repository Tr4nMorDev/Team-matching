import React from "react";
import { useGroupContext } from "../../context/GroupContext";
import { useNavigate } from "react-router-dom"; // Dùng useNavigate để chuyển hướng

const MyGroups = () => {
  const { isGroupJoined } = useGroupContext();
  const navigate = useNavigate(); // Khởi tạo navigate

  // Giả lập dữ liệu nhóm cá nhân
  const myGroups = [
    {
      name: "My Group 1",
      photo: "/avata.jpg",
      ki: "Ki 1",
      description: "This is a personal group",
      members: 5,
    },
    {
      name: "My Group 2",
      photo: "/avata.jpg",
      ki: "Ki 2",
      description: "Another personal group",
      members: 8,
    },
  ];

  const renderGroupList = (groupList) => (
    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-8 max-w-5xl mx-auto">
      {groupList.map((group, index) => {
        // Không cần kiểm tra isJoined vì chỉ cần hiển thị nút "Enroll"
        return (
          <div
            key={index}
            className="bg-white p-6 rounded-2xl shadow-lg text-center"
          >
            <img
              src={group.photo}
              alt={group.name}
              className="w-24 h-24 mx-auto rounded-full object-cover mb-4"
            />
            <h2 className="text-lg font-semibold text-blue-600">
              {group.name}
            </h2>
            <div className="text-lg font-semibold text-cyan-950">
              <h2>Kì : {group.ki}</h2>
            </div>
            <p className="text-gray-500 text-sm">{group.description}</p>
            <div className="flex justify-around text-sm text-gray-700 mt-4">
              <span>Member: {group.members}</span>
            </div>
            <button
              className="mt-4 py-2 px-4 rounded-full bg-blue-500 text-white cursor-pointer"
              onClick={() => {
                // Chuyển hướng đến trang nhóm với tên nhóm
                navigate(`/group/${group.name}`);
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
      {renderGroupList(myGroups)}
    </div>
  );
};

export default MyGroups;
