import { useState } from "react";
import { ChevronDown, ChevronRight, Users } from "lucide-react";
import NotificationBar from "./NotificationBar";

// Sidebar Item Component
const SidebarItem = ({ icon, label, badge }) => (
  <li className="flex items-center justify-between p-2 hover:bg-gray-100 rounded-md cursor-pointer">
    <div className="flex items-center gap-2">
      {icon}
      <span>{label}</span>
    </div>
    {badge && (
      <span className="bg-red-500 text-white text-xs px-2 py-1 rounded-full">
        {badge}
      </span>
    )}
  </li>
);

// Sidebar Project Component
const SidebarProject = ({ projectName, children }) => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <div className="mb-2">
      <div
        onClick={() => setIsOpen(!isOpen)}
        className="flex items-center justify-between p-2 hover:bg-gray-100 rounded-md cursor-pointer"
      >
        <div className="flex items-center gap-2">
          <ChevronRight className={`transform ${isOpen ? "rotate-90" : ""}`} />
          <span>{projectName}</span>
        </div>
      </div>
      {isOpen && <ul className="ml-6 space-y-1">{children}</ul>}
    </div>
  );
};

// Sidebar Component
const Sidebar = () => (
  <aside className="w-1/4 bg-white p-4 rounded-xl shadow-md">
    <input
      type="text"
      placeholder="Search"
      className="w-full p-2 mb-4 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-300"
    />
    <ul className="space-y-2">
      <SidebarItem
        label="Add Project"
        icon={<span className="text-xl">+</span>}
      />

      {/* Project Group */}
      <SidebarProject projectName="Secrat Project">
        <SidebarItem label="Task 1" />
        <SidebarItem label="Task 2" />
        <SidebarItem label="Task 3" />
        <SidebarItem label="Task 4" />
      </SidebarProject>

      <SidebarProject projectName="Bnie Mobile App">
        <SidebarItem label="Task A" />
        <SidebarItem label="Task B" />
      </SidebarProject>

      <SidebarItem
        label="All Tasks"
        icon={<span className="text-green-500">●</span>}
      />
      <SidebarItem
        label="People"
        icon={<span className="text-yellow-500">●</span>}
      />
      <SidebarItem
        label="Files"
        icon={<span className="text-red-500">●</span>}
        badge="10"
      />
      <SidebarItem
        label="Statistics"
        icon={<span className="text-blue-500">●</span>}
      />
    </ul>
  </aside>
);

const LeaderGroupDashBoard = () => {
  return (
    <main className="flex justify-center bg-gray-100 min-h-screen pt-20">
      <div className="w-full max-w-6xl bg-white shadow-md rounded-lg p-4">
        <div className="w-full">
          <NotificationBar groupName="Team Awesome" notificationCount={5} />
          <div className="flex gap-4">
            <Sidebar />
            <div className="w-3/4 bg-gray-50 p-6 rounded-lg shadow-inner">
              <h1 className="text-2xl font-semibold">Dashboard Content</h1>
            </div>
          </div>
        </div>
      </div>
    </main>
  );
};

export default LeaderGroupDashBoard;
