import { useState } from "react";
import { MoreVertical } from "lucide-react"; // Import icon 3 chấm
import { useAuth } from "../../context/useAuth";
import LoginModal from "../LoginModal";
import { ThumbsUp, MessageCircle } from "lucide-react";

// { avatar , time , content , like ,comment }
const BlogItem = ({ name, avatar, images, time, content, like }) => {
  const { isLoggedIn } = useAuth();
  const [showMenu, setShowMenu] = useState(false);
  const [showLogin, setShowLogin] = useState(false);

  return (
    <>
      <div className="p-4 mt-4 bg-white rounded-lg shadow relative">
        {/* Header */}
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-3">
            <img
              src={avatar}
              alt="Profile"
              className="h-10 w-10 rounded-full"
            />
            <div>
              <h3 className="font-semibold text-gray-900 ">{name}</h3>
              <p className="text-sm text-gray-500">
                -----------------------------------------------------------------------------------------
              </p>
            </div>
          </div>

          {/* Nút ba chấm */}
          {/* <button
            onClick={() => {
              if (!isLoggedIn) return setShowLogin(true);
              () => setShowMenu(!showMenu);
            }}
            className="p-2 rounded-full cursor-pointer bg-blue-300 "
          >
            <MoreVertical size={20} />
          </button> */}

          {/* Menu hiển thị khi bấm */}
          {showMenu && (
            <div className="absolute right-4 top-12 w-40 bg-white border border-gray-300 rounded-lg shadow-lg">
              <button className="w-full px-4 py-2 text-left bg-gray-100 cursor-pointer text-blue-700">
                Kết bạn
              </button>
            </div>
          )}
        </div>

        {/* Nội dung bài blog */}
        <p className="mt-3 text-gray-700">{content}</p>

        {/* Ảnh bài blog */}
        <div className="w-full max-h-[600px] overflow-hidden rounded-lg mt-3">
          <img
            src={images}
            alt="Blog"
            className="w-full h-auto max-h-[600px] object-cover"
          />
        </div>

        {/* Nút like & comment */}
        <div className="mt-3">
          <div className="flex justify-between items-center p-3 border-t border-gray-200 rounded-lg bg-gray-100">
            <button
              className="flex items-center gap-2 text-gray-700 hover:text-blue-500 cursor-pointer"
              onClick={() => {
                if (!isLoggedIn) return setShowLogin(true);
              }}
            >
              <ThumbsUp size={18} className="mr-2" />
              {like} Thích
            </button>
            <button
              className="flex items-center gap-2 text-gray-700 hover:text-blue-500 cursor-pointer"
              onClick={() => {
                if (!isLoggedIn) return setShowLogin(true);
              }}
            >
              <MessageCircle size={18} className="mr-2" />
              Bình luận
            </button>
          </div>
        </div>
      </div>
      {showLogin && <LoginModal onClose={() => setShowLogin(false)} />}
    </>
  );
};

export default BlogItem;
