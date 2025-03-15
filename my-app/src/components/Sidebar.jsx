import { Home, Users, Bell, Mail, Menu, User, CheckSquare } from "lucide-react";
import { Link, useLocation } from "react-router-dom";
import { useAuth } from "../context/useAuth";

const Sidebar = ({ isOpen, toggleSidebar, userRole }) => {
  const location = useLocation();
  const { isLoggedIn, user, role } = useAuth();
  // 🌟 Role-based routes
  const routes = {
    noLogin: [
      {
        path: "/",
        label: "Home",
        icon: <Home className="w-6 h-6" />,
        requiresAuth: false,
      },
      {
        path: "/profile",
        label: "Profile",
        icon: <User className="w-6 h-6" />,
        requiresAuth: true,
      },
      {
        path: "/group",
        label: "Group",
        icon: <Users className="w-6 h-6" />,
        requiresAuth: true,
      },
      {
        path: "/message",
        label: "Message Group",
        icon: <CheckSquare className="w-6 h-6" />,
        requiresAuth: true,
      },
    ],
  };
  const handleLinkClick = (e, requiresAuth) => {
    if (requiresAuth && !isLoggedIn) {
      e.preventDefault();
      // Implement your login prompt logic here
      alert("Please log in to access this section.");
    }
  };
  const sidebarItems = routes[userRole] || routes.noLogin; // Mặc định là student nếu không có role

  return (
    <div
      className={`fixed left-0 top-0 h-full z-20 ${
        isOpen ? "w-64" : "w-16"
      } bg-white shadow-lg transition-all duration-300 ease-in-out flex flex-col`}
    >
      {/* Button Toggle Menu */}
      <button className="p-4 text-gray-500" onClick={toggleSidebar}>
        {isOpen ? "✖" : <Menu className="w-6 h-6" />}
      </button>

      {/* Sidebar Items */}
      <div className="flex flex-col items-start mt-9 space-y-2 px-2 ">
        {sidebarItems.map((item) => {
          const isActive = location.pathname === item.path;
          const isDisabled = item.requiresAuth && !isLoggedIn;
          return (
            <Link
              key={item.path}
              to={isDisabled ? "#" : item.path}
              onClick={(e) => handleLinkClick(e, item.requiresAuth)}
              className={`flex items-center gap-3 px-4 py-2 rounded-lg w-full ${
                isActive
                  ? "bg-[#e2f2ff] text-blue-400"
                  : isDisabled
                  ? "text-gray-400 cursor-not-allowed"
                  : "text-gray-700"
              }`}
              style={{ pointerEvents: isDisabled ? "none" : "auto" }}
            >
              {item.icon}
              {isOpen && (
                <span className="transition-opacity duration-500">
                  {item.label}
                </span>
              )}
            </Link>
          );
        })}
      </div>
    </div>
  );
};

export default Sidebar;
