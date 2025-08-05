import { useState, createContext } from 'react';
import ComponentB from './ComponentB.js';
export const UserContext=createContext();
function ComponentA(){
    const [username, setUsername]=useState("Vighnesh");
    return(
        <div className="box">
        <h2>ComponentA</h2>
        <p>Hello {username}</p>
        <UserContext.Provider value={[username, setUsername]}>
            <ComponentB/>
        </UserContext.Provider>
        </div>
    );
}
export default ComponentA;