import React from "react";
import { useGroupContext } from "../../context/GroupContext";

const CommunityGroups = () => {
  const { isGroupPending, addPendingGroup, isGroupJoined } = useGroupContext();

  // Giả lập dữ liệu nhóm cộng đồng
  const communityGroups = [
    {
      name: "Community Group 1",
      photo: "/avata.jpg",
      ki: "Ki 1",
      description: "This is a community group",
      members: 10,
    },
    {
      name: "Community Group 2",
      photo: "/avata.jpg",
      ki: "Ki 2",
      description: "Another community group",
      members: 20,
    },
  ];

  const renderGroupList = (groupList) => (
    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-8 max-w-5xl mx-auto">
      {groupList.map((group, index) => {
        const isPending = isGroupPending(group.name);
        const isJoined = isGroupJoined(group.name);

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
            {isPending && (
              <p className="text-yellow-500 text-sm">Chờ leader xử lý</p>
            )}
            <button
              className={`mt-4 py-2 px-4 rounded-full cursor-pointer ${
                isJoined
                  ? "bg-green-500 text-white"
                  : isPending
                  ? "bg-gray-400"
                  : "bg-blue-500 text-white"
              }`}
              onClick={() => {
                if (!isJoined && !isPending) {
                  addPendingGroup(group.name);
                }
              }}
              disabled={isPending}
            >
              {isJoined
                ? "Đã gia nhập"
                : isPending
                ? "Chờ leader xử lý"
                : "Xin vào nhóm"}
            </button>
          </div>
        );
      })}
    </div>
  );

  return (
    <div>
      <h2 className="text-2xl font-bold mb-4 text-center">Nhóm cộng đồng</h2>
      {renderGroupList(communityGroups)}
    </div>
  );
};

export default CommunityGroups;
