import { useEffect, useState } from "react";
import axios from "axios";

function SearchBar() {
    const [search, setSearch] = useState("");
    const [results, setResults] = useState({ users: [], teams: [] });
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem("token");
        const cancelToken = axios.CancelToken.source(); // Tạo token hủy yêu cầu

        const delayDebounce = setTimeout(() => {
            if (search.trim() !== "") {
                setLoading(true);
                axios
                    .get(`/api/search?keyword=${encodeURIComponent(search)}`, {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                        cancelToken: cancelToken.token, // Gắn token hủy
                    })
                    .then((res) => setResults(res.data))
                    .catch((err) => {
                        if (!axios.isCancel(err)) {
                            console.error("Search failed", err);
                        }
                    })
                    .finally(() => setLoading(false));
            } else {
                setResults({ users: [], teams: [] });
                setLoading(false);
            }
        }, 300);

        return () => {
            clearTimeout(delayDebounce);
            cancelToken.cancel(); // Hủy yêu cầu khi component unmount hoặc search thay đổi
        };
    }, [search]);

    return (
        <div className="relative w-full max-w-md mx-auto">
            <input
                type="text"
                value={search}
                onChange={(e) => setSearch(e.target.value)}
                placeholder="Tìm kiếm người dùng hoặc nhóm..."
                className="w-full px-4 py-2 bg-blue-100 rounded-lg outline-none text-black"
            />

            {search && (
                <div className="absolute top-full left-0 right-0 bg-white border border-gray-200 shadow-lg mt-1 rounded-md z-50 max-h-72 overflow-y-auto">
                    {loading && <p className="p-2 text-gray-500">🔍 Đang tìm kiếm...</p>}

                    {!loading && results.users.length === 0 && results.teams.length === 0 && (
                        <p className="p-2 text-gray-500">Không có kết quả phù hợp.</p>
                    )}

                    {!loading && (
                        <>
                            {results.users.length > 0 && (
                                <div className="border-b border-gray-300">
                                    <p className="px-2 pt-2 text-xs text-gray-400">👤 Người dùng</p>
                                    {results.users.map((user) => (
                                        <div key={user.id} className="p-2 hover:bg-gray-100 cursor-pointer">
                                            {user.fullName}
                                        </div>
                                    ))}
                                </div>
                            )}

                            {results.teams.length > 0 && (
                                <div className="pt-2">
                                    <p className="px-2 pt-2 text-xs text-gray-400">👥 Nhóm</p>
                                    {results.teams.map((team) => (
                                        <div key={team.id} className="p-2 hover:bg-gray-100 cursor-pointer">
                                            {team.teamName} {/* Sử dụng đúng thuộc tính */}
                                        </div>
                                    ))}
                                </div>
                            )}
                        </>
                    )}
                </div>
            )}
        </div>
    );
}

export default SearchBar;
