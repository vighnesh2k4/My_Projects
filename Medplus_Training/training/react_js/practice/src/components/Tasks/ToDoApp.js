import { useState } from "react";
import {FaTrash, FaArrowUp, FaArrowDown} from 'react-icons/fa';

function ToDoApp(){
    const [tasks, setTasks]=useState([]);
    const handleAddTask=()=>{
        const newTask=document.getElementById("newTask").value;
        setTasks(t=>[...t, newTask])
        document.getElementById("newTask").value='';
    }
    const handleRemoveTask=(index)=>{
        setTasks(tasks.filter((_,i)=>i!==index));
    }
    const handleArrowUp=(index)=>{
        if(index>0){
            const updtTask=[...tasks];
            [updtTask[index], updtTask[index-1]]=[updtTask[index-1], updtTask[index]];
            setTasks(updtTask);
        }
    }
    const handleArrowDown=(index)=>{
        if(index<tasks.length-1){
            const updtTask=[...tasks];
            [updtTask[index], updtTask[index+1]]=[updtTask[index+1], updtTask[index]];
            setTasks(updtTask);
        }
    }
    return(
        <div>
        <h1>To Do List Application</h1>
        <label>Enter Task to ToDo List: <input type="text" placeholder="Enter Task" id="newTask"/></label>
        <button onClick={handleAddTask}>Add Task</button>
        <h3>To Do List:</h3>
        <ol>
            {tasks.map((task, index)=>(<li key={index}>{task} <button onClick={()=>handleRemoveTask(index)}><FaTrash color="red" /></button>
            <button onClick={()=>handleArrowUp(index)}><FaArrowUp /></button><button onClick={()=>handleArrowDown(index)}><FaArrowDown /></button></li>))}
        </ol>
        </div>
    );
}

export default ToDoApp;