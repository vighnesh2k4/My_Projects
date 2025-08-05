import { useContext } from "react";
import { UserContext } from "./ComponentA";
function ComponentD(){
    const [username, setUsername]=useContext(UserContext);
    
    return(
        <div className="box">
        <h2>ComponentD</h2>
        <p>Hello {username}</p>
        <input type="text" placeholder="change name" onChange={(e)=>{setUsername(e.target.value)}} />
        </div>
    );
}
export default ComponentD;