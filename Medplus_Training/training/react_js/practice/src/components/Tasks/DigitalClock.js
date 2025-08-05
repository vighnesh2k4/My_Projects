import { useState, useEffect } from "react";
function DigitalClock(){
    const [time, setTime]=useState(new Date());
    useEffect(()=>{
        const intervalId=setInterval(()=>{
            setTime(new Date())
            },1000);
        return ()=>{
            clearInterval(intervalId);
        };
    },[]);
    function digiClock(){
        let hours=time.getHours();
        const mins=time.getMinutes();
        const secs=time.getSeconds();
        const meridiem= hours>12? "PM" : "AM";

        hours= hours % 12 || 12;
        return `${padZero(hours)}:${padZero(mins)}:${padZero(secs)} ${meridiem}`;
    }
    function padZero(number){
        return (number<10? "0":"")+number;
    }
    return(
    <>
        <h1>Digital Clock</h1>
        <h2>{digiClock()}</h2>
    </>
    );
}
export default DigitalClock;