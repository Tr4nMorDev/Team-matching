import axios from "axios";

const API_URL = "https://jsonplaceholder.typicode.com/users";
const API_PROJECT = "http://localhost:8080";

export const postComment = async ({ postId, comment, commentbyid, token }) => {
  console.log("post id:", postId);
  console.log("userid:", commentbyid);
  try {
    const response = await axios.post(
      `${API_PROJECT}/api/comments`,
      {
        postId,
        comment,
        commentbyid,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error("Error posting comment:", error);
    throw error;
  }
};
export const getUser = async (id) => {
  const response = await fetch(`${API_URL}/${id}`);
  if (!response.ok) throw new Error("Failed to fetch user");
  return response.json();
};
async function getBlogs(page = 0, pageSize = 10) {
  try {
    const response = await axios.get(`${API_PROJECT}/api/blogs`, {
      params: { page, size: pageSize },
    });
    return response.data;
  } catch (error) {
    console.error("Error fetching posts:", error);
    throw error;
  }
}
export default getBlogs;
