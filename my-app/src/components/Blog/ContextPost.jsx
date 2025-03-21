import CreatePost from "./CreatePost";
import PostItem from "./PostItem";
import { useEffect, useState } from "react";
import { useAuth } from "../../context/useAuth";
import getPosts from "../../api/userApi";

const MainContent = () => {
  const { isLoggedIn } = useAuth();
  const [posts, setPosts] = useState([]);
  useEffect(() => {
    getPosts()
      .then((data) => setPosts(data))
      .catch(console.error);
  }, []);

  return (
    <main className="flex justify-center bg-gray-100 min-h-screen py-10">
      <div className="w-full max-w-3xl bg-white shadow-md rounded-lg p-4 flex flex-col gap-4 mt-5">
        {/* Chỉ hiển thị CreatePost nếu đã đăng nhập */}
        {isLoggedIn && <CreatePost />}

        {/* Danh sách bài viết luôn hiển thị */}
        {posts.map((post) => (
          <PostItem
            key={post.id}
            name={post.author.userName}
            avatar={post.author.profilePictureUrl}
            time={post.createdAt}
            image="http://localhost:8080/imagespost/image2.jpg"
            content={post.content}
            like={post.likeCount}
          />
        ))}
        <PostItem />
      </div>
    </main>
  );
};

export default MainContent;
prop;
