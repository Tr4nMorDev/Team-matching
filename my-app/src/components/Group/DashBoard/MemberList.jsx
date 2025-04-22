import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import { motion } from "framer-motion";

const MemberList = () => {
    const { teamId } = useParams();
    const [members, setMembers] = useState([]);
    const [loading, setLoading] = useState(true);

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
            } catch (error) {
                console.error("Lỗi khi tải danh sách thành viên:", error);
            } finally {
                setLoading(false);
            }
        };

        if (teamId) {
            fetchMembers();
        }
    }, [teamId]);

    if (loading) return <div>Đang tải danh sách thành viên...</div>;

    return (
        <div className="p-4 bg-white rounded-lg shadow-md">
            <div className="flex items-center justify-between mb-2">
                <h2 className="text-lg font-semibold">Members</h2>
                <button
                    className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600"
                    onClick={() => alert("Thêm thành viên")}
                >
                    Thêm thành viên
                </button>
            </div>

            <ul className="space-y-2">
                {members.map((member, index) => (
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
                        <button
                            onClick={() => handleDeleteMember(member.id)}
                            className="text-red-500 hover:text-red-700"
                        >
                            Xóa
                        </button>
                    </motion.li>
                ))}
            </ul>
        </div>
    );
};

export default MemberList;
