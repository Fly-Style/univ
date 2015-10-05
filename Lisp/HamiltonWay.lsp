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

; a[b] = 0;







;hamilton cycle (vertexlist, edgelist);

(defun hamiltoncycle (vertexlist edgelist vertexquantity vertexcount chosenvertex visited nextvertex)
	(if ( eql (vertexquantity vertexcount) )
			(setf (nth chosenvertex nextvertex 0) (return nil)
		)
	)	
		
			(setq counter ( 0 ) ) 

		(loop for counter from 0 to (- vertexquantity 1)
			(if (and not (member (counter visited)) (member ((chosenvertex counter) edgelist))) ; edgelist[counter] - ? mb digits;
				(setf (nth chosenvertex nextvertex) counter)
				(push counter visited)
			
				(if (hamiltoncycle (vertexlist edgelist vertexquantity (+ vertexcount 1) ) t)
					pop counter visited )
			)                   
		)
		(return nil)
)

( print ( hamiltoncycle '(1 2 3) '((1 2) (2 3) (1 3)) 3 1 1 nil 0) )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



( print (linkedgraph '(v1 v2 v3 v4) '((v1 v2) (v2 v3) (v3 v4) ) ) )
 

;( print (hamiltoncycle `(v1 v2 v3 v4) `((v1 v2) (v2 v3) (v3 v4) (v1 v4)) `(v1) )

;
;(setq path (list chosenvertex) 	; added to a all neighbours [a.push_back(vertexlist[chosenvertex])].

;(setq next (nth chosenvertex))

;(setq pathlist (list (aref vertexlist chosenvertex)))


;)

;(let ((x (car path)) (ypos))
;(loop 
;(if (eql path nil) (return))
;	(setq vydranyj (remove pathlist (position y pathlist)))

;(if (not (member vydranyj path)))
;	(push vydranyj (cdr path))

;	(unless (member vydranyj pathlist) )
;	pop x path); )
	
;)