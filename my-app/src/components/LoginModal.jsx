import { motion, AnimatePresence } from "framer-motion";
import { useState } from "react";
import { X } from "lucide-react";

const LoginModal = ({ onClose }) => {
  const [isSignUp, setIsSignUp] = useState(false);
  const [key, setKey] = useState(0);
  const handleToggle = () => {
    setIsSignUp((prev) => !prev);
    setKey((prev) => prev + 1);
  };

  return (
    <AnimatePresence>
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0, scale: 1.2 }} // Hiệu ứng fade-out + phóng to nhẹ
        transition={{ duration: 0.5, ease: "easeOut" }} // Kéo dài animation
        className="fixed inset-0 flex items-center justify-center bg-black/30 backdrop-blur-md transition-opacity duration-300 z-20"
      >
        <motion.div
          key={key}
          initial={{ scale: 0.8, opacity: 0 }}
          animate={{ scale: 1, opacity: 1 }}
          exit={{ scale: 0.8, opacity: 0 }}
          transition={{ duration: 0.3, ease: "easeOut" }}
          className="relative bg-white bg-opacity-90 rounded-[30px] shadow-lg w-[800px] flex overflow-hidden"
        >
          {/* Form bên trái */}
          <div className="w-1/2 p-8 backdrop-blur-lg">
            <AnimatePresence>
              <motion.button
                initial={{ opacity: 0, scale: 0.8 }}
                animate={{ opacity: 1, scale: 1 }}
                exit={{ opacity: 0, scale: 0.8 }}
                transition={{ duration: 0.2 }}
                onClick={onClose}
                className="absolute top-3 right-3 text-gray-600 hover:text-gray-900"
              >
                <X className="w-7 h-7 cursor-pointer" />
              </motion.button>
            </AnimatePresence>

            {isSignUp ? (
              <>
                <h2 className="text-2xl font-semibold mt-9 text-gray-900">
                  Sign up
                </h2>
                <p className="text-gray-500 mb-6">
                  Create an account to get started.
                </p>

                <input
                  type="text"
                  placeholder="Full Name"
                  className="w-full p-2 mb-4 border rounded-md  text-gray-600"
                />
                <input
                  type="email"
                  placeholder="Email"
                  className="w-full p-2 mb-4 border rounded-md  text-gray-600"
                />
                <input
                  type="password"
                  placeholder="Password"
                  className="w-full p-2 mb-4 border rounded-md  text-gray-600"
                />

                <select className="w-full p-2 mb-4 border rounded-md text-gray-600">
                  <option value="student">Student</option>
                  <option value="lecturer">Lecturer</option>
                </select>

                <select className="w-full p-2 mb-4 border rounded-md  text-gray-600">
                  <option value="male">Male</option>
                  <option value="female">Female</option>
                </select>

                <button className="w-full bg-blue-500 text-white p-2 rounded-md font-semibold cursor-pointer mb-4">
                  Sign Up
                </button>

                <p className="text-sm text-gray-600 text-center">
                  Already have an account?{" "}
                  <button
                    onClick={handleToggle}
                    className="text-blue-500 cursor-pointer"
                  >
                    Sign in
                  </button>
                </p>
              </>
            ) : (
              <>
                <h2 className="text-2xl font-semibold mt-20 text-gray-900">
                  Sign in
                </h2>
                <p className="text-gray-500 mb-6">
                  Enter your email and password to continue.
                </p>

                <input
                  type="email"
                  placeholder="Email"
                  className="w-full p-2 mb-4 border rounded-md text-gray-600"
                />
                <input
                  type="password"
                  placeholder="Password"
                  className="w-full p-2 mb-4 border rounded-md text-gray-600"
                />

                <button className="w-full bg-blue-500 text-white p-2 rounded-md font-semibold cursor-pointer mb-4">
                  Sign in
                </button>

                <p className="text-sm text-gray-600 text-center">
                  Don't have an account?{" "}
                  <button
                    onClick={handleToggle}
                    className="text-blue-500 cursor-pointer"
                  >
                    Sign up
                  </button>
                </p>
              </>
            )}
          </div>

          {/* Hình ảnh bên phải */}
          <div className="w-1/2 relative">
            <img
              src="/imagecopy.png" // Đường dẫn trực tiếp đến ảnh
              alt="Background"
              className="w-full h-full object-cover"
            />
          </div>
        </motion.div>
      </motion.div>
    </AnimatePresence>
  );
};

export default LoginModal;
