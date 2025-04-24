import { useEffect, useState } from "react";
import { motion } from "framer-motion";
import { useParams } from "react-router-dom";

const MemberRequestList = () => {
    const [requests, setRequests] = useState([]);
    const { teamId } = useParams();

    // Fetch danh sách yêu cầu gia nhập nhóm khi component mount
    useEffect(() => {
        const fetchJoinRequests = async () => {
            const token = localStorage.getItem("token");
            try {
                const response = await fetch(`/api/teams/${teamId}/join-requests`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                if (!response.ok) {
                    throw new Error("Failed to fetch requests");
                }
                const data = await response.json();
                console.log(response);
                setRequests(data); // Cập nhật danh sách yêu cầu
            } catch (error) {
                console.error("Error fetching join requests:", error);
            }
        };

        fetchJoinRequests();
    }, [teamId]);

    // Xử lý yêu cầu gia nhập nhóm (chấp nhận hoặc từ chối)
    const handleJoinRequest = async (studentId, accept) => {
        const token = localStorage.getItem("token");
        try {
            const response = await fetch(`/api/teams/${teamId}/join-requests/${studentId}/handle?accept=${accept}`, {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            if (!response.ok) {
                throw new Error("Failed to handle request");
            }

            const result = await response.json();
            console.log(result);

            // Cập nhật lại danh sách yêu cầu sau khi xử lý
            setRequests(requests.filter(request => request.studentId !== studentId)); // Loại bỏ yêu cầu đã xử lý
        } catch (error) {
            console.error("Error handling join request:", error);
        }
    };

    return (
        <div className="p-4 bg-white rounded-lg shadow-md">
            <h2 className="text-lg font-semibold mb-2">Member Requests</h2>
            <ul className="space-y-2">
                {requests.map((request, index) => (
                    <motion.li
                        key={index}
                        initial={{ opacity: 0, y: 10 }}
                        animate={{ opacity: 1, y: 0 }}
                        transition={{ duration: 0.3, delay: index * 0.1 }}
                        className="flex items-center justify-between gap-2 p-2 rounded-md hover:bg-gray-100"
                    >
                        <div className="flex items-center gap-3">
                            <img
                                src={request.avatar} // Đảm bảo backend trả về avatar
                                alt="Avatar"
                                className="w-10 h-10 rounded-full"
                            />
                            <span className="font-medium">{request.fullName}</span>
                        </div>
                        <div className="space-x-2">
                            <button
                                className="px-3 py-1 bg-green-500 text-white rounded-md hover:bg-green-600"
                                onClick={() => handleJoinRequest(request.id, true)}
                            >
                                Chấp nhận
                            </button>
                            <button
                                className="px-3 py-1 bg-red-500 text-white rounded-md hover:bg-red-600"
                                onClick={() => handleJoinRequest(request.id, false)}
                            >
                                Từ chối
                            </button>
                        </div>
                    </motion.li>
                ))}
            </ul>
        </div>
    );
};

export default MemberRequestList;
