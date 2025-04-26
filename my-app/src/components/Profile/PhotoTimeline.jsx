import React from "react";

const PhotoTimeline = ({ blogs }) => {
  return (
    <div className="w-1/3 bg-white shadow-md rounded-xl p-4 h-fit">
      <div className="flex justify-between items-center mb-2">
        <h3 className="text-lg font-semibold text-cyan-950">Photos</h3>
        <button className="text-blue-500 text-sm">Add Photo</button>
      </div>
      <div className="grid grid-cols-2 gap-2 mt-6 mb-6">
        {blogs?.map(
          (blog, i) =>
            blog.images && (
              <img
                key={i}
                src={blog.images}
                alt={`Photo ${i}`}
                className="w-full h-20 object-cover rounded-lg"
              />
            )
        )}
      </div>
    </div>
  );
};

export default PhotoTimeline;
