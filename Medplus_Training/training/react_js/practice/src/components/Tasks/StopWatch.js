import { useState, useRef, useEffect } from "react";
function StopWatch(){
    const [isRunning, setIsRunning] = useState(false);
    const [elapsedTime, setElapsedTime]=useState(0);
    const intervalRefId=useRef(null);
    const startTimeRef=useRef(0);
    useEffect(()=>{
        if(isRunning){
            intervalRefId.current=setInterval(()=>{
                setElapsedTime(Date.now()-startTimeRef.current);
            },10);
        }
        return ()=>{
            clearInterval(intervalRefId.current);
        }
    },[isRunning]);
    function start(){
        setIsRunning(true);
        startTimeRef.current=Date.now()-elapsedTime;
    }
    function stop(){
        setIsRunning(false);
    }
    function reset(){
        setElapsedTime(0);
        setIsRunning(false);
    }
    function formatTime(){
        let hours=Math.floor(elapsedTime/(1000*60*60));
        let minutes=Math.floor(elapsedTime/(1000*60)%60);
        let seconds=Math.floor(elapsedTime/(1000)%60);
        let milliseconds=Math.floor((elapsedTime%1000)/10);

        hours=String(hours).padStart(2,"0");
        minutes=String(minutes).padStart(2,"0");
        seconds=String(seconds).padStart(2,"0");
        milliseconds=String(milliseconds).padStart(2,"0");
        
        return `${hours}:${minutes}:${seconds}:${milliseconds}`;
    }
    return(
        <>
        <div>
        <h1>Stop Watch</h1>
        <h2>{formatTime()}</h2>
        </div>
        <div>
        <button onClick={start} style={{color:"green"}}>Start</button>
        <button onClick={stop} style={{color:"red"}}>Stop</button>
        <button onClick={reset} style={{color:"blue"}}>Reset</button>
        </div>
        </>
    );
}
export default StopWatch;