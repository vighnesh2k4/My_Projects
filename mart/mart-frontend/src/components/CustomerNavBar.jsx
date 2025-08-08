import { Link } from "react-router-dom";
import { useUser } from './UserContext.jsx';

function CustomerNavBar(){
    const { logout } = useUser();
    return(
        <div>
            <h4>
                <Link to="/customer">Products</Link> • 
                <Link to="/customercart">My Cart</Link> • 
                <Link to="/customerdetails">My Account & Orders</Link> • 
                <a href="#" onClick={logout}>Logout</a>
            </h4>
        </div>
    );
}

export default CustomerNavBar;