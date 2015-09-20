; ������� ������� (������ ������� �����, ������ ������� �����,

; ������ ��������� ������, ������ ����� ������)

(defun neighbour(x y foundedlist newlist)

(cond

((not (member x newlist)) nil) ;x �� �������� ����� ��������

((member y newlist) nil) ;y �������� ����� ��������

((member y foundedlist) nil) ;y �������� ����� ��������� ��������

(t t))) ;������� �������� ����� ������� ��������

;����� ������� ������ (������ ��������� ������, ������ ����� ������, ������ �����)

(defun adj(foundedlist newlist edgelist)

(cond

((null edgelist) nil) ;����� ������

((neighbour (caar edgelist) (cadar edgelist) foundedlist newlist) ;������� �������

(cons (cadar edgelist) (adj foundedlist newlist (cdr edgelist)))) ;���������� � ������

((neighbour (cadar edgelist) (caar edgelist) foundedlist newlist) ;������� �������

(cons (caar edgelist) (adj foundedlist newlist (cdr edgelist)))) ;���������� � ������

(t (adj foundedlist newlist (cdr edgelist))))) ;������� �����

;����� � ������ (������ ��������� ������, ������ ����� ��������� ������,

; ������� ��� ������, ������ �����)

(defun bfs(foundedlist newlist y edgelist)

(cond

((null newlist) nil) ;�� ������� �� ����� ����� �������

((member y newlist) t) ;������� �������

(t (bfs (append foundedlist newlist) (adj foundedlist newlist edgelist) y edgelist)))) ;���������� ����� ������

;����� ���� (������ �������, ������ �������, ������ �����)

(defun findpath(x y edgelist)

(bfs nil (list x) y edgelist)) ;����� � ������

;������� ������ (������ �������, ������ ������, ������ �����)

(defun bust(firstvertex vertexlist edgelist)

(cond

((null vertexlist) t) ;����� ��������

((findpath firstvertex (car vertexlist) edgelist) (bust firstvertex (cdr vertexlist) edgelist)) ;���� ������

(t nil))) ;��� ����

;����������� ����������� �����(������ ������, ������ �����)

(defun linkedgraph(vertexlist edgelist)

(bust (car vertexlist) (cdr vertexlist) edgelist)) ;������� ������ � ����� ���� �� ������ ������� �� ���� ���������

;hamilton cycle (vertexlist, edgelist);

(defun hamiltoncycle (vertexlist edgelist chosenvertex)

(setq path (list chosenvertex) 	; added to a all neighbours [a.push_back(vertexlist[chosenvertex])].
(setq pathlist (list aref vertexlist chosenvertex))

(
)

)

( print (linkedgraph '(v1 v2 v3 v4) '((v1 v2) (v2 v3) (v3 v4) ) ) )
( print (hamiltoncycle `(v1 v2 v3 v4) `((v1 v2) (v2 v3) (v3 v4) ) ) )