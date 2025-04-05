import { createContext, useState, useEffect } from "react";
import axios from "axios";

// Tạo context
export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);
  const [role, setRole] = useState(null);
  const [token, setToken] = useState(localStorage.getItem("token") || null); // Lưu token vào state

  const login = (token, userData) => {
    console.log("Token received:", token); // Kiểm tra token có đúng không
    setIsLoggedIn(true);
    setToken(token);
    setUser(userData);
    localStorage.setItem("token", token);
  };

  const logout = () => {
    setIsLoggedIn(false);
    setUser(null);
    setRole(null);
    setToken(null);
    localStorage.removeItem("token"); // Xóa token khỏi localStorage
  };

  // Lấy thông tin người dùng sau khi đăng nhập
  const getProtectedData = async () => {
    try {
      const token = localStorage.getItem("token"); // hoặc cách lưu token của bạn
      const response = await fetch(
        "http://localhost:8080/api/protected-resource",
        {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();
      return data;
    } catch (error) {
      console.error("Error fetching protected data:", error);
      throw error;
    }
  };

  // Lấy thông tin người dùng khi đăng nhập
  useEffect(() => {
    if (isLoggedIn && token) {
      getProtectedData();
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
