import { createContext, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom"; // ✅ Đúng
import axios from "axios";

// Tạo context
export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);
  const [role, setRole] = useState(null);
  const [token, setToken] = useState(localStorage.getItem("token") || null);
  const [hasFetched, setHasFetched] = useState(false);
  const navigate = useNavigate();

  // Kiểm tra và khôi phục trạng thái đăng nhập khi component mount

  const login = (token, userData) => {
    console.log("Token received:", token);
    setIsLoggedIn(true);
    setToken(token);
    setUser(userData);
    localStorage.setItem("token", token);
    localStorage.setItem("username", userData.username);
  };

  const logout = () => {
    setIsLoggedIn(false);
    setUser(null);
    setRole(null);
    setToken(null);
    localStorage.removeItem("token");
    navigate("/");
  };

  // Lấy thông tin người dùng sau khi đăng nhập
  const getProtectedData = async () => {
    try {
      const token = localStorage.getItem("token");
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
    const fetchUser = async () => {
      const storedToken = localStorage.getItem("token");
      if (storedToken && !user) {
        try {
          const data = await getProtectedData();
          setIsLoggedIn(true);
          setUser(data.user);
          setRole(data.role);
          setToken(storedToken);
        } catch {
          logout();
        }
      }
      setHasFetched(true);
    };
    fetchUser();
  }, [user]);

  return (
    <AuthContext.Provider
      value={{ isLoggedIn, role, user, token, login, logout }}
    >
      {children}
    </AuthContext.Provider>
  );
};
