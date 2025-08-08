import './App.css'
import { Routes, Route } from 'react-router-dom';
import { UserProvider } from './components/UserContext.jsx';
import LoginPage from './components/LoginPage';
import RegisterPage from './components/RegisterPage';
import AdminHome from './components/AdminHome';
import AdminProducts from './components/AdminProducts';
import AdminDetails from './components/AdminDetails';
import CustomerHome from './components/CustomerHome';
import CustomerCart from './components/CustomerCart';
import CustomerDetails from './components/CustomerDetails';
import CustomerOrderItems from './components/CustomerOrderItems';
import ProtectedRoute from './components/ProtectedRoute'; 

function App() {

  return (
      <UserProvider>
      <Routes>
        <Route path="/" element={<h1>Welcome to Mart</h1>} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />

        {/* Protected routes for Admin */}
        <Route element={<ProtectedRoute requiredRole="ADMIN" />}>
          <Route path="/admin" element={<AdminHome />} />
          <Route path="/adminproducts" element={<AdminProducts />} />
          <Route path="/admindetails" element={<AdminDetails />} />
        </Route>

        {/* Protected routes for Customer */}
        <Route element={<ProtectedRoute requiredRole="CUSTOMER" />}>
          <Route path="/customer" element={<CustomerHome />} />
          <Route path="/customercart" element={<CustomerCart />} />
          <Route path="/customerdetails" element={<CustomerDetails />} />
          <Route path="/orderitems/:orderid" element={<CustomerOrderItems />} />
        </Route>

        <Route path="*" element={<h2>Page Not Found</h2>} />
      </Routes>
    </UserProvider>

  );
}

export default App
