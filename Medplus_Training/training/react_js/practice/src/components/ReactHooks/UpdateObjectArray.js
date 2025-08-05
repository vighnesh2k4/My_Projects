import { useState } from "react";
import { FaEdit, FaTrash } from 'react-icons/fa';

function UpdateObjectArray(){
    const [cars, setCars] = useState([]);
    const [tempIndex, setTempIndex]=useState(-1);
    const [tempDisp, setTempDisp]=useState(false);
    const handleAddObject=()=>{
        const newCar={
            year: document.getElementById("carYear").value,
            make: document.getElementById("carMake").value,
            model: document.getElementById("carModel").value
        }
        setCars(c=>[...c, newCar]);
        document.getElementById("carYear").value='';
        document.getElementById("carMake").value='';
        document.getElementById("carModel").value='';
    }
    const handleEditCar=(index)=>{
        document.getElementById("carYear").value=cars[index].year;
        document.getElementById("carMake").value=cars[index].make;
        document.getElementById("carModel").value=cars[index].model;
        setTempIndex(index);
        setTempDisp(true);
    }
    const handleUpdateCar=()=>{
        cars[tempIndex]={year: document.getElementById("carYear").value,
            make:document.getElementById("carMake").value,
            model:document.getElementById("carModel").value
        };
        setTempIndex(-1);
        setTempDisp(false);

        document.getElementById("carYear").value='';
        document.getElementById("carMake").value='';
        document.getElementById("carModel").value='';
    }
    const handleRemoveCar=(index)=>{
        setCars(cars.filter((_,i)=>i!==index));
        setTempDisp(false);
        document.getElementById("carYear").value='';
        document.getElementById("carMake").value='';
        document.getElementById("carModel").value='';
    }
    return(
        <div>
            <p>Your cars: </p>
            <ol>{cars.map((car, index)=> (<li key={index}>{"\t"+car.year}, {"\t"+car.make}, {"\t"+car.model} 
                <button onClick={()=>handleEditCar(index)}><FaEdit color="lightgreen" /></button>
                <button onClick={()=>handleRemoveCar(index)}><FaTrash color="red" /></button></li>))}</ol>
            <input type="number" placeholder="Enter car year" id="carYear" />
            <input type="text" placeholder="Enter car make" id="carMake" />
            <input type="text" placeholder="Enter car model" id="carModel" />
            {tempDisp? <button onClick={handleUpdateCar} >Update Car</button> : <button onClick={handleAddObject} >Add Car</button>}
        </div>
    );
}

export default UpdateObjectArray;