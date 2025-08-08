import { useState, useEffect } from "react";
import axios from "axios";
import {FaBox} from 'react-icons/fa';
import NoProductImage from "../assets/No-Product-Image.png";
import CustomerNavBar from "./CustomerNavBar.jsx";
import { useUser } from './UserContext.jsx';

function CustomerProducts(){
    const { user } = useUser();
    const [allProducts, setAllProducts]=useState([]);
    const [message, setMessage] = useState('');

    const getProducts=async()=>{
        try{
            const allProductsResponse=await axios.get("http://localhost:8080/api/product/getAllProducts");
            if(allProductsResponse.data.status==='SUCCESS'){
                setAllProducts(allProductsResponse.data.dataObject);
            }else{
                console.log(allProductsResponse.data.message);
            }
        }catch (error) {
            console.error(error);
        }
    }
    
    useEffect(()=>{
        getProducts();
    },[]);

    const handleAddCart = async (product_id, quantity) => {
        if (quantity <= 0) {
            setMessage('Quantity must be greater than 0.');
            return;
        }
        
        try {
            const cartItem = {
                user_id: user.user_id, 
                product_id: product_id, 
                quantity: quantity
            };
            await axios.post(`http://localhost:8080/api/cart/${user.user_id}/addCartItem`, cartItem);
            setMessage('Item added to cart successfully!');
        } catch (error) {
            console.error('Error adding to cart:', error);
            setMessage('Failed to add item to cart.');
        }
        setTimeout(() => setMessage(''), 3000); 
    };
    
    function ProductItem({ product }) {
        const [quantity, setQuantity]=useState(0);
        
        function decreaseQuantity(){
            if(quantity>0){
                setQuantity(q=>q-1);                
            }
        }
        
        function increaseQuantity(){
            setQuantity(q=>q+1);
        }
        
      return (
        (product.status==='ACTIVE') &&
          (<div className="prod-card">
              <img className="prod-card-image" src={NoProductImage} alt="Product Image"></img>
              <h2 className="prod-card-title">{product.name}</h2>
              <pre className="prod-card-text">Description: {product.description}</pre>
              <h3 className="prod-card-text">Price: ₹{product.price}</h3>
              <div>
                  <button onClick={decreaseQuantity}>-</button>
                  <span>{quantity}</span>
                  <button onClick={increaseQuantity}>+</button>
              </div>
              <button onClick={()=>handleAddCart(product.product_id,quantity)}>Add to Cart <FaBox/></button>
          </div>)
      );
    }
    
    return(
        <div>
            <h1>Customer Page</h1>
            < CustomerNavBar />
            <div>
                <h2>Products</h2>
                {message && <p style={{ color: 'green' }}>{message}</p>}
                {allProducts.map((product)=>(
                    <ProductItem key={product.product_id} product={product} />
                ))}
            </div>
        </div>
    );
}

export default CustomerProducts;