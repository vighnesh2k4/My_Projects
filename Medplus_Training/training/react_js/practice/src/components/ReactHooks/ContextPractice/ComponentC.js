import ComponentD from './ComponentD.js';
function ComponentC(){
    return(
        <div className="box">
        <h2>ComponentC</h2>
        < ComponentD />
        </div>
    );
}
export default ComponentC;