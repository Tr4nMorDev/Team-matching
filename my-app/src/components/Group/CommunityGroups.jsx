import React, { useEffect, useState } from "react";
import axios from "axios";
import { useGroupContext } from "../../context/GroupContext";

const CommunityGroups = () => {
  const [communityGroups, setCommunityGroups] = useState([]);
  const { isGroupPending, addPendingGroup, isGroupJoined } = useGroupContext();

  useEffect(() => {
    const fetchCommunityGroups = async () => {
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get("http://localhost:8080/api/teams/community-available", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setCommunityGroups(response.data);
      } catch (error) {
        console.error("Lỗi khi lấy danh sách nhóm cộng đồng:", error);
      }
    };

    fetchCommunityGroups();
  }, []);

  const renderGroupList = (groupList) => (
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-8 max-w-5xl mx-auto">
        {groupList.map((group) => {
          const isPending = isGroupPending(group.id);
          const isJoined = isGroupJoined(group.id);

          return (
              <div
                  key={group.id}
                  className="bg-white p-6 rounded-2xl shadow-lg text-center"
              >
                <img
                    src={group.teamPicture || "/default-avatar.jpg"}
                    alt={group.teamName}
                    className="w-24 h-24 mx-auto rounded-full object-cover mb-4"
                />
                <h2 className="text-lg font-semibold text-blue-600">
                  {group.teamName}
                </h2>
                <p className="text-gray-500 text-sm">{group.description}</p>
                <div className="flex justify-around text-sm text-gray-700 mt-4">
                  <span>Thành viên: {group.students?.length || 0}</span>
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
                        addPendingGroup(group.id);
                        // TODO: Gửi join request API tại đây
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
