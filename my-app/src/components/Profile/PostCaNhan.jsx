import { useState, useEffect } from "react";
import { MoreVertical, ThumbsUp, MessageCircle } from "lucide-react";
import { useAuth } from "../../context/useAuth";
import LoginModal from "../LoginModal";
import axios from "axios";

import dayjs from "dayjs";
import relativeTime from "dayjs/plugin/relativeTime";
import "dayjs/locale/vi";
const API_PROJECT = import.meta.env.VITE_HOST;
dayjs.extend(relativeTime);
dayjs.locale("vi");

const PostCaNhan = ({ blogs }) => {
  const { isLoggedIn, user } = useAuth();
  const [showLogin, setShowLogin] = useState(false);

  return (
    <>
      {blogs.map((blog) => (
        <div
          key={blog.id}
          className="p-4 mt-4 bg-white rounded-lg shadow relative"
        >
          {/* Header */}
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-3">
              <img
                src={user?.profilePicture || "/avata.jpg"}
                alt="Profile"
                className="h-10 w-10 rounded-full"
              />
              <div>
                <h3 className="font-semibold text-gray-900">
                  {user?.fullName || "Người dùng"}
                </h3>
                <p className="text-sm text-gray-500">
                  {blog.createdAt ? dayjs(blog.createdAt).fromNow() : "Chưa có"}
                </p>
              </div>
            </div>

            {/* Icon 3 chấm (chưa làm menu) */}
            <MoreVertical className="text-gray-500 cursor-pointer" />
          </div>

          {/* Nội dung blog */}
          <p className="mt-3 text-gray-700">{blog.content}</p>

          {/* Ảnh nếu có */}
          {blog.images && (
            <div className="w-full max-h-[600px] overflow-hidden rounded-lg mt-3">
              <img
                src={blog.images}
                alt="Ảnh bài blog"
                className="w-full h-auto max-h-[600px] object-cover"
              />
            </div>
          )}

          {/* Nút like & comment */}
          <div className="mt-3">
            <div className="flex justify-between items-center p-3 border-t border-gray-200 rounded-lg bg-gray-100">
              <button
                className="flex items-center gap-2 text-gray-700 hover:text-blue-500 cursor-pointer"
                onClick={() => {
                  if (!isLoggedIn) return setShowLogin(true);
                  // TODO: xử lý like
                }}
              >
                <ThumbsUp size={25} className="mr-2" />
                Thích
              </button>
              <button
                className="flex items-center gap-2 text-gray-700 hover:text-blue-500 cursor-pointer"
                onClick={() => {
                  if (!isLoggedIn) return setShowLogin(true);
                  // TODO: xử lý comment
                }}
              >
                <MessageCircle size={25} className="mr-2" />
                Bình luận
              </button>
            </div>
          </div>
        </div>
      ))}

      {showLogin && <LoginModal onClose={() => setShowLogin(false)} />}
    </>
  );
};

export default PostCaNhan;
