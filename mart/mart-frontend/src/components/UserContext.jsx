import React, { createContext, useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const UserContext = createContext(null);

export const UserProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  
  useEffect(() => {
    const storedUser = localStorage.getItem('martUser');
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
    setLoading(false);
  }, []);

  const login = async (username, password) => {
    try {
      const response = await axios.post('http://localhost:8080/api/user/login', { username, password });
      if (response.data.status === 'SUCCESS') {
        const userData = response.data.dataObject;
        setUser(userData);
        localStorage.setItem('martUser', JSON.stringify(userData));
        return { success: true, role: userData.role };
      } else {
        return { success: false, message: response.data.message || 'Login failed.' };
      }
    } catch (error) {
      console.error('Login failed:', error);
      return { success: false, message: 'Invalid credentials. Please try again.' };
    }
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem('martUser');
    navigate('/login');
  };

  const updateUserInContext = (updatedUserData) => {
    setUser(updatedUserData);
    localStorage.setItem('martUser', JSON.stringify(updatedUserData));
  };

  const value = { user, loading, login, logout, updateUserInContext };

  return <UserContext.Provider value={value}>{children}</UserContext.Provider>;
};


export const useUser = () => {
  const context = useContext(UserContext);
  if (!context) {
    throw new Error('useUser must be used within a UserProvider');
  }
  return context;
};