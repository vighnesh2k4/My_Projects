/* const dog={
    name:"Jackie",
    breed:"Indian dog",
    age:2,
    weight:20,
    eat: function() {
        console.log("Home Food");
    },
    bark(){
        console.log('bow bow');
    }
}
console.log(dog)
*/

function get_dog(name, breed, age, weight){
    return {
        name,
        breed:breed,
    age:age,
    weight:weight,
    eat: function() {
        console.log(this.name+"Home Food");
    },
    bark(){
        console.log(this.name+'bow bow');
    }
    }
}

// console.log(get_dog("jackie","indian dog",2,20));
const anotherDog=get_dog("Jackie","indian dog",2,20);
console.log(anotherDog);

const person={
    Name: "vighnesh"
}
person.age=21;
console.log(person);
function add(n1,n2){
    return n1+n2;
}
let addition=add;
console.log(addition(11,11));
console.log(add.length);

// function Programmer(name){
//     this.name=name;
//     this.writeCode=function(){
//         console.log("Code in JavaSCript");
//     }
// }
// console.log(Programmer.length);
// console.log(Programmer.constructor);

const Programmer=new Function('name',`
    this.name=name;
    this.writeCode=function(){
    console.log(this.name+" Codes in JavaScript");
    }
`);

const newProgrammer=new Programmer('Vighnesh');
newProgrammer.writeCode();

let x=10;
let y=x;
x=20;
console.log(x);
console.log(y);


x={value:100};
y=x;
x.value=1000;
console.log(x.value);
console.log(y.value);
