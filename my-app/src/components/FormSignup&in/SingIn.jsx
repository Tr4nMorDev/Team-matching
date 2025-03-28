import React, { useState, useContext } from "react";
import axios from "axios";
import { AuthContext } from "../../context/AuthContext";

function SignIn({ handleToggle, onClose }) {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });
  const { login } = useContext(AuthContext);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async () => {
    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/signin",
        formData
      );

      console.log("Full response data:", response.data); // 🔍 Debug

      const { token, userData } = response.data;
      console.log("Sign in successful:", userData);
      localStorage.setItem("token", token);
      login(token, userData);
      onClose();
    } catch (error) {
      console.error("Error signing in:", error.response?.data || error.message);
    }
  };

  return (
    <>
      <h2 className="text-2xl font-semibold mt-20 text-gray-900">Sign in</h2>
      <p className="text-gray-500 mb-6">
        Enter your username and password to continue.
      </p>

      <input
        type="text"
        name="username"
        placeholder="Username"
        value={formData.username}
        onChange={handleChange}
        className="w-full p-2 mb-4 border rounded-md text-gray-600"
      />
      <input
        type="password"
        name="password"
        placeholder="Password"
        value={formData.password}
        onChange={handleChange}
        className="w-full p-2 mb-4 border rounded-md text-gray-600"
      />

      <button
        onClick={handleSubmit}
        className="w-full bg-blue-500 text-white p-2 rounded-md font-semibold cursor-pointer mb-4"
      >
        Sign in
      </button>

      <p className="text-sm text-gray-600 text-center">
        Don't have an account?{" "}
        <button onClick={handleToggle} className="text-blue-500 cursor-pointer">
          Sign up
        </button>
      </p>
    </>
  );
}

export default SignIn;
