import { useParams } from "react-router-dom";
import PostCaNhan from "./PostCaNhan.jsx";
import ProfileStudent from "./ProfileInfoStudent.jsx";

const ProfilePage = () => {
    const { userId } = useParams();

    return (
        <ProfileStudent userId={userId} />
    );
};

export default ProfilePage;
