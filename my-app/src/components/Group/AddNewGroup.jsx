import React, { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import axios from 'axios';  // Thêm axios để gửi yêu cầu HTTP

const CreateGroupForm = ({ onCreate, onClose }) => {
  const [groupName, setGroupName] = useState("");
  const [groupDescription, setGroupDescription] = useState("");
  const [groupType, setGroupType] = useState("Nhóm học thuật");
  const [groupPhoto, setGroupPhoto] = useState("/avata.jpg");

  const handlePhotoChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const imageUrl = URL.createObjectURL(file);
      setGroupPhoto(imageUrl);
    }
  };

// Hàm gửi yêu cầu POST đến backend
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (groupName && groupDescription) {
      const newGroup = {
        teamName: groupName,
        description: groupDescription,
        teamType: groupType === "Nhóm học thuật" ? "ACADEMIC" : "EXTERNAL",
        teamPicture: groupPhoto,
        creatorId: 101,        // 👈 Thay bằng giá trị thực tế từ context hoặc props
        isLecturer: false,     // 👈 Nếu là sinh viên thì false
      };

      try {
        const response = await axios.post("http://localhost:8080/api/teams", newGroup);

        if (response.status === 201) {
          onCreate(newGroup);
          setGroupName("");
          setGroupDescription("");
          setGroupType("Nhóm học thuật");
          onClose();
        }
      } catch (error) {
        console.error("Lỗi khi tạo nhóm:", error);
        alert("Đã có lỗi xảy ra khi tạo nhóm. Vui lòng thử lại.");
      }
    }
  };

  const handleBackdropClick = (e) => {
    if (e.target === e.currentTarget) {
      onClose();
    }
  };

  return (
    <AnimatePresence>
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
        transition={{ duration: 0.5, ease: "easeOut" }}
        className="fixed inset-0 flex items-center justify-center bg-black/30 backdrop-blur-md z-20"
        onClick={handleBackdropClick}
      >
        <motion.div
          initial={{ scale: 0.8, opacity: 0 }}
          animate={{ scale: 1, opacity: 1 }}
          exit={{ scale: 0.8, opacity: 0 }}
          transition={{ duration: 0.3, ease: "easeOut" }}
          className="relative bg-white bg-opacity-90 rounded-[30px] shadow-lg w-[500px] p-6"
          onClick={(e) => e.stopPropagation()}
        >
          <h2 className="text-2xl font-bold mb-4 text-center">
            Create New Group
          </h2>
          <div className="flex justify-center mb-4">
            <label className="relative cursor-pointer">
              <img
                src={groupPhoto}
                alt="Group"
                className="w-32 h-32 rounded-full object-cover border-2 border-gray-300"
              />
              <input
                type="file"
                className="absolute inset-0 opacity-0 cursor-pointer"
                onChange={handlePhotoChange}
              />
              <div className="absolute bottom-0 right-0 bg-gray-200 p-1 rounded-full">
                ✏️
              </div>
            </label>
          </div>
          <form onSubmit={handleSubmit} className="space-y-4">
            <input
              type="text"
              placeholder="Group Name"
              className="border p-2 rounded w-full"
              value={groupName}
              onChange={(e) => setGroupName(e.target.value)}
            />
            <textarea
              placeholder="Group Description"
              className="border p-2 rounded w-full"
              value={groupDescription}
              onChange={(e) => setGroupDescription(e.target.value)}
            />
            <div className="flex gap-4">
              <select
                className="border p-2 rounded w-full"
                value={groupType}
                onChange={(e) => setGroupType(e.target.value)}
              >
                <option value="Nhóm học thuật">Nhóm học thuật</option>
                <option value="Nhóm bên ngoài">Nhóm bên ngoài</option>
              </select>
            </div>
            <div className="flex justify-end">
              <button
                type="submit"
                className="bg-blue-500 text-white py-2 px-4 rounded"
              >
                Submit
              </button>
            </div>
          </form>
        </motion.div>
      </motion.div>
    </AnimatePresence>
  );
};

export default CreateGroupForm;
