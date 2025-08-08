import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useUser } from './UserContext.jsx';

function LoginPage() {
  const [formData, setFormData] = useState({
    username: '',
    password: ''
  });
  const [error, setError] = useState('');
  const { login } = useUser();
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData(prev => ({
      ...prev,
      [e.target.id]: e.target.value
    }));
  };

  const handleLoginSubmit = async (e) => {
    e.preventDefault();
    setError('');

    const { success, role, message } = await login(formData.username, formData.password);
    
    if (success) {
      if (role === 'ADMIN') {
        navigate('/admin');
      } else if (role === 'CUSTOMER') {
        navigate('/customer');
      }
    } else {
      setError(message);
    }
  };

  return (
    <div>
      <h1>Welcome to Mart</h1>
      <div className="login-div">
        <h2>Login</h2>
        <form onSubmit={handleLoginSubmit}>
          <input type="text" className="loginIp" id="username" placeholder="Username" onChange={handleChange} value={formData.username} required /><br /><br />
          <input type="password" className="loginIp" id="password" placeholder="Password" onChange={handleChange} value={formData.password} required /><br /><br />
          {error && <p style={{ color: 'red' }}>{error}</p>}
          <input type="submit" value="Submit" className="loginIp" />
        </form>
        <h5>Don't have an account? <Link to="/register">Register</Link></h5>
      </div>
    </div>
  );
}

export default LoginPage;