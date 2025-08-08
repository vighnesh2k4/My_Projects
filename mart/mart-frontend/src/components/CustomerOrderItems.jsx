import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import CustomerNavBar from './CustomerNavBar';
import { useUser } from './UserContext';

function CustomerOrderItems() {
    const { orderid } = useParams();
    const { user } = useUser();
    const [orderItems, setOrderItems] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        const getOrderItems = async () => {
            if (!user) {
                setError('User not authenticated.');
                setLoading(false);
                return;
            }

            try {
                const response = await axios.get(`http://localhost:8080/api/order/getOrderItems/${orderid}`);
                
                if (response.data.status === 'SUCCESS' && response.data.dataObject) {
                    const fetchedItems = response.data.dataObject;
                    
                    const itemsWithProductDetails = await Promise.all(
                        fetchedItems.map(async (item) => {
                            try {
                                const productResponse = await axios.get(`http://localhost:8080/api/product/getProduct/${item.product_id}`);
                                if (productResponse.data.status === 'SUCCESS' && productResponse.data.dataObject) {
                                    return {
                                        ...item,
                                        product_name: productResponse.data.dataObject.name,
                                    };
                                }
                                return { ...item, product_name: 'Product not found' };
                            } catch (err) {
                                console.error(`Failed to fetch product for ID ${item.product_id}:`, err);
                                return { ...item, product_name: 'Product not found' };
                            }
                        })
                    );
                    setOrderItems(itemsWithProductDetails);
                } else {
                    setError('Failed to fetch order items.');
                }
            } catch (err) {
                console.error(err);
                setError('Failed to fetch order items. Please check the server.');
            } finally {
                setLoading(false);
            }
        };

        if (user && orderid) {
            getOrderItems();
        }

    }, [orderid, user]);

    if (loading) return <div>Loading order details...</div>;
    if (error) return <div style={{ color: 'red' }}>Error: {error}</div>;

    return (
        <div>
            <h1>Customer Page</h1>
            <CustomerNavBar />
            <h2>Order Details for Order #{orderid}</h2>
            <table>
                <thead>
                    <tr>
                        <th>Product Name</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    {orderItems.map(item => (
                        <tr key={item.item_id}>
                            <td>{item.product_name || 'N/A'}</td>
                            <td>{item.quantity}</td>
                            <td>₹{item.price}</td>
                            <td>₹{item.quantity * item.price}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default CustomerOrderItems;