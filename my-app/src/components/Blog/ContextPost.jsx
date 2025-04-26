import CreateBlog from "./CreatePost";
import BlogItem from "./BlogItem";
import { useAuth } from "../../context/useAuth";
import getBlogs from "../../api/userApi";
import { data } from "react-router-dom";
import { useEffect, useState, useRef } from "react";

import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

const MainContent = () => {
  const { isLoggedIn } = useAuth();
  const [blogs, setBlogs] = useState([]);
  const stompClientRef = useRef(null);
  useEffect(() => {
    let isMounted = true; // đảm bảo không setState nếu component đã unmount
    // Gọi API lấy danh sách ban đầu
    getBlogs()
      .then((data) => {
        if (isMounted) {
          setBlogs(data);
        }
      })
      .catch(console.error);

    // Kết nối WebSocket
    const socket = new SockJS("http://localhost:8080/ws"); // đổi đúng endpoint của backend bạn
    const stompClient = Stomp.over(socket);
    stompClient.connect({}, () => {
      console.log("Connected to WebSocket");
      // Sub vào topic blog mới
      stompClient.subscribe("/topic/blogs", (message) => {
        const newBlog = JSON.parse(message.body);
        setBlogs((prevBlogs) => [newBlog, ...prevBlogs]); // thêm blog mới vào đầu
      });
    });

    // Lưu lại client để ngắt kết nối khi unmount
    stompClientRef.current = stompClient;

    return () => {
      // Cleanup
      if (stompClientRef.current) {
        stompClientRef.current.disconnect(() => {
          console.log("Disconnected from WebSocket");
        });
      }
    };
  }, []);

  return (
    <main className="flex justify-center bg-gray-100 min-h-screen py-10">
      <div className="w-full max-w-3xl bg-white shadow-md rounded-lg p-4 flex flex-col gap-4 mt-5">
        {/* Chỉ hiển thị CreatePost nếu đã đăng nhập */}
        {isLoggedIn && <CreateBlog />}

        {/* Danh sách bài viết luôn hiển thị */}

        {blogs.map((blog) => {
          // console.log(blog); // 👈 check object ở đây
          return <BlogItem key={blog.id} postId={blog.id} blogs={blog} />;
        })}
      </div>
    </main>
  );
};

export default MainContent;
