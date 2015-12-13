%clauses :-=
av(2,1).
av(2,-1).
av(1,2).
av(1,-2).
av(-1,2).
av(-1,-2).
av(-2,1).
av(-2,-1).

outborder(X):-X>0,X<7.

elem(X,Y,[X,Y|List]):-!.
elem(X,Y,[_,_|Tail]):-elem(X,Y,Tail).

stephorse(X,Y,X1,Y1):-
av(Dx,Dy),X1=X+Dx,Y1=Y+Dy,
outborder(X1),outborder(Y1).

solve(List,List,1):-!.
solve([X,Y|Past],Res,N):-
stephorse(X,Y,X1,Y1),
not(elem(X1,Y1,Past)),
N1=N-1,
solve([X1,Y1,X,Y|Past],Res,N1).
%goal
main. :- solve([1,1],Res,36),write(Res).