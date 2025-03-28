import { createContext, useState, useEffect } from "react";

// Tạo context
export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);
  const [role, setRole] = useState(null);
  const [token, setToken] = useState(localStorage.getItem("token") || null); // Lưu token vào state

  const login = (token, userData) => {
    setIsLoggedIn(true);
    setToken(token); // Lưu token vào state
    setUser(userData); // Lưu thông tin người dùng vào state
    localStorage.setItem("token", token); // Lưu token vào localStorage
  };

  const logout = () => {
    setIsLoggedIn(false);
    setUser(null);
    setRole(null);
    setToken(null);
    localStorage.removeItem("token"); // Xóa token khỏi localStorage
  };

  // Lấy thông tin người dùng sau khi đăng nhập
  const fetchUserData = async () => {
    try {
      const response = await fetch(
        "https://jsonplaceholder.typicode.com/users/1"
      );
      const userData = await response.json();
      setUser(userData);
      setRole(userData.username);
      console.log(userData.username);
    } catch (error) {
      console.error("Lỗi khi lấy dữ liệu người dùng:", error);
    }
  };

  // Lấy thông tin người dùng khi đăng nhập
  useEffect(() => {
    if (isLoggedIn && token) {
      fetchUserData();
    }
  }, [isLoggedIn, token]);

  return (
    <AuthContext.Provider
      value={{ isLoggedIn, role, user, token, login, logout }}
    >
      {children}
    </AuthContext.Provider>
  );
};
