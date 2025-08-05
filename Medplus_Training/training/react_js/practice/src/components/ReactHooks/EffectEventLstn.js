import { useEffect, useState } from "react";
function EffectEventLstn(){
    const [width,setWidth]=useState(window.innerWidth);
    const [height, setHeight]=useState(window.innerHeight);
    useEffect(()=>{
        window.addEventListener("resize",handleEvent);
        console.log("EVENT LISTENER ADDED");
        return ()=> {
            window.removeEventListener("resize",handleEvent);
        console.log("EVENT LISTENER REMOVED");
        };
    },[]);
    function handleEvent(){
        setWidth(window.innerWidth);
        setHeight(window.innerHeight);
    }
    return(
        <>
        <p>Window width: {width}</p>
        <p>Window height: {height}</p>
        </>
    );
}
export default EffectEventLstn