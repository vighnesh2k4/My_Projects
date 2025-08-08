import { Link } from "react-router-dom";
import { useUser } from './UserContext.jsx';

function AdminNavBar(){
    const { logout } = useUser();
    return(
        <div>
            <h4>
                <Link to="/admin">Manage Users</Link> • 
                <Link to="/adminproducts">Manage Products</Link> • 
                <Link to="/admindetails">My Account</Link> • 
                <a href="#" onClick={logout}>Logout</a>
            </h4>
        </div>
    );
}

export default AdminNavBar;