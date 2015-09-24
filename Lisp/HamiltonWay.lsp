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

(setq next (nth chosenvertex))

(setq pathlist (list (aref vertexlist chosenvertex)))


)

(let ((x (car path)) (ypos))
(loop 
(if (eql path nil) (return))
	(setq vydranyj (remove pathlist (position y pathlist)))

(if (not (member vydranyj path)))
	(push vydranyj (cdr path))

	(unless (member vydranyj pathlist) )
	pop x path)
)
	
)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(defun NEIGHBOUR3 (lambda (x graph)
(cond 
( (null (assoc x graph)) nil )
( t (cdr (assoc x graph)) )
)
))

(defun hc (lambda (graph start visited sons)
; START - ������ ������� ����� ;
; VISITED - ������ ���������� ������ ;
; SONS - ������ ��������������� ������� ;

(cond 
( (null sons) nil )
( T 
(cond ( (and (member start sons)
(eq (length graph) (length visited))
)
(reverse visited)
)
( t (cond 
(( member (car sons) visited)
(hc graph start visited (cdr sons)) )
( t (or (hc graph start
(cons
(car sons) visited)
(NEIGHBOUR3 (car sons) graph)
)
(hc graph start visited (cdr sons))) 
)) )
)
)
)
))

(defun hamilton (lambda (GRAPH)
; GRAPH - ���� � ���� ��������� ��������� ;
; ���������: ����������� ���� � ���� ������ ������, ;
; NIL - ���� ������������ ����� �� ���������� ;
(cond 
( (null graph) nil )
( t 
(cond 
( (null (cdr graph))
(list (caar graph)))
( t (hc graph (caar graph)
(list (caar graph)) (cdar graph)))))
)
))

( print (linkedgraph '(v1 v2 v3 v4) '((v1 v2) (v2 v3) (v3 v4) ) ) )
 
(print (hamilton '((1 . (2 6)) (2 . (1 3 4)) (3 . (2 4)) (4 . (2 3 5)) (5 . (4 6)) (6 . (1 5))))
				  (1 2 3 4 5 6)
)
;( print (hamiltoncycle `(v1 v2 v3 v4) `((v1 v2) (v2 v3) (v3 v4) (v1 v4)) `(v1) )