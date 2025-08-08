import CustomerNavBar from './CustomerNavBar.jsx';
import { useState, useEffect } from 'react';
import axios from 'axios';
import NoProductImage from "../assets/No-Product-Image.png";
import { FaBox, FaTrash } from 'react-icons/fa';
import { useUser } from './UserContext.jsx';

function CustomerCart(){
    const { user } = useUser();
    const [allCartItems, setAllCartItems]=useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    const getCartItems=async()=>{
        setLoading(true);
        setError('');
        try{
            const allCartItemsResponse=await axios.get(`http://localhost:8080/api/cart/${user.user_id}/getAllCartItems`);
            if(allCartItemsResponse.data.status==='SUCCESS'){
                setAllCartItems(allCartItemsResponse.data.dataObject);
            }else{
                setError('Failed to fetch cart items.');
            }
        }catch (err) {
            console.error(err);
            setError('Failed to fetch cart items. Please check the server.');
        }finally {
            setLoading(false);
        }
    }

    useEffect(()=>{
        if (user) {
            getCartItems();
        }
    }, [user]);

    const handleUpdateCart = async (cart) => {
        try {
            await axios.put(`http://localhost:8080/api/cart/${cart.user_id}/updateCartItem/${cart.cart_id}`, cart);
            getCartItems(); 
        } catch (error) {
            console.error('Error updating cart:', error);
            setError('Failed to update cart item.');
        }
    };
    
    const handleDeleteCart = async (cart) => {
        try {
            await axios.delete(`http://localhost:8080/api/cart/${cart.user_id}/deleteCartItem/${cart.cart_id}`);
            getCartItems();
        } catch (error) {
            console.error('Error deleting cart:', error);
            setError('Failed to delete cart item.');
        }
    };
    
    const handleClearCart = async () => {
        try {
            await axios.delete(`http://localhost:8080/api/cart/${user.user_id}/clearCartItems`);
            getCartItems(); 
        } catch (error) {
            console.error('Error clearing cart:', error);
            setError('Failed to clear cart.');
        }
    };
    
    const handlePlaceOrder = async () => {
        try {
            await axios.post(`http://localhost:8080/api/order/${user.user_id}/placeOrder`);
            getCartItems(); 
        } catch (error) {
            console.error('Error placing order:', error);
            setError('Failed to place order.');
        }
    };

    function CartItem({ cart }) {
        const [quantity, setQuantity] = useState(cart.quantity);
        const [product, setProduct] = useState(null);

        useEffect(() => {
            const fetchProduct = async () => {
                try {
                    const response = await axios.get(`http://localhost:8080/api/product/getProduct/${cart.product_id}`);
                    if (response.data.status === 'SUCCESS') {
                        setProduct(response.data.dataObject);
                    } else {
                        console.log(response);
                    }
                } catch (error) {
                    console.error(error);
                }
            };
            fetchProduct();
        }, [cart.product_id]);

        const decreaseQuantity = () => {
            if (quantity > 1) {
                setQuantity(q => q - 1);
            }
        };

        const increaseQuantity = () => {
            setQuantity(q => q + 1);
        };

        const updateCart = () => {
            const updatedCart = { ...cart, quantity: quantity };
            handleUpdateCart(updatedCart);
        };

        if (!product) {
            return <div>Loading product info...</div>;
        }

        return (
            <div className="cart-card">
                <img className="prod-card-image" src={NoProductImage} alt="Product" />
                <h2 className="prod-card-title">{product.name}</h2>
                <pre className="prod-card-text">Description: {product.description}</pre>
                <h3 className="prod-card-text">Price: ₹{product.price}</h3>
                <div>
                    <button onClick={decreaseQuantity}>-</button>
                    <span>{quantity}</span>
                    <button onClick={increaseQuantity}>+</button>
                </div>
                <div>
                    <button onClick={updateCart}>Update Cart <FaBox /></button>
                    <button onClick={()=>handleDeleteCart(cart)}><FaTrash/></button>
                </div>
            </div>
        );
    }
    
    if (loading) return <div>Loading cart items...</div>;
    if (error) return <div style={{ color: 'red' }}>Error: {error}</div>;

    return(
        <div>
            <h1>Customer Page</h1>
            <CustomerNavBar />
            <div>
            <h2> Cart Items</h2>
            {allCartItems.length === 0 ? (
                <p>Your cart is empty.</p>
            ) : (
                allCartItems.map((cart)=>(
                    <CartItem key={cart.cart_id} cart={cart} />
                ))
            )}
            </div>
            {allCartItems.length > 0 && (
                <div>
                    <button onClick={handleClearCart}>Clear Cart</button>
                    <button onClick={handlePlaceOrder}>Place Order</button>
                </div>
            )}
        </div>
    );
}

export default CustomerCart;