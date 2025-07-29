/* arithmetic ops */
console.log(16+6);
console.log(45-23);
console.log(11*2);
console.log(88/4);
console.log(22%23);
console.log(2**4);
/*assignment ops */
let a=10;
console.log(a);
a+=10;
console.log(a);
a-=10
console.log(a);
a*=10
console.log(a);
a/=10
console.log(a);
a%=6
console.log(a);
/*increment and decrement ops*/
console.log(++a);
console.log(a++);
console.log(a);
console.log(--a);
console.log(a--);
console.log(a);
// comparision operators
console.log(a>2);
console.log(a>=4);
console.log(a<2);
console.log(a<=4);
console.log(a==4);
console.log(a!=5);
//equality ops
a=22
let b='22';
console.log(a==b); //loose equality
console.log(a===b); //strict equality
//ternary ops
let age=17;
var licence=age>18?"eligible":"not eligible";
console.log(licence);
//logical ops
console.log(true||false);
console.log(true&&false);
console.log(true&&true);
console.log(!false);
//null coalescing
a=null;
const result=a?? "null coalescing";
console.log(result);
//logical with non bool
console.log(false||"pink"||"red");
console.log(false&&"pink"&&"red");