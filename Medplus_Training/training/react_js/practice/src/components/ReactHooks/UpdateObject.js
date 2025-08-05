import { useState } from "react";
function UpdateObject(){
    const [car, setCar] = useState({year:2024,
            make:"Ford", model:"Mustang"});
    const handleYearChange=(e)=>{
        setCar(car=>({...car, year: e.target.value}));
    }
    const handleMakeChange=(e)=>{
        setCar(car=>({...car, make: e.target.value}));
    }
    const handleModelChange=(e)=>{
        setCar(car=>({...car, model: e.target.value}));
    }
    return(
        <div>
            <p>Your favourite car: 
                 {"\t"+ car.year}, {"\t"+car.make},{"\t"+car.model}
            </p>
            <input type="number" value={car.year} onChange={handleYearChange} />
            <input type="text" value={car.make} onChange={handleMakeChange} />
            <input type="text" value={car.model} onChange={handleModelChange} />
        </div>
    );
}
export default UpdateObject;