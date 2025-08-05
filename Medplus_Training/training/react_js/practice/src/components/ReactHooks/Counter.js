import { useState } from "react";
function Counter(){
    const [counter, setCounter]=useState(0);
    const Incr=()=>{
        setCounter(counter+1);
    }
    const Decr=()=>{
        setCounter(counter-1);
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

export default Counter;