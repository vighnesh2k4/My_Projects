import { useState, useEffect, useRef } from "react";
import axios from "axios";
import {FaEdit, FaTrash} from 'react-icons/fa';
import NoProductImage from "../assets/No-Product-Image.png";
import AdminNavBar from "./AdminNavBar.jsx";

function AdminProducts(){
    const [allProducts, setAllProducts]=useState([]);
    const [isEditing, setIsEditing]=useState(false);
    const [currentProductId, setCurrentProductId]=useState(null);
    const [productForm, setProductForm] = useState({
        name: '',
        description: '',
        price: ''
    });
    const inputRef=useRef(null);
    
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
    
    const handleFormChange = (e) => {
        setProductForm(prev => ({
            ...prev,
            [e.target.id]: e.target.value
        }));
    };

    const handleAdd = async () => {
        try {
            await axios.post('http://localhost:8080/api/product/addProduct', productForm);
            setProductForm({ name: '', description: '', price: '' });
            getProducts();
        } catch (error) {
            console.error('Error adding product:', error);
        }
    };
    
    const handleUpdate = async () => {
        try {
            await axios.put(`http://localhost:8080/api/product/updateProduct/${currentProductId}`, productForm);
            setIsEditing(false);
            setCurrentProductId(null);
            setProductForm({ name: '', description: '', price: '' });
            getProducts();
        } catch (error) {
            console.error('Error updating product:', error);
        }
    };
    
    const handleEdit = (product) => {
        setIsEditing(true);
        setCurrentProductId(product.product_id);
        setProductForm({
            name: product.name,
            description: product.description,
            price: product.price
        });
        if (inputRef.current) {
            inputRef.current.focus();
        }
    };
    
    const handleDelete = async (productId) => {
        try {
            await axios.delete(`http://localhost:8080/api/product/deleteProduct/${productId}`);
            getProducts(); 
        } catch (error) {
            console.error('Error deleting product:', error);
        }
    };
    
    function ProductItem({ product }) {
      return (
        <div className="prod-card">
        <img className="prod-card-image" src={NoProductImage} alt="Product Image"></img>
        <h2 className="prod-card-title">{product.product_id} : {product.name}</h2>
        <pre className="prod-card-text">Description: {product.description}</pre>
        <h3 className="prod-card-text">Price: ₹{product.price}</h3>
        <h4 className="prod-card-text"> Availability: {product.status}</h4>
        <button onClick={()=>handleEdit(product)}><FaEdit/></button><button onClick={()=>handleDelete(product.product_id)}><FaTrash/></button>
        </div>
      );
    }
    
    return(
        <div>
            <h1>Admin Page</h1>
            < AdminNavBar />
            <div>
            <h2>Save Product</h2>
            <div>
                {!isEditing && <input type="text" className="productIp" id="name" placeholder="Product Name" value={productForm.name} onChange={handleFormChange} />}<br /><br />
                <input type="text" className="productIp" id="description" placeholder="Description" value={productForm.description} onChange={handleFormChange} /><br /><br />
                <input type="number" ref={inputRef} className="productIp" id="price" placeholder="Price" value={productForm.price} onChange={handleFormChange} /><br /><br />
                {isEditing? <button onClick={handleUpdate} >Update Product</button> : <button onClick={handleAdd} >Add Product</button>}
            </div>
            <h2>Manage Products</h2>
            {allProducts.map((product)=>(
                <ProductItem key={product.product_id} product={product} />
            ))}
            </div>
        </div>
    );
}

export default AdminProducts;