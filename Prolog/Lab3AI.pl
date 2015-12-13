%   Nonograms
%          Problem statement:          Solution:

%          |_|_|_|_|_|_|_|_| 3         |_|X|X|X|_|_|_|_| 3           
%          |_|_|_|_|_|_|_|_| 2 1       |X|X|_|X|_|_|_|_| 2 1         
%          |_|_|_|_|_|_|_|_| 3 2       |_|X|X|X|_|_|X|X| 3 2         
%          |_|_|_|_|_|_|_|_| 2 2       |_|_|X|X|_|_|X|X| 2 2         
%          |_|_|_|_|_|_|_|_| 6         |_|_|X|X|X|X|X|X| 6           
%          |_|_|_|_|_|_|_|_| 1 5       |X|_|X|X|X|X|X|_| 1 5         
%          |_|_|_|_|_|_|_|_| 6         |X|X|X|X|X|X|_|_| 6           
%          |_|_|_|_|_|_|_|_| 1         |_|_|_|_|X|_|_|_| 1           
%          |_|_|_|_|_|_|_|_| 2         |_|_|_|X|X|_|_|_| 2           
%           1 3 1 7 5 3 4 3             1 3 1 7 5 3 4 3              
%           2 1 5 1                     2 1 5 1                      



% --------------------------------------------------------------------------

% nonogram(RowNums,ColNums,Solution,Opt) :- given the specifications for
%    the rows and columns in RowNums and ColNums, respectively, the puzzle
%    is solved by Solution, which is a row-by-row representation of
%    the filled puzzle grid. Opt = 0 is without optimization, Opt = 1
%    optimizes the order of the line tasks (see below). 
%    (list-of-int-lists,list-of-int-lists,list-char-lists)    (+,+,-)

nonogram(RowNums,ColNums,Solution,Opt) :-
   length(RowNums,NRows),
   length(ColNums,NCols),
   make_rectangle(NRows,NCols,Rows,Cols),
   append(Rows,Cols,Lines),
   append(RowNums,ColNums,LineNums),
   maplist(make_runs,LineNums,LineRuns),
   combine(Lines,LineRuns,LineTasks),
   optimize(Opt,LineTasks,OptimizedLineTasks),
   solve(OptimizedLineTasks),
   Solution = Rows.
 
combine([],[],[]).
combine([L1|Ls],[N1|Ns],[task(L1,N1)|Ts]) :- combine(Ls,Ns,Ts).

solve([]). 
solve([task(Line,LineRuns)|Tasks]) :- 
   place_runs(LineRuns,Line),
   solve(Tasks).

%  -------------------------------------------------------------------------

make_rectangle(NRows,NCols,Rows,Cols) :-
   NRows > 0, NCols > 0,
   length(Rows,NRows),
   Pred1 =.. [inv_length, NCols],
   checklist(Pred1,Rows),
   length(Cols,NCols),
   Pred2 =.. [inv_length, NRows],
   checklist(Pred2,Cols),
   unify_rectangle(Rows,Cols).

inv_length(Len,List) :- length(List,Len).

unify_rectangle(_,[]).
unify_rectangle([],_).

unify_rectangle([[X|Row1]|Rows],[[X|Col1]|Cols]) :-
   unify_row(Row1,Cols,ColsR), 
   unify_rectangle(Rows,[Col1|ColsR]).   

unify_row([],[],[]).
unify_row([X|Row],[[X|Col1]|Cols],[Col1|ColsR]) :- unify_row(Row,Cols,ColsR).


make_runs([],[]) :- !.
make_runs([Len1|Lens],[Run1-T|Runs]) :- 
   put_x(Len1,Run1,T),
   make_runs2(Lens,Runs).

% make_runs2(RunLens,Runs) :- same as make_runs, except that the runs
%    start with a space character.

make_runs2([],[]).
make_runs2([Len1|Lens],[[' '|Run1]-T|Runs]) :- 
   put_x(Len1,Run1,T),
   make_runs2(Lens,Runs).

put_x(0,T,T) :- !.
put_x(N,['x'|Xs],T) :- N > 0, N1 is N-1, put_x(N1,Xs,T).



place_runs([],[]).
place_runs([Line-Rest|Runs],Line) :- place_runs(Runs,Rest).
place_runs(Runs,[' '|Rest]) :- place_runs(Runs,Rest).
 

optimize(0,LineTasks,LineTasks).     

optimize(1,LineTasks,OptimizedLineTasks) :- 
   label(LineTasks,LabelledLineTasks),
   sort(LabelledLineTasks,SortedLineTasks),
	unlabel(SortedLineTasks,OptimizedLineTasks).
   
label([],[]).
label([task(Line,LineRuns)|Tasks],[task(Count,Line,LineRuns)|LTasks]) :- 
   length(Line,N),   
   findall(L,(length(L,N), place_runs(LineRuns,L)),Ls),
   length(Ls,Count),
   label(Tasks,LTasks).

unlabel([],[]).
unlabel([task(_,Line,LineRuns)|LTasks],[task(Line,LineRuns)|Tasks]) :- 
   unlabel(LTasks,Tasks).

% Printing the solution ----------------------------------------------------

% print_nonogram(RowNums,ColNums,Solution) :-

print_nonogram([],ColNums,[]) :- print_colnums(ColNums).
print_nonogram([RowNums1|RowNums],ColNums,[Row1|Rows]) :-
   print_row(Row1),
   print_rownums(RowNums1),
   print_nonogram(RowNums,ColNums,Rows).

print_row([]) :- write('  ').
print_row([X|Xs]) :- print_replace(X,Y), write(' '), write(Y), print_row(Xs).
   
print_replace(' ',' ') :- !.
print_replace(x,'*').

print_rownums([]) :- nl.
print_rownums([N|Ns]) :- write(N), write(' '), print_rownums(Ns).

print_colnums(ColNums) :-
   maxlength(ColNums,M,0),
	print_colnums(ColNums,ColNums,1,M).

maxlength([],M,M).
maxlength([L|Ls],M,A) :- length(L,N), B is max(A,N), maxlength(Ls,M,B). 

print_colnums(_,[],M,M) :- !, nl.
print_colnums(ColNums,[],K,M) :- K < M, !, nl,
   K1 is K+1, print_colnums(ColNums,ColNums,K1,M).
print_colnums(ColNums,[Col1|Cols],K,M) :- K =< M, 
   write_kth(K,Col1), print_colnums(ColNums,Cols,K,M).
   
write_kth(K,List) :- nth1(K,List,X), !, writef('%2r',[X]).
write_kth(_,_) :- write('  ').

% --------------------------------------------------------------------------


specimen_nonogram(
   'Hen',
   [[3], [2,1], [3,2], [2,2], [6], [1,5], [6], [1], [2]],
   [[1,2], [3,1], [1,5], [7,1], [5], [3], [4], [3]]
   ).

specimen_nonogram(
   'Jack & The Beanstalk',
   [[3,1], [2,4,1], [1,3,3], [2,4], [3,3,1,3], [3,2,2,1,3],
    [2,2,2,2,2], [2,1,1,2,1,1], [1,2,1,4], [1,1,2,2], [2,2,8],
    [2,2,2,4], [1,2,2,1,1,1], [3,3,5,1], [1,1,3,1,1,2],
    [2,3,1,3,3], [1,3,2,8], [4,3,8], [1,4,2,5], [1,4,2,2],
    [4,2,5], [5,3,5], [4,1,1], [4,2], [3,3]],
   [[2,3], [3,1,3], [3,2,1,2], [2,4,4], [3,4,2,4,5], [2,5,2,4,6],
    [1,4,3,4,6,1], [4,3,3,6,2], [4,2,3,6,3], [1,2,4,2,1], [2,2,6],
    [1,1,6], [2,1,4,2], [4,2,6], [1,1,1,1,4], [2,4,7], [3,5,6],
    [3,2,4,2], [2,2,2], [6,3]]
   ).

specimen_nonogram(
   'WATER BUFFALO',
   [[5], [2,3,2], [2,5,1], [2,8], [2,5,11], [1,1,2,1,6], [1,2,1,3],
    [2,1,1], [2,6,2], [15,4], [10,8], [2,1,4,3,6], [17], [17],
    [18], [1,14], [1,1,14], [5,9], [8], [7]],
   [[5], [3,2], [2,1,2], [1,1,1], [1,1,1], [1,3], [2,2], [1,3,3],
    [1,3,3,1], [1,7,2], [1,9,1], [1,10], [1,10], [1,3,5], [1,8],
    [2,1,6], [3,1,7], [4,1,7], [6,1,8], [6,10], [7,10], [1,4,11],
    [1,2,11], [2,12], [3,13]]
   ).

   % --------------------------------------------------------------------------


test(Name,Opt) :- 
   specimen_nonogram(Name,Rs,Cs),
   nonogram(Rs,Cs,Solution,Opt), nl,
   print_nonogram(Rs,Cs,Solution).

main. :- time(test('Hen',0)).


%
%
% Каждая ячейка принадлежит (по горизонтали) строке и столбцу (по вертикали). 
% Мы собираемся рассматривать каждую ячейку в качестве переменной, которая может быть доступна через строку или столбец.
% Целью является каждый квадрат либо с точкой либо без.

% Строки и столбцы должны быть обработаны аналогичным образом. 
% Назвем их "линии", и мы называем последовательность точек "пробегом".
% Для каждой данной линии, есть, в целом, несколько возможностей, чтобы положить иксы на квадраты. 
% Например, если мы должны поставить пробег длиной 3 в линию длиной 8, то есть 6 способов сделать это.

% В принципе, все эти возможности должны быть исследованы для всех линий.
% Тем не менее, потому что мы заинтересованы только в одном решении, 
% не во всех из них, это может быть выгодно, чтобы сначала попробовать линий с несколькими возможностей.


%   make_rectangle(NRows,NCols,Rows,Cols) :- прямоугольный список с @NRows строками 
%   и @NCols столбцами. Доступ к ячейке может быть и с @Rows и с @Cols как 
%   [[_,X|_]|_] или [_,[X|_]|_]. Cool!
%   (integer,integer,list-of-char-list,list-of-char-list)    (+,+,_,_)

% make_runs(RunLens,Runs) :- Пробег это список списков 
%    соотвествующий  данному пробегу длинной @RunLens. Любой пробег - отдельный список
%    e.g ['x','x'|T]-T. (integer-list,list-of-runs) (+,-)

% place_runs(Runs,Line) :- @Runs - список пробегов.
%    @Line - список решений (*, ' ').
%    Пробег помещается на @Line,  выбирая с помощью бэктрекинга лучшую возможность построения пробега.

% Оптимизация - сортировка расстановки пробегов в сторону большего. 




