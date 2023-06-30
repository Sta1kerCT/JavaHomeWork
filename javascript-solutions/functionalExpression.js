"use strict";
// :NOTE: это не каррирование
let uncurry = function(f) {
    // :NOTE: как эта функция связана с множеством возможных переменных?
    return (...args) => (x, y, z) => {
        let result = args.map((arg) => arg(x, y, z))
        return f(...result);
    }
};
let add = uncurry((a, b) => a + b)
let cnst = a => () => a;
let variable = a => (x, y, z) => a === "x" ? x : a === "y" ?
y : a === "z" ? z : undefined;
let negate = uncurry(a => -a);
let subtract = uncurry((a, b) => a - b);
let divide = uncurry((a, b) => a / b);
let multiply = uncurry((a, b) => a * b);
let madd = uncurry((a, b, c) => (a * b + c));
let floor = uncurry((a) => (Math.floor(a)));
let ceil = uncurry((a) => (Math.ceil(a)));
let one = cnst(1);
let two = cnst(2);
let constAndVariables = new Map([
["one", one], ["two", two], ['x', variable('x')], ['y', variable('y')], ['z', variable('z')]
]);
let opInfo = new Map([
["*+", [3, madd]], ["*", [2, multiply]], ["+", [2, add]], ["-", [2, subtract]],
["/", [2, divide]], ["^", [1, ceil]], ["_", [1, floor]], ['negate', [1, negate]]
]);
let parse = function(s) {
    let stack = [];
    let arr = s.trim().split(/\s+/);
    arr.forEach(function(op) {
        if (opInfo.has(op)) {
            let result = stack.splice(stack.length - opInfo.get(op)[0], opInfo.get(op)[0])
            stack.push(opInfo.get(op)[1](...result));
        } else if (constAndVariables.has(op)) {
            stack.push(constAndVariables.get(op))
        } else {
            stack.push(cnst(Number(op)));
        }
    });
    return stack.pop();
}