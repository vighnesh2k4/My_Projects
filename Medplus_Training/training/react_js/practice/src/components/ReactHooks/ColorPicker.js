import { useState } from "react";
function ColorPicker(){
    const [color, setColor] = useState("#FFFFFF");
    function handleColorChange(event){
        setColor(event.target.value);
    }
    return(
        <div>
            <h1>Color Picker</h1>
            <div style={{backgroundColor: color, width: '300px', height:'300px', display:'flex', fontSize:'2rem', justifyContent:'center', alignItems:'center', textAlign:'center' }}>
                <p>Selected Color: {color}</p>
            </div>
            <label>Select a Color:</label>
            <input type="color" value={color} onChange={handleColorChange}/>
        </div>
    );
}

export default ColorPicker;