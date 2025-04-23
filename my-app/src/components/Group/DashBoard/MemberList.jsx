import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import { motion } from "framer-motion";

const MemberList = () => {
    const { teamId } = useParams();
    const [members, setMembers] = useState([]);
    const [teamType, setTeamType] = useState(""); // "ACADEMIC" or "COMMUNITY"
    const [hasLecturer, setHasLecturer] = useState(false);
    const [isLeader, setIsLeader] = useState(false);
    const [isLecturer, setIsLecturer] = useState(false);
    const [loading, setLoading] = useState(true);
    const [deletingId, setDeletingId] = useState(null); // Để xử lý loading khi xoá

    const currentUserId = parseInt(localStorage.getItem("userId"));

    useEffect(() => {
        const fetchMembers = async () => {
            try {
                const token = localStorage.getItem("token");
                const res = await axios.get(`/api/teams/${teamId}/members`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

                setMembers(res.data);

                const lecturerExists = res.data.some(member => member.type === "LECTURER");
                const leader = res.data.find(member => member.type === "LEADER");
                const current = res.data.find(member => member.id === currentUserId);

                setHasLecturer(lecturerExists);
                setIsLeader(current?.type === "LEADER");
                setIsLecturer(current?.type === "LECTURER");

                console.log("Lecturer Exists: ", lecturerExists);
                console.log("Is Leader: ", current?.type === "LEADER");
                console.log("Has Lecturer: ", hasLecturer);

                const teamRes = await axios.get(`/api/teams/${teamId}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                console.log("Team Res: ", teamRes.data);

                setTeamType(teamRes.data.type);
                console.log("Team Type: ", teamRes.data.type);
            } catch (error) {
                console.error("Lỗi khi tải danh sách thành viên:", error);
            } finally {
                setLoading(false);
            }
        };

        if (teamId) fetchMembers();
    }, [teamId, currentUserId]);

    const handleDeleteMember = async (studentIdToRemove) => {
        if (!window.confirm("Bạn có chắc muốn xóa thành viên này khỏi nhóm?")) return;

        try {
            setDeletingId(studentIdToRemove);
            const token = localStorage.getItem("token");

            await axios.delete(`/api/teams/${teamId}/remove-student`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
                params: {
                    studentIdToRemove,
                },
            });

            setMembers(prev => prev.filter(member => member.id !== studentIdToRemove));
            alert("Xoá thành viên thành công!");
        } catch (error) {
            console.error("Lỗi khi xoá thành viên:", error);
            alert("Xoá thất bại. Có thể bạn không có quyền hoặc thành viên không tồn tại.");
        } finally {
            setDeletingId(null);
        }
    };

    if (loading) return <div>Đang tải danh sách thành viên...</div>;

    return (
        <div className="p-4 bg-white rounded-lg shadow-md">
            <div className="flex items-center justify-between mb-2">
                <h2 className="text-lg font-semibold">Members</h2>
                <div className="flex gap-2">
                    {(isLeader || isLecturer) && (
                        <button
                            className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600"
                            onClick={() => alert("Thêm thành viên")}
                        >
                            + Add Member
                        </button>
                    )}
                    {teamType === "ACADEMIC" && isLeader && !hasLecturer && (
                        <button
                            className="px-4 py-2 bg-green-500 text-white rounded-md hover:bg-green-600"
                            onClick={() => alert("Gán giảng viên")}
                        >
                            + Assign Lecturer
                        </button>
                    )}
                </div>
            </div>

            <ul className="space-y-2">
                {members.map((member, index) => {
                    const isBothLeaderAndLecturer =
                        member.type === "LEADER" && member.role === "LECTURER";

                    const canRemove =
                        (isLeader || isLecturer) && !isBothLeaderAndLecturer;

                    return (
                        <motion.li
                            key={member.id}
                            initial={{ opacity: 0, y: 10 }}
                            animate={{ opacity: 1, y: 0 }}
                            transition={{ duration: 0.3, delay: index * 0.1 }}
                            className="flex items-center justify-between p-2 rounded-md hover:bg-gray-100"
                        >
                            <div className="flex items-center gap-2">
                                <img
                                    src={"/avatar.jpg"}
                                    alt="Avatar"
                                    className="w-8 h-8 rounded-full"
                                />
                                <div>
                                    <span className="font-medium">{member.fullName}</span>
                                    <div className="text-sm text-gray-500">{member.type}</div>
                                </div>
                            </div>
                            {canRemove && (
                                <button
                                    onClick={() => handleDeleteMember(member.id)}
                                    disabled={deletingId === member.id}
                                    className={`text-red-500 hover:text-red-700 ${
                                        deletingId === member.id ? "opacity-50 cursor-not-allowed" : ""
                                    }`}
                                >
                                    {deletingId === member.id ? "Removing..." : "Remove"}
                                </button>
                            )}
                        </motion.li>
                    );
                })}
            </ul>
        </div>
    );
};

export default MemberList;
