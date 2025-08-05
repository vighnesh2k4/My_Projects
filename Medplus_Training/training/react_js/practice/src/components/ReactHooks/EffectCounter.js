import { useEffect, useState } from "react";
function EffectCounter(){
    const [count, setCount]=useState(0);
    const [temp1, setTemp1]=useState("");
    const [temp2, setTemp2]=useState("");
    const [temp3, setTemp3]=useState("");
    const handleAddBtn=()=>{
        setCount(c=>c+1);
    }
    useEffect(()=>{
        setTemp1(`Count: ${count}`);
    })
    useEffect(()=>{
        setTemp2(`Count: ${count}`);
    },[])
    useEffect(()=>{
        setTemp3(`Count: ${count}`);
    },[count])
    return(
    <>
    <p>Count: {count}</p>
    <button onClick={handleAddBtn}>Add</button>
    <pre>
        useEffect : {temp1+"\n"}
        useEffect with [] : {temp2+"\n"}
        useEffect with [value] : {temp3}
    </pre>
    </>
    );
}
export default EffectCounter;