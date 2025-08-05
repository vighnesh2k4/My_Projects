function List(){
    const fruits=[{name:'Orange', calories:47},
        {name:'Apple', calories:52},
        {name:'Banana', calories:89},
        {name:'Coconut', calories:354},
        {name:'Watermelon', calories:30}];
    const fullListItems=fruits.map(fruit=><li key={fruit.name}>
        {fruit.name}: <b>{fruit.calories}</b></li>);
    
    const lowCalFruits=fruits.filter(fruit=>fruit.calories<69);
    lowCalFruits.sort((a,b)=>a.calories-b.calories);//a.name.localeCompare(b.name));
    const lowCalListItems=lowCalFruits.map(fruit=><li key={fruit.name}>
        {fruit.name}: <b>{fruit.calories}</b></li>);

    return(
        <>
        <ol>{fullListItems}</ol>
        <ol>{lowCalListItems}</ol>
        </>
    );
}

export default List;