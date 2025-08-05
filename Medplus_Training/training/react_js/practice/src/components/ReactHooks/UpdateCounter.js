import { useState } from "react";
function UpdateCounter(){
    const [counter, setCounter]=useState(0);
    const Incr=()=>{
        setCounter(prevCount=>prevCount+1);
        setCounter(c=>c+1);
        setCounter(c=>c+1);
    }
    const Decr=()=>{
        setCounter(c=>c-1);
        setCounter(c=>c-1);
        setCounter(c=>c-1);
    }
    const Res=()=>{
        setCounter(0);
    }
    return(
        < div style={{textAlign: "center"}}>
        <b>Count: {counter}</b>
        <br/>
        <button onClick={Incr}>Increment</button>
        <button onClick={Decr}>Decrement</button>
        <button onClick={Res}>Reset</button>
        </div>
    );
}

export default UpdateCounter;