import CreatePost from "./CreatePost";
import PostItem from "./PostItem";
import { useAuth } from "../../context/useAuth";

const MainContent = () => {
  const { isLoggedIn } = useAuth();

  return (
    <main className="flex justify-center bg-gray-100 min-h-screen py-10">
      <div className="w-full max-w-3xl bg-white shadow-md rounded-lg p-4 flex flex-col gap-4 mt-5">
        {/* Chỉ hiển thị CreatePost nếu đã đăng nhập */}
        {isLoggedIn && <CreatePost />}

        {/* Danh sách bài viết luôn hiển thị */}
        <PostItem />
        <PostItem />
      </div>
    </main>
  );
};

export default MainContent;
