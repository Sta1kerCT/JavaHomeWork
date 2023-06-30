sieve(Cur, N) :- Cur * Cur =< N, (\+ (prime(Cur), filling(Cur, 0, N))), Next is Cur + 1, sieve(Next, N).
filling(Cur, Factor, N) :- Prod is (Cur * (Factor + Cur)), Prod =< N, assert(composite(Prod)),
    assert(divisor(Prod, Cur)), Next is Factor + 1, filling(Cur, Next, N).
init(N) :- sieve(2, N).
prime(2) :- !.
prime(X) :- \+ composite(X).
divisor(X, Y) :- prime(X), Y is X, !.

reduce(X, Last, Cur, []) :- X is Cur, !.
reduce(X, Last, Cur, [S | T]) :- !,
    prime(S),
    Last =< S,
    Next is Cur * S,
    reduce(X, S, Next, T).

prime_divisors(X, L) :- var(X), reduce(X, 2, 1, L), assert(prime_divisors_table(X, L)), !.

prime_divisors(1, []) :- !.
prime_divisors(X, L) :- prime_divisors_table(X, L), !.
prime_divisors(X, L) :- divisor(X, M),
    X1 is X / M,
    prime_divisors(X1, NL),
    append([M], NL, L),
    assert(prime_divisors_table(X, L)), !.

fast_power(_, 0, 1).
fast_power(A, B, R) :-
   B > 0, 1 is mod(B, 2),
   B1 is B - 1, fast_power(A, B1, R1),
   R is A * R1.
fast_power(A, B, R) :-
    B > 0, 0 is mod(B, 2),
    B2 is div(B, 2), fast_power(A, B2, R2),
    R is R2 * R2.

reduce_p(X, Last, Cur, []) :- X is Cur, !.
reduce_p(X, Last, Cur, [(K, V) | T]) :- prime(K), Last =< K,
    fast_power(K, V, VX),
    Next is Cur * VX,
    reduce_p(X, K, Next, T), !.

pack([H | T], Cur_divisor, Cur_pow, Result) :- H =:= Cur_divisor, Next_pow is Cur_pow + 1,
    pack(T, Cur_divisor, Next_pow, Result), !.
pack([H | T], Cur_divisor, Cur_pow, Result) :- H =\= Cur_divisor, pack(T, H, 1, NResult),
    append([(Cur_divisor, Cur_pow)], NResult, Result).
pack([], Cur_divisor, Cur_pow, Result) :- append([(Cur_divisor, Cur_pow)], [], Result), !.

compact_prime_divisors(1, []) :- !.
compact_prime_divisors(X, L) :- var(X), reduce_p(X, 2, 1, L), !.
compact_prime_divisors(X, L) :- prime_divisors(X, PD), divisor(X, M), pack(PD, M, 0, Result),
    append([], Result, L), !.