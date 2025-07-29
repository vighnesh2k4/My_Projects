var firstName="Vighneshwar", lastName="Reddy";
let nickName="venky";
console.log(firstName);
const myBirthday="02.01.2004";
console.log("My birthday: "+myBirthday);

// primitive datatypes
let str="Isha";
console.log(typeof(str));
let age=22;
console.log(typeof(age));
let rating=3.57;
console.log(typeof(rating));
let mobile=22455709497747089160020104n;
console.log(typeof(mobile));
let inlove=false;
console.log(typeof(inlove));
let friend;
console.log(typeof(friend));
friend=null;
console.log(typeof(friend));
const uniqueKey=Symbol();
console.log(typeof(uniqueKey));
let girlFriends={
    friend1:"ritha",
    friend2:"seetha"
}
console.log(typeof(girlFriends));

console.log(girlFriends);
console.log(girlFriends['friend1']);
console.log(girlFriends.friend2);
let someone='friend1';
console.log(girlFriends[someone]);
console.log("using for loop");
for(const key in girlFriends){
    console.log(girlFriends[key]);
}
