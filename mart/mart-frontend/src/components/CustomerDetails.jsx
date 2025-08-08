import { useEffect, useState } from "react";
import axios from "axios";
import CustomerNavBar from "./CustomerNavBar.jsx";
import { FaBan, FaEdit, FaSave, FaSignOutAlt, FaTrash } from "react-icons/fa";
import { Link } from "react-router-dom";
import { useUser } from './UserContext.jsx';

function CustomerDetails(){
    const { user, updateUserInContext, logout } = useUser();
    const [allOrders,setAllOrders]=useState([]);
    const [userData, setUserData]=useState({});
    const [isEditing, setIsEditing]=useState(false);
    const [error, setError] = useState('');

    const getUserDetails=async()=>{
        if (!user) return;
        try{
            const userResponse=await axios.get(`http://localhost:8080/api/user/getUser/${user.user_id}`);
            if(userResponse.data.status==='SUCCESS'){
                setUserData(userResponse.data.dataObject);
            }else{
                setError('Failed to fetch user details.');
            }
        }catch (err) {
            console.error(err);
            setError('Failed to fetch user details.');
        }
    }

    const getOrders=async()=>{
        if (!user) return;
        try{
            const ordersResponse=await axios.get(`http://localhost:8080/api/order/${user.user_id}/getAllOrders`);
            if(ordersResponse.data.status==='SUCCESS'){
                setAllOrders(ordersResponse.data.dataObject);
            }else{
                setError('Failed to fetch orders.');
            }
        }catch (err) {
            console.error(err);
            setError('Failed to fetch orders.');
        }
    }

    useEffect(()=>{
        if (user) {
            getUserDetails();
            getOrders();
        }
    },[user]);

    const handleSave = async () => {
        setIsEditing(false);
        setError('');
        try {
            const response = await axios.put(`http://localhost:8080/api/user/updateUser/${user.user_id}`, userData);
            if (response.data.status === 'SUCCESS') {
                updateUserInContext(response.data.dataObject);
                alert('Profile updated successfully!');
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
    
    const handleOrderAction = async (order) => {
        const newStatus = order.status === 'COMPLETED' ? 'CANCELLED' : 'COMPLETED';
        const updatedOrder = { ...order, status: newStatus };
        
        try {
            await axios.put(`http://localhost:8080/api/order/${user.user_id}/updateOrder/${order.order_id}`, updatedOrder);
            getOrders(); 
        } catch (error) {
            console.error('Error updating order status:', error);
            setError('Failed to update order status.');
        }
    };

    return(
        <div>
            <h1>Customer Page</h1>
            < CustomerNavBar />
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
            <div>
                <h2>My Orders</h2>
                <table>
                    <thead>
                    <tr>
                        <th>S. No.</th>
                        <th>Order Items</th>
                        <th>Total Amount</th>
                        <th>Status</th>
                        <th>Order Date</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {allOrders.map((order,index)=>(
                    <tr key={index}>
                        <td>{index+1}</td>
                        <td><Link to={`/orderitems/${order.order_id}`} >Order items</Link></td>
                        <td>{order.total_amount}</td>
                        <td>{order.status}</td>
                        <td>{new Date(order.order_date).toLocaleDateString()}</td>
                        <td>
                            {order.status === 'COMPLETED' ? (
                                <a href="#" onClick={() => handleOrderAction(order)}>Cancel Order</a>
                            ) : (
                                <a href="#" onClick={() => handleOrderAction(order)}>Undo Cancel Order</a>
                            )}
                        </td>
                    </tr>
                    ))}
                    </tbody>
                </table>
            </div>
            <label>Click here to logout: <button onClick={logout}><FaSignOutAlt/></button></label>
        </div>
    );
}

export default CustomerDetails;