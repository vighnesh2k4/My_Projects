import { useEffect, useState } from "react";
import axios from "axios";
import AdminNavBar from "./AdminNavBar.jsx";
import { FaBan, FaEdit, FaSave, FaSignOutAlt, FaTrash } from "react-icons/fa";
import { useUser } from './UserContext.jsx';

function AdminDetails(){
    const { user, updateUserInContext, logout } = useUser();
    const [userData, setUserData]=useState({});
    const [isEditing, setIsEditing]=useState(false);
    const [error, setError] = useState('');

    useEffect(() => {
        if (user) {
            setUserData(user);
        }
    }, [user]);

    const handleSave = async () => {
        setIsEditing(false);
        setError('');
        try {
            const response = await axios.put(`http://localhost:8080/api/user/updateUser/${user.user_id}`, userData);
            if (response.data.status === 'SUCCESS') {
                updateUserInContext(response.data.dataObject);
            } else {
                setError('Failed to save changes.');
            }
        } catch (err) {
            console.error('Error saving data:', err);
            setError('Failed to save changes.');
        }
    };
    
    const handleChange = (e) => {
        setUserData(prev => ({
            ...prev,
            [e.target.id]: e.target.value
        }));
    };
    
    const handleDelete = async () => {
        setError('');
        if (window.confirm("Are you sure you want to delete your account? This action cannot be undone.")) {
            try {
                await axios.delete(`http://localhost:8080/api/user/deleteUser/${user.user_id}`);
                logout(); 
            } catch (err) {
                console.error('Error deleting user:', err);
                setError('Failed to delete account.');
            }
        }
    };

    return(
        <div>
            <h1>Admin Page</h1>
            < AdminNavBar />
            <h2>My Profile</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {isEditing? <div>
            <label>User ID : {userData.user_id}</label><br/><br/>
            <label>Username: <br/><input type="text" value={userData.username} onChange={handleChange} id="username" className="usrIp"/></label><br/><br/>
            <label>Email: <br/><input type="email" value={userData.email} onChange={handleChange} id="email" className="usrIp"/></label><br/><br/>
            <label>Password: <br/><input type="password" value={userData.password} onChange={handleChange} id="password" className="usrIp"/></label><br/><br/>
            <label>Role: {userData.role}</label><br/><br/>
            <span><button onClick={()=>{setIsEditing(false);}}><FaBan/></button><button onClick={handleSave}><FaSave/></button></span>
            </div> : <div>
            <label>User Id: {userData.user_id}</label><br/><br/>
            <label>Username: {userData.username}</label><br/><br/>
            <label>Email: {userData.email}</label><br/><br/>
            <label>Password: **********</label><br/><br/>
            <label>Role: {userData.role}</label><br/><br/>
            <span><button onClick={()=>{setIsEditing(true);}}><FaEdit/></button><button onClick={handleDelete}><FaTrash/></button></span>
            </div>}
            <label>Click here to logout: <button onClick={logout}><FaSignOutAlt/></button></label>
        </div>
    );
}

export default AdminDetails;