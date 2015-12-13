%
%  ?-consult(['c.pro']).
%  ?-begin.
% --------------------- Display Board Routines ----------------------------------------------------%
display_board:-display(' '),display_line(0,1,2),nl,display_lines(1).

check_fig(I,J,[[X1,ROW,COL]|Y]):-I=COL,J=ROW,!.
check_fig(I,J,[[X1,ROW,COL]|Y]):-check_fig(I,J,Y).

check_figure(I,J,K,C):-brd(b,PL1,PL2),check_fig(I,J,PL1),C='X',!.
check_figure(I,J,K,C):-brd(b,PL1,PL2),check_fig(I,J,PL2),C='Y',!.
check_figure(I,J,K,C):-K=0,C=' '.
check_figure(I,J,K,C):-C='#'.

display_line(I,J,K):-J<9,K=1,
                       check_figure(I,J,K,C),
                       display('['),display(C),display(']'),
                       J1 is J+1,!,display_line(I,J1,0).
display_line(I,J,K):-J<9,K=0,
                       check_figure(I,J,K,C),
                       display('['),display(C),display(']'),
                       J1 is J+1,!,display_line(I,J1,1).

display_line(I,J,K):-J<9,K=2,display(' '),display(J),display(' '),J1 is J+1,!, display_line(I,J1,2).
display_line(I,J,_):-J=9,!.

display_lines(I):-I<9,I1 is I+1,display(I),K is I mod 2,display_line(I,1,K),nl,display_lines(I1),!.
display_lines(I).
%--------------------------------------------------------------------------------------------------%


% --------------------- Game Playing Routines  (Interface,Initial States)--------------------------%
:- op(1200,xfx,[-->]). 

set_brd(X) :-retract(brd(_,_,_)),asserta(X).
set_brd(X) :-asserta(X).

parse_input(([R1,C1] --> [R2,C2])):-!,display(R1),display(':'),display(C1),
                                      display(' move to '),
                                      display(R2),display(':'),display(C2),nl.
parse_input(_):-display('Input error!'),!.

make_input(X):-!,display('input move in form [R,C] --> [R,C].'),nl,read(X).

% Two players game:
game_play2:-display_board,
            brd(b,PL1,PL2),
            plr_move(PL1,PL2),
           
            brd(b,PLL,PLM),
            retract(brd(b,PLL,PLM)),
            NB = brd(b,PLM,PLL),
            assertz(NB),

           game_play2.

% Two player vs Mashine:
game_play1:-display_board,
            brd(b,PL1,PL2),
            plr_move(PL1,PL2),
           
            brd(b,PLL,PLM),
            retract(brd(b,PLL,PLM)),
            NB = brd(b,PLM,PLL),
            assertz(NB),
           
            display_board,
            comp_move(PLM,PLL),

            brd(b,PLL1,PLM1),
            retract(brd(b,PLL1,PLM1)),
            NB1 = brd(b,PLM1,PLL1),
            assertz(NB1),

           game_play1.

begin:- set_brd(brd(b,[[p,1,1], [p,3,1], [p,5,1], [p,7,1],
                       [p,2,2], [p,4,2], [p,6,2], [p,8,2],
                       [p,1,3], [p,3,3], [p,5,3], [p,7,3]],
                                                    
                      [[p,2,8], [p,4,8], [p,6,8], [p,8,8],
                       [p,1,7], [p,3,7], [p,5,7], [p,7,7],
                       [p,2,6], [p,4,6], [p,6,6], [p,8,6]]
                    )
               ),
        display('Input (game_play1) : Player vs Computer '),nl,
        display('      (game_play2) : Player vs Player '),nl,
        read(Game_play),
        Game_play.
%--------------------------------------------------------------------------------------------------%


% --------------------- Human moving and Computer Logic -------------------------------------------%

% check whether board cell is empty
empty_cell(Row,Col,B):-!,brd(B,PL1,PL2),empty_cell(Row,Col,B,PL1),empty_cell(Row,Col,B,PL2).

empty_cell(Row,Col,B,[[p,R,C]|Y]):- [Row,Col] \= [R,C],!, empty_cell(Row,Col,B,Y).
empty_cell(Row,Col,B,[[p,R,C]|Y]):- [Row,Col]  = [R,C],!, fail.
empty_cell(_,_,_,_).

% Check wheter this move is valid
valid_move(R1,C1,R2,C2):-R2 is R1+1, C2 is C1+1,R2<9,C2<9,R2>0,C2>0,!.
valid_move(R1,C1,R2,C2):-R2 is R1+1, C2 is C1-1,R2<9,C2<9,R2>0,C2>0,!.
valid_move(R1,C1,R2,C2):-R2 is R1-1, C2 is C1+1,R2<9,C2<9,R2>0,C2>0,!.
valid_move(R1,C1,R2,C2):-R2 is R1-1, C2 is C1-1,R2<9,C2<9,R2>0,C2>0,!.

% Check wheter this bit is valid
valid_bit(R1,C1,R2,C2,R3,C3):-R2 is R1+2, C2 is C1+2,R3 is R1+1, C3 is C1+1,R2<9,C2<9,R2>0,C2>0,!.
valid_bit(R1,C1,R2,C2,R3,C3):-R2 is R1+2, C2 is C1-2,R3 is R1+1, C3 is C1-1,R2<9,C2<9,R2>0,C2>0,!.
valid_bit(R1,C1,R2,C2,R3,C3):-R2 is R1-2, C2 is C1+2,R3 is R1-1, C3 is C1+1,R2<9,C2<9,R2>0,C2>0,!.
valid_bit(R1,C1,R2,C2,R3,C3):-R2 is R1-2, C2 is C1-2,R3 is R1-1, C3 is C1-1,R2<9,C2<9,R2>0,C2>0,!.

not(X):-X,!,fail.
not(X).

check_move(([R1,C1] --> [R2,C2]),B,PL1,PL2):-empty_cell(R2,C2,B),not(empty_cell(R1,C1,PL1)),
                                             valid_move(R1,C1,R2,C2).

check_bit(([R1,C1] --> [R2,C2]),B,PL1,PL2,R3,C3):-empty_cell(R2,C2,B),not(empty_cell(R1,C1,PL1)),
                                                valid_bit(R1,C1,R2,C2,R3,C3),not(empty_cell(R3,C3,PL2)).

%Move check
make_move(([R1,C1] --> [R2,C2]),B,PL1,PL2):-check_move(([R1,C1] --> [R2,C2]),B,PL1,PL2),
                                            delete(PL1,[p,R1,C1],PPL1),
                                            append(PPL1,[[p,R2,C2]],PNL1),
                                            retract(brd(B,PL1,PL2)),
                                            NB = brd(B,PNL1,PL2),
                                            assertz(NB).
%Bit a check
make_move(([R1,C1] --> [R2,C2]),B,PL1,PL2):-check_bit(([R1,C1] --> [R2,C2]),B,PL1,PL2,R3,C3),
                                            delete(PL1,[p,R1,C1],PPL1),
                                            delete(PL2,[p,R3,C3],PPL2),
                                            append(PPL1,[[p,R2,C2]],PNL1),
                                            retract(brd(B,PL1,PL2)),
                                            NB = brd(B,PNL1,PPL2),
                                            assertz(NB).

chck_fin.

plr_move(PL1,PL2):- make_input(X),
                    parse_input(X),
                    make_move(X,b,PL1,PL2),
                    chck_fin,!.

comp_move(PL1,PL2):-  assertz(move_cost([0,0,0,0,0])),
                      generate_move(PL1,PL2,b),
                      retract(move_cost([X,R1,C1,R2,C2])),
                      make_move(([R1,C1]-->[R2,C2]),b,PL1,PL2),!.
                    


%All valid moves
v_m(B,R1,C1,R2,C2,PL1,PL2):-R2 is R1+1, C2 is C1+1,R2<9,C2<9,R2>0,C2>0,empty_cell(R2,C2,B),not(empty_cell(R1,C1,B,PL1)).
v_m(B,R1,C1,R2,C2,PL1,PL2):-R2 is R1+1, C2 is C1-1,R2<9,C2<9,R2>0,C2>0,empty_cell(R2,C2,B),not(empty_cell(R1,C1,B,PL1)).
v_m(B,R1,C1,R2,C2,PL1,PL2):-R2 is R1-1, C2 is C1+1,R2<9,C2<9,R2>0,C2>0,empty_cell(R2,C2,B),not(empty_cell(R1,C1,B,PL1)).
v_m(B,R1,C1,R2,C2,PL1,PL2):-R2 is R1-1, C2 is C1-1,R2<9,C2<9,R2>0,C2>0,empty_cell(R2,C2,B),not(empty_cell(R1,C1,B,PL1)).

%All valid bits
v_b(B,R1,C1,R2,C2,R3,C3,PL1,PL2):-R2 is R1+2, C2 is C1+2,R3 is R1+1, C3 is C1+1,R2<9,C2<9,R2>0,C2>0,empty_cell(R2,C2,B),not(empty_cell(R1,C1,B,PL1)),not(empty_cell(R3,C3,B,PL2)).
v_b(B,R1,C1,R2,C2,R3,C3,PL1,PL2):-R2 is R1+2, C2 is C1-2,R3 is R1+1, C3 is C1-1,R2<9,C2<9,R2>0,C2>0,empty_cell(R2,C2,B),not(empty_cell(R1,C1,B,PL1)),not(empty_cell(R3,C3,B,PL2)).
v_b(B,R1,C1,R2,C2,R3,C3,PL1,PL2):-R2 is R1-2, C2 is C1+2,R3 is R1-1, C3 is C1+1,R2<9,C2<9,R2>0,C2>0,empty_cell(R2,C2,B),not(empty_cell(R1,C1,B,PL1)),not(empty_cell(R3,C3,B,PL2)).
v_b(B,R1,C1,R2,C2,R3,C3,PL1,PL2):-R2 is R1-2, C2 is C1-2,R3 is R1-1, C3 is C1-1,R2<9,C2<9,R2>0,C2>0,empty_cell(R2,C2,B),not(empty_cell(R1,C1,B,PL1)),not(empty_cell(R3,C3,B,PL2)).

%Generates all possible moves
generate_move([[p,R1,C1]|Y],PL2,B):-brd(B,PL1,PL2),
                                    findall((R1,C1,R2,C2),v_m(B,R1,C1,R2,C2,PL1,PL2),VM),
                                    findall((R1,C1,R2,C2,R3,C3),v_b(B,R1,C1,R2,C2,R3,C3,PL1,PL2),VB),
%                                    write(VM),
%                                    write(VB),
%                                    nl,
                                    analyze_move(VM,VB),
                                    generate_move(Y,PL2,B),!.
generate_move(_,_,_).
                               
%Calculate cost function
gen_move_lst([[p,R1,C1]|Y],PL2,B,[(VM,VB,[p,R1,C1])|L]):-
                                    brd(B,PL1,PL2),                
                                    findall((R1,C1,R2,C2),v_m(B,R1,C1,R2,C2,PL1,PL2),VM),
                                    findall((R1,C1,R2,C2,R3,C3),v_b(B,R1,C1,R2,C2,R3,C3,PL1,PL2),VB),
                                    gen_move_lst(Y,PL2,B,L),!.
gen_move_lst(_,_,_,[]).

max([X|X1],[Y|Y1],Z):- X>Y,Z=[X|X1],!.
max(X,Y,Z):- Z = Y,!.

calc_cost(B,R):-brd(B,PL1,PL2),
                gen_move_lst(PL1,PL2,B,L),
                length(L,R).
                

%Analyze all moves and select best.
analyze_move(A,[(R1,C1,R2,C2,_,_)|Y]):- brd(b,PL1,PL2),
                                        NB =brd(cmp,PL1,PL2), 
                                        assertz(NB),
                                        make_move(([R1,C1] --> [R2,C2]),cmp,PL1,PL2),
                                        calc_cost(cmp,REZ),
                                        retract(brd(cmp,_,_)),

                                          retract(move_cost(Old_cost)),
                                          max([REZ,R1,C1,R2,C2],Old_cost,New_cost),
                                          NMV = move_cost(New_cost),
                                          assertz(NMV),
                                        analyze_move(A,Y),!.

analyze_move([(R1,C1,R2,C2)|Y],[]):- brd(b,PL1,PL2),
                                     NB = brd(cmp,PL1,PL2),
                                     assertz(NB),
                                     make_move(([R1,C1] --> [R2,C2]),cmp,PL1,PL2),
                                     calc_cost(cmp,REZ),
                                     retract(brd(cmp,_,_)),

                                     retract(move_cost(Old_cost)),
                                     max([REZ,R1,C1,R2,C2],Old_cost,New_cost),
                                     NMV = move_cost(New_cost),
                                    assertz(NMV),
                                    analyze_move(Y,[]),!.
analyze_move([],[]).

main. :- begin.
%main. :- consult('Lab4_AI.pl').