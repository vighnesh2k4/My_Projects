import { useEffect, useRef } from "react";
function RefComponent(){
    const r=useRef(0);
    const ipRef1=useRef(null);
    const ipRef2=useRef(null);
    const ipRef3=useRef(null);
    useEffect(()=>{
        console.log("COMP RENDERED");
    });
    function handleClick1(){
        r.current++;
        console.log(r.current);
    }
    function handleClickIp1(){
        ipRef1.current.focus();
        ipRef1.current.style.backgroundColor="orange";
        ipRef2.current.style.backgroundColor="";
        ipRef3.current.style.backgroundColor="";
    }
    function handleClickIp2(){
        ipRef2.current.focus();
        ipRef2.current.style.backgroundColor="gray";
        ipRef1.current.style.backgroundColor="";
        ipRef3.current.style.backgroundColor="";
    }
    function handleClickIp3(){
        ipRef3.current.focus();
        ipRef3.current.style.backgroundColor="aqua";
        ipRef1.current.style.backgroundColor="";
        ipRef2.current.style.backgroundColor="";
    }

    return(
        <>
        <button onClick={handleClick1} >Click Me 1</button>
        <br/><input ref={ipRef1} />
        <button onClick={handleClickIp1} >Click Me Ip1</button>
        <br/><input ref={ipRef2} />
        <button onClick={handleClickIp2} >Click Me Ip2</button>
        <br/><input ref={ipRef3} />
        <button onClick={handleClickIp3} >Click Me Ip3</button>
        </>
    );
}
export default RefComponent;