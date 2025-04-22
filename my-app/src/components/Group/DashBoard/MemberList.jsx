import { useState, useEffect } from "react";
import { motion } from "framer-motion";
import axios from "axios";
import { useParams } from "react-router-dom";

const MemberList = () => {
    const [members, setMembers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const { teamId } = useParams();

    useEffect(() => {
        const token = localStorage.getItem("token");
        axios.get(`/api/teams/${teamId}/members`,{
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then(res => {
                setMembers(res.data);
                setLoading(false);
            })
            .catch(err => {
                setError("Failed to load members");
                setLoading(false);
            });
    }, [teamId]);
    console.log(members);
    if (loading) return <div className="p-4 text-center">Loading...</div>;
    if (error) return <div className="p-4 text-center text-red-500">{error}</div>;

    return (
        <div className="p-4 bg-white rounded-lg shadow-md">
            <h2 className="text-lg font-semibold mb-2">Members</h2>
            <ul className="space-y-2">
                {members.map((member) => (
                    <motion.li
                        key={member.id} // Sử dụng member.id thay vì index
                        initial={{ opacity: 0, y: 10 }}
                        animate={{ opacity: 1, y: 0 }}
                        transition={{ duration: 0.3 }}
                        className="flex items-center justify-between p-2 rounded-md hover:bg-gray-100"
                    >
                        <div className="flex items-center gap-2">
                            <img
                                src={member.profilePicture || "/avatar.jpg"}
                                alt="Avatar"
                                className="w-8 h-8 rounded-full"
                            />
                            <span className="font-medium">{member.fullName}</span>
                        </div>
                        <span className="text-sm text-gray-500">{member.roleInTeam}</span>
                    </motion.li>
                ))}
            </ul>
        </div>
    );
};

export default MemberList;
