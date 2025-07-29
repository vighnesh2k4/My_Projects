let age=20;
if(age>35){
    console.log("Senior");
}else if(age>18){
    console.log("Young");
}
else{
    console.log("Adult");
}
let experience="JR SE";
switch(experience){
    case "JR SE":
        console.log("Experience is less than 2 years");
        break;
    case "SE-1":
        console.log("Experience is 2-3 years");
        break;
    case "SE-2":
        console.log("Experience is 3-5 years");
        break;
    case "SE-3":
        console.log("Experience is 5-10 years");
        break;
    case "Manager":
        console.log("Experience is more than 10 years");
        break;    
    default:
        console.log("Bye");
        break;
}
let arr=[1,2,3,4,5,6,7,8,9];
for(let i=0;i<arr.length;i++){
    console.log(arr[i]);
}

let i=arr.length-1;
while(i>=0){
    console.log(arr[i]);
    i--;
}
i=0;
do {
    
    console.log("hello "+i);
    i++;
}while(i<2);
for(let element of arr){
    console.log(element);
}
const obj1={a:1,b:2,c:3};
for(const key in obj1){
    if(key=='b') break;
    else console.log(key);
}
for(let i=20;i<24;i++){
    if(i%2!=0) continue;
    else console.log("Hello "+i);
}