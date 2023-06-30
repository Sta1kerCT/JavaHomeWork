"use strict";

let variables = new Map([["x", 0], ["y", 1], ["z", 2]])
function Item(arg) {
    this.arg = arg;
}
Item.prototype.toString = function() {
    return String(this.arg);
}
Item.prototype.prefix = function() {
    return String(this.arg);
}
Item.prototype.postfix = function() {
    return String(this.arg);
}
Item.prototype.evaluate = function() {
    return null;
}
Item.prototype.diff = function(variable) {
    return null;
}
function itemExtend(item, evaluateImpl, diffImpl) {
    item.prototype = Object.create(Item.prototype);
    item.prototype.constructor = item;
    item.prototype.evaluate = evaluateImpl;
    item.prototype.diff = diffImpl;
}


function Const(arg) {
    Item.call(this, arg);
}
itemExtend(Const, function() { return this.arg }, f => new Const(0))


function Variable(arg) {
    Item.call(this, arg)
}
itemExtend(Variable,
  function(...args) { return args[variables.get(this.arg)]; },
  function(variable) {
      if (this.arg === variable) {
          return new Const(1);
      } else {
          return new Const(0);
      }
  })



function MultiOperation(...args) {
    this.args = args
}
MultiOperation.prototype.getOperationString = function() {
    return "";
}
MultiOperation.prototype.calc = function(...args) {
    return null;
}
MultiOperation.prototype.evaluate = function(x, y, z) {
    let result = this.args.map((arg) => arg.evaluate(x, y, z));
    return this.calc(...result);
}
MultiOperation.prototype.toString = function() {
    return this.args.map((arg) => arg.toString()).join(" ") + " " + this.getOperationString();
}
MultiOperation.prototype.prefix = function() {
    return "(" + this.getOperationString() + " " +
    this.args.map((arg) => arg.prefix()).join(" ") + ")" ;
}
MultiOperation.prototype.postfix = function() {
    return "(" + this.args.map((arg) => arg.postfix()).join(" ") + " " + this.getOperationString() + ")" ;
}
MultiOperation.prototype.diff = function(variable) {
    return null;
}
function multiOpExtend(operation, getOperationStringImpl, calcImpl, diffImpl) {
    operation.prototype = Object.create(MultiOperation.prototype);
    operation.prototype.constructor = operation;
    operation.prototype.getOperationString = getOperationStringImpl
    operation.prototype.calc = calcImpl;
    operation.prototype.diff = diffImpl;
}
function Negate(...args) {
    MultiOperation.call(this, ...args);
}
multiOpExtend(Negate, f => "negate", (a) => -a, function(variable) {
    return new Negate(this.args[0].diff(variable));
});

function Add(...args) {
    MultiOperation.call(this, ...args);
}
multiOpExtend(Add, f => "+", (a, b) => a + b, function(variable) {
    return new Add(this.args[0].diff(variable), this.args[1].diff(variable));
});


function Subtract(...args) {
    MultiOperation.call(this, ...args);
}
multiOpExtend(Subtract, f => "-", (a, b) => a - b, function(variable) {
    return new Subtract(this.args[0].diff(variable), this.args[1].diff(variable));
});


function Multiply(...args) {
    MultiOperation.call(this, ...args);
}
multiOpExtend(Multiply, f => "*", (a, b) => a * b, function(variable) {
    return new Add(new Multiply(this.args[0].diff(variable), this.args[1]),
     new Multiply(this.args[1].diff(variable), this.args[0]));
});


function Divide(...args) {
    MultiOperation.call(this, ...args);
}
multiOpExtend(Divide, f => "/", (a, b) => a / b, function(variable) {
    return new Divide(
        new Subtract(
            new Multiply(this.args[0].diff(variable), this.args[1]),
            new Multiply(this.args[1].diff(variable), this.args[0])
            ),
        new Multiply(this.args[1], this.args[1]));
});

function square(x) { return x * x; }
function foldLeft(f, argF, zero, args) {
    let result = zero;
    for (const arg of args) {
        result = f(result, argF(arg));
    }
    return result;
}
function squareDiff(arg, variable) {
    return new Multiply(new Const(2), new Multiply(arg, arg.diff(variable)))
}
function SumsqN(...args) {
    MultiOperation.call(this, ...args);
}
multiOpExtend(SumsqN, function() {
   return "sumsq" + this.args.length
   },
   function(...ops) {
       return foldLeft((a, b) => a + b, square, 0, ops);
   },
   function(variable) {
      return foldLeft((a, b) => new Add(a, b), (arg) => squareDiff(arg, variable),
       squareDiff(this.args[0], variable), this.args.slice(1));
});


function Sumsq2(...args) {
    SumsqN.call(this, ...args);
}
Sumsq2.prototype = Object.create(SumsqN.prototype);


function Sumsq3(...args) {
    SumsqN.call(this, ...args);
}
Sumsq3.prototype = Object.create(SumsqN.prototype);

function Sumsq4(...args) {
    SumsqN.call(this, ...args);
}
Sumsq4.prototype = Object.create(SumsqN.prototype);

function Sumsq5(...args) {
    SumsqN.call(this, ...args);
}
Sumsq5.prototype = Object.create(SumsqN.prototype);


function DistanceN(...args) {
    MultiOperation.call(this, ...args)
}
multiOpExtend(DistanceN, function() {
   return "distance" + this.args.length
   },
   function(...args) {
       return Math.sqrt(foldLeft((a, b) => a + b, square, 0, args));
   },
   function(variable) {
       return new Divide(
        new SumsqN(...this.args).diff(variable),
        new Multiply(new Const(2), new DistanceN(...this.args))
        );
   });

function Distance2(...args) {
    DistanceN.call(this, ...args)
}
Distance2.prototype = Object.create(DistanceN.prototype);

function Distance3(...args) {
    DistanceN.call(this, ...args)
}
Distance3.prototype = Object.create(DistanceN.prototype);

function Distance4(...args) {
    DistanceN.call(this, ...args)
}
Distance4.prototype = Object.create(DistanceN.prototype);

function Distance5(...args) {
    DistanceN.call(this, ...args)
}
Distance5.prototype = Object.create(DistanceN.prototype);
let exp = function(arg) {
    return Math.pow(Math.E, arg);
}

function Sumexp(...args) {
    MultiOperation.call(this, ...args);
}
function expDiff(arg, variable) {
    return new Multiply(arg.diff(variable), new Sumexp(arg));
}
multiOpExtend(Sumexp, f => "sumexp",
   function(...args) {
       return foldLeft((a, b) => a + b, exp, 0, args);
   },
   function(variable) {
      return foldLeft((a, b) => new Add(a, b), (arg) =>
      expDiff(arg, variable), new Const(0), this.args);
   });

function LSE(...args) {
    MultiOperation.call(this, ...args);
}
multiOpExtend(LSE, f => "lse",
   function(...args) {
       return Math.log(foldLeft((a, b) => a + b, exp, 0, args));
   },
   function(variable) {
      return new Multiply(new Divide(new Const(1), new Sumexp(...this.args)), new Sumexp(...this.args).diff(variable));
   });

let multiOp = new Map([
["*", [2, Multiply]], ["+", [2, Add]], ["-", [2, Subtract]], ["/", [2, Divide]],
 ['negate', [1, Negate]], ["lse", [-1, LSE]], ["distance2", [2, DistanceN]], ["sumexp", [-1, Sumexp]],
 ["distance3", [3, DistanceN]], ["distance4", [4, DistanceN]], ["distance5", [5, DistanceN]],
 ["sumsq2", [2, SumsqN]], ["sumsq3", [3, SumsqN]], ["sumsq4", [4, SumsqN]], ["sumsq5", [5, SumsqN]],
]);
let parse = function(s) {
    let stack = [];
    let arr = s.trim().split(/\s+/);
    let pushOp = function(n, curOp) {
        let result = stack.splice(stack.length - n, n);
        stack.push(new curOp(...result));
    }
    arr.forEach(function(op) {
        if (multiOp.has(op)) {
            pushOp(multiOp.get(op)[0], multiOp.get(op)[1]);
        } else if (variables.has(op)) {
            stack.push(new Variable(op));
        } else {
            stack.push(new Const(Number(op)));
        }
    });
    return stack.pop();
}

let IllegalState = function(message) {
    Error.call(this);
    this.message = message;
}
IllegalState.prototype = Object.create(Error.prototype);
IllegalState.prototype.getMessage = function() {
    return this.message;
}
IllegalState.prototype.constructor = IllegalState;

let parsePrefix = function(s) {
    return parseUni(s, true);
}
let parsePostfix = function(s) {
    return parseUni(s, false);
}

let parseUni = function(s, pref) {
   let arr = s.trim().replaceAll('(', " ( ").
   replaceAll(')', " ) ").split(/\s+/).filter((arg) => (arg.length > 0));
   let result = parseExpr(arr, pref);
   if (arr.length > 0) {
       throw new IllegalState("Illegal expression: expected EOF, found " + (pref ? arr.shift() : arr.pop()));
   }
   return result;
}

let parseExpr = function(arr, pref) {
    let cur;
    let open;
    let close;
    if (pref) {
        cur = arr.shift();
        open = '(';
        close = ')';
    } else {
        cur = arr.pop();
        open = ')';
        close = '(';
    }
    if (cur === open) {
        return parseOp(arr, pref);
    } else if (variables.has(cur)) {
        return new Variable(cur);
    } else if (cur === close) {
        return "EOF";
    } else {
        let parsed = Number.parseInt(cur);
        if (Number.isNaN(parsed) || parsed != cur) {
            throw new IllegalState("Unsupported argument: " + cur);
        }
        return new Const(Number(cur));
    }
    return result;
}

let parseOp = function(arr, pref) {
    let cur = pref ? arr.shift() : arr.pop();
    if (multiOp.has(cur)) {
        let opInfo = multiOp.get(cur);
        let args = [];
        let last = parseExpr(arr, pref);
        while (last !== "EOF") {
            args.push(last);
            last = parseExpr(arr, pref);
        }
        if (!pref) {
            args.reverse();
        }
        if (opInfo[0] !== -1 && opInfo[0] === args.length) {
            return new opInfo[1](...args);
        } else if (opInfo[0] === -1 && args.length >= 0) {
            return new opInfo[1](...args);
        } else {
            throw new IllegalState("Unsupported number of arguments (" + arg.length + ") for operation: " + cur);
        }
    } else {
        throw new IllegalState("Unsupported operation: " + cur);
    }
}