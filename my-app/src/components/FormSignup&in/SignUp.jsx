import React, { useState } from "react";
import axios from "axios";

function SignUp() {
  const [formData, setFormData] = useState({
    fullName: "",
    username: "",
    email: "",
    password: "",
    role: "",
    gender: "",
    profilePicture: "",
    skills: [], // 🆕 Mảng kỹ năng
    hobbies: [], // 🆕 Mảng sở thích
    projects: [], // 🆕 Mảng dự án
    phoneNumber: "",
    major: "",
    term: 1,
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async () => {
    console.log("Form data before sending:", JSON.stringify(formData, null, 2));
    try {
      const response = await axios.post(
        "http://localhost:8080/api/users",
        formData
      );
      console.log("Sign up successful:", response.data);
      alert("Sign up successful!");
    } catch (error) {
      console.error("Error signing up:", error.response?.data || error.message);
      alert("Sign up failed!");
    }
  };

  return (
    <>
      <h2 className="text-2xl font-semibold mt-9 text-gray-900">Sign up</h2>
      <p className="text-gray-500 mb-6">Create an account to get started.</p>

      <input
        type="text"
        name="fullName"
        placeholder="Full name"
        value={formData.fullName}
        onChange={handleChange}
        className="w-full p-2 mb-4 border rounded-md text-gray-600"
      />
      <input
        type="text"
        name="username"
        placeholder="username"
        value={formData.username}
        onChange={handleChange}
        className="w-full p-2 mb-4 border rounded-md text-gray-600"
      />
      <input
        type="email"
        name="email"
        placeholder="Email"
        value={formData.email}
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

      <select
        name="role"
        value={formData.role}
        onChange={handleChange}
        className="w-full p-2 mb-4 border rounded-md text-gray-600"
      >
        <option value="student">STUDENT</option>
        <option value="lecturer">LECTURER</option>
      </select>

      <select
        name="gender"
        value={formData.gender}
        onChange={handleChange}
        className="w-full p-2 mb-4 border rounded-md text-gray-600"
      >
        <option value="male">MALE</option>
        <option value="female">FEMALE</option>
      </select>

      <select
        name="major"
        value={formData.major}
        onChange={handleChange}
        className="w-full p-2 mb-4 border rounded-md text-gray-600"
      >
        <option value="Logistics">Logistics</option>
        <option value="Information Technology">Information Technology</option>
        <option value="Marketing">Marketing</option>
        <option value="media">Media</option>
      </select>

      <button
        onClick={handleSubmit}
        className="w-full bg-blue-500 text-white p-2 rounded-md font-semibold cursor-pointer mb-4"
      >
        Sign Up
      </button>

      <p className="text-sm text-gray-600 text-center">
        Already have an account?{" "}
        <button
          onClick={() => alert("Toggle to Sign In")}
          className="text-blue-500 cursor-pointer"
        >
          Sign in
        </button>
      </p>
    </>
  );
}

export default SignUp;
