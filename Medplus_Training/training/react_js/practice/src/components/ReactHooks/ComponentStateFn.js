import React, {useState} from 'react';

function ComponentStateFn(){
    let [count,setCount]=useState(0);
    const [name,setName]=useState("Guest");
    const updateName=()=>{
        count%2===0? setName("Karna"):setName("Arjun");
        console.log(name);
        console.log(count);
        setCount(count+1);
    };
    return(
        <div>
            <p>Name: {name}</p>
            <button onClick={updateName} >Set Name</button>
        </div>
    );
}

export default ComponentStateFn;