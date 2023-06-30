lookup(K, [(K, V) | _], V).
lookup(K, [_ | T], V) :- lookup(K, T, V).

variable(Name, variable(Name)).
const(Value, const(Value)).

op_add(A, B, operation(op_add, A, B)).
op_subtract(A, B, operation(op_subtract, A, B)).
op_multiply(A, B, operation(op_multiply, A, B)).
op_divide(A, B, operation(op_divide, A, B)).
op_negate(A, operation(op_negate, A)).
op_not(A, operation(op_not, A)).
op_and(A, B, operation(op_and, A, B)).
op_or(A, B, operation(op_or, A, B)).
op_xor(A, B, operation(op_xor, A, B)).


operation(op_add, A, B, R) :- R is A + B.
operation(op_subtract, A, B, R) :- R is A - B.
operation(op_multiply, A, B, R) :- R is A * B.
operation(op_divide, A, B, R) :- R is A / B.
operation(op_negate, A, R) :- R is -A.
operation(op_not, A, R) :- A =< 0, R is 1, !.
operation(op_not, A, R) :- R is 0.
operation(op_and, A, B, R) :- A > 0, B > 0, R is 1, !.
operation(op_and, A, B, R) :- R is 0.
operation(op_or, A, B, R) :- (A > 0 ; B > 0), R is 1, !.
operation(op_or, A, B, R) :- R is 0.0.
operation(op_xor, A, B, R) :- ((A > 0, B =< 0) ; (A =< 0, B > 0)), R is 1, !.
operation(op_xor, A, B, R) :- R is 0.0.


evaluate(const(V), _, V).
evaluate(variable(Name), Vars, R) :- atom_chars(Name, [H | _]), lookup(H, Vars, R).
evaluate(operation(Op, A), Vars, R) :-
    evaluate(A, Vars, AV),
    operation(Op, AV, R).
evaluate(operation(Op, A, B), Vars, R) :-
    evaluate(A, Vars, AV),
    evaluate(B, Vars, BV),
    operation(Op, AV, BV, R).

nonvar(V, T) :- var(V).
nonvar(V, T) :- nonvar(V), call(T).

:- load_library('alice.tuprolog.lib.DCGLibrary').

skip_space(A) --> {var(A), !}, [' '], skip_space(A).
skip_space(_) --> [].
var_p([]) --> [].
var_p([H | T]) -->
  { member(H, ['x', 'X', 'y', 'Y', 'z', 'Z'])},
  [H], var_p(T).
var_p([H | T]) -->
  { nonmember(H, ['x', 'X', 'y', 'Y', 'z', 'Z'])},
  [],
  var_p(T).

expr_p(variable(Name), Q) -->
  { nonvar(Name, atom_chars(Name, Chars)) },
  skip_space(Q), var_p(Chars), skip_space(Q),
  { Chars = [_ | _], atom_chars(Name, Chars) }.

expr_p(const(Value), Q) -->
  { nonvar(Value, number_chars(Value, Chars)) },
  skip_space(Q), digits_p(Chars), skip_space(Q),
  { Chars = [_ , _ | _], number_chars(Value, Chars) }.

digits_p([]) --> [].
digits_p([H | T]) -->
  { member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-'])},
  [H],
  digits_p(T).

op_p(op_xor) --> ['^', '^'].
op_p(op_or) --> ['|', '|'].
op_p(op_and) --> ['&', '&'].
op_p(op_not) --> ['!'].
op_p(op_add) --> ['+'].
op_p(op_subtract) --> ['-'].
op_p(op_multiply) --> ['*'].
op_p(op_divide) --> ['/'].
op_p(op_negate) --> ['n', 'e', 'g', 'a', 't', 'e'].

expr_p(operation(Op, A, B), Q) --> skip_space(Q), ['('], expr_p(A, Q), [' '],
 op_p(Op), [' '], expr_p(B, Q), [')'], skip_space(Q).
expr_p(operation(Op, A), Q) --> skip_space(Q), op_p(Op), [' '], expr_p(A, Q).

infix_str(E, A) :- ground(E), phrase(expr_p(E, a), C), atom_chars(A, C).
infix_str(E, A) :-   atom(A), atom_chars(A, C), phrase(expr_p(E, Q), C).
