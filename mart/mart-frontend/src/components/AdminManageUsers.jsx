import axios from "axios";
import { useEffect, useState } from "react";
import { FaUserMinus, FaUserPlus } from "react-icons/fa";
import AdminNavBar from "./AdminNavBar.jsx";

function AdminManageUsers(){
    const [allUsers, setAllUsers]=useState([]);
    const [loading, setLoading]=useState(false);
    const [error, setError]=useState('');
    
    const getUsers=async()=>{
        setLoading(true);
        setError('');
        try{
            const allUsersResponse=await axios.get("http://localhost:8080/api/user/getAllUsers");
            if(allUsersResponse.data.status==='SUCCESS'){
                setAllUsers(allUsersResponse.data.dataObject);
            }else{
                setError(allUsersResponse.data.message || 'Failed to fetch users.');
            }
        }catch (err) {
            setError('Failed to fetch users. Please check the server.');
        }finally{
            setLoading(false);
        }
    }
    
    useEffect(()=>{
        getUsers();
    },[]);

    const handleUpdateRole = async (user) => {
        const newRole = user.role === 'ADMIN' ? 'CUSTOMER' : 'ADMIN';
        const updatedUser = { ...user, role: newRole };
        
        try {
            await axios.put(`http://localhost:8080/api/user/updateUserRole/${user.user_id}`, updatedUser);
            getUsers();
        } catch (error) {
            console.error('Error updating role:', error);
            setError('Failed to update user role.');
        }
    };

    if(loading) return <div>Loading users...</div>;
    if(error) return <div>Error: {error}</div>;

    return(
        <div>
            <h1>Admin Page</h1>
            <AdminNavBar />
            <h2>Manage Users</h2>
            <table>
                <thead>
                    <tr>
                        <th>USER ID</th>
                        <th>USERNAME</th>
                        <th>EMAIL</th>
                        <th>PASSWORD</th>
                        <th>ROLE</th>
                        <th>STATUS</th>
                        <th>UPDATE ROLE</th>
                    </tr>
                </thead>
                <tbody>
                    {allUsers.map((user)=>(
                        <tr key={user.user_id}>
                            <td>{user.user_id}</td>
                            <td>{user.username}</td>
                            <td>{user.email}</td>
                            <td>{user.password}</td>
                            <td>{user.role}</td>
                            <td>{user.status}</td>
                            <td>{user.role==='ADMIN'? 
                                <FaUserMinus onClick={()=>handleUpdateRole(user)} style={{cursor:"pointer"}}/> : 
                                <FaUserPlus onClick={()=>handleUpdateRole(user)} style={{cursor:"pointer"}}/>}
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}
export default AdminManageUsers;