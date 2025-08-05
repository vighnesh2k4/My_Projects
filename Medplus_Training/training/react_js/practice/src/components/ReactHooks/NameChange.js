import { useState } from "react";
function NameChange(){
    const [name,setName]=useState("");
    const nameChange=(e)=>
    {
        setName(e.target.value);
    }
    const [quantity,setQuantity]=useState(0);
    const quantityChange=(e)=>
    {
        setQuantity(e.target.value);
    }
    const [comment,setComment]=useState("");
    const commentChange=(e)=>
    {
        setComment(e.target.value);
    }
    const [payment,setPayment]=useState("");
    const paymentChange=(e)=>
    {
        setPayment(e.target.value);
    }
    const [shipping,setShipping]=useState("");
    const shippingChange=(e)=>
    {
        setShipping(e.target.value);
    }
    return(
        <div>
        <input type="text" value={name} onChange={nameChange}/>
        <p>Good Morning {name}!</p>
        <input type="number" value={quantity} onChange={quantityChange}/>
        <p>Quantity: {quantity}</p>
        <input type="text" value={comment} onChange={commentChange}/>
        <p>Comment: {comment}</p>
        <select value={payment} onChange={paymentChange}>
            <option value="">Select an option</option>
            <option value="Visa">Visa</option>
            <option value="Mastercard">Mastercard</option>
            <option value="Giftcard">Giftcard</option>
        </select>
        <p>Payment: {payment}</p>
        <label>
            <input type="radio" value="PickUp" checked={shipping==="PickUp"} onChange={shippingChange}/> Pick Up
        </label>
        <label>
            <input type="radio" value="Delivery" checked={shipping==="Delivery"} onChange={shippingChange}/> Delivery
        </label>
        <p>Shipping: {shipping}</p>
        </div>
    );
}

export default NameChange;