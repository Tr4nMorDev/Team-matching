import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/useAuth';

const SignIn = ({ handleToggle, onClose }) => {
    const navigate = useNavigate();
    const { login } = useAuth();
    const [error, setError] = useState('');
    const [formData, setFormData] = useState({
        username: '',
        password: '',
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

  const handleSubmit = async () => {
    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/signin",
        formData
      );

      const { token, userData } = response.data;

      // Lưu token và toàn bộ userData
      localStorage.setItem("token", token);
      login(token, userData); // truyền toàn bộ object luôn

      console.log("Signed in with full userData:", userData);
      onClose();
    } catch (error) {
      console.error("Error signing in:", error.response?.data || error.message);
    }
  };

                <button
                    type="submit"
                    className="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
                >
                    Sign In
                </button>

                <div className="text-center mt-4">
                    <p className="text-sm text-gray-600">
                        Don't have an account?{' '}
                        <button
                            type="button"
                            onClick={handleToggle}
                            className="text-blue-600 hover:text-blue-700 font-medium"
                        >
                            Sign Up
                        </button>
                    </p>
                </div>
            </form>
        </div>
    );
};

export default SignIn;
