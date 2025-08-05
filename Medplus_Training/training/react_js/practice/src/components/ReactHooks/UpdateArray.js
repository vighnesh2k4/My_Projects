import { useState } from "react";
import { FaTrash } from 'react-icons/fa';
function UpdateArray(){
    const [fruits, setFruits]=useState([]);
    const handleFruitsChange=()=>{
        const newFruit={
            name:document.getElementById("fruitname").value,
            calories:document.getElementById("calories").value
        }
        setFruits(f => [...f, newFruit]);
        document.getElementById('fruitname').value = '';
        document.getElementById('calories').value = '';
    }
    const handleClearButton=()=>{
        setFruits([]);
        document.getElementById('fruitname').value = '';
        document.getElementById('calories').value = '';
    }
    const handleRemoveFruit=(index)=>{
        setFruits(fruits.filter((_,i)=>i!==index));
    }
    return(
        <>
        <label>Enter Fruit: <input type="text"  placeholder="Enter fruit name" id="fruitname"/></label>
        <label>Enter Calories: <input type="number"  placeholder="Enter fruit calories" id="calories"/></label>
        <div className="fruitBtns" style={{display: 'flex', flexDirection: 'row', gap: '10px', padding:'10px'}}>
        <br/> <button onClick={handleFruitsChange}>Add Fruit</button>
        <br/> <button onClick={handleClearButton}>Clear Fruits</button>
        </div>
        <b>List of Fruits and their calories:</b>
        <ul>{fruits.map((fruit, index)=> (<li key={index}>{fruit.name}: {fruit.calories} <button onClick={()=>handleRemoveFruit(index)}><FaTrash color="red" /></button></li>))}</ul>
        </>
    );
}
export default UpdateArray;