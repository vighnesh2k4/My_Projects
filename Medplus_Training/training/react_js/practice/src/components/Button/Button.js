import styles from './Button.module.css';

function Button(){
    let count=0;
    const handleClick1=()=>console.log("OUCH!");
    const handleClick2=(name)=>{ count++;
        console.log("Hello "+name+` You clicked me ${count} times`)};
    const handleClick3=(e)=>e.target.textContent="OOPs";
    return(
        <button onDoubleClick={(e)=>handleClick2(e)} className={styles.button}>Click me and watch magic on console</button>
    );
}

export default Button;