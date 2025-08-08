import React, { useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';

function RegisterPage() {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: ''
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData(prev => ({
      ...prev,
      [e.target.id]: e.target.value
    }));
  };

  const handleRegisterSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    const { username, email, password, confirmPassword } = formData;

    if (password !== confirmPassword) {
      setError("Passwords do not match.");
      return;
    }
    
    if (password.length < 6) {
        setError("Password must be at least 6 characters long.");
        return;
    }

    try {
      const user = { username, email, password };
      const response = await axios.post('http://localhost:8080/api/user/register', user);
      
      if (response.data.status === 'SUCCESS') {
        setSuccess('Registration successful! Redirecting to login...');
        setTimeout(() => navigate('/login'), 2000);
      } else {
        setError(response.data.message || 'Registration failed. Please try again.');
      }
    } catch (error) {
      console.error('Error posting data:', error);
      setError('Registration failed. A user with this username or email might already exist.');
    }
  };

  return (
    <div>
      <h1>Welcome to Mart</h1>
      <div className="register-div">
        <h2>Signup</h2>
        <form onSubmit={handleRegisterSubmit}>
          <input type="text" className="registerIp" id="username" placeholder="Username" onChange={handleChange} value={formData.username} required /><br /><br />
          <input type="email" className="registerIp" id="email" placeholder="Email" onChange={handleChange} value={formData.email} required /><br /><br />
          <input type="password" className="registerIp" id="password" placeholder="Create password" onChange={handleChange} value={formData.password} required /><br /><br />
          <input type="password" className="registerIp" id="confirmPassword" placeholder="Confirm password" onChange={handleChange} value={formData.confirmPassword} required /><br /><br />
          {error && <p style={{ color: 'red' }}>{error}</p>}
          {success && <p style={{ color: 'green' }}>{success}</p>}
          <input type="submit" value="Submit" className="registerIp" />
        </form>
        <h5>Already have an account? <Link to="/login">Login</Link></h5>
      </div>
    </div>
  );
}

export default RegisterPage;