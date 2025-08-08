import { Navigate, Outlet } from 'react-router-dom';
import { useUser } from './UserContext.jsx';

const ProtectedRoute = ({ requiredRole }) => {
  const { user, loading } = useUser();

  if (loading) {
    return <div>Loading...</div>; 
  }

  if (!user) {
    return <Navigate to="/login" replace />;
  }

  if (requiredRole && user.role !== requiredRole) {
    return <div>Access Denied</div>; 
  }

  return <Outlet />;
};

export default ProtectedRoute;