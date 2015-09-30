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

(defun func(vert lst i)
(cond 
((null lst) nil)
(if (and (eql (car lst) 0) (eql vert i) ) (T)
(func vert (cdr lst) (+0 i)) 
)
)
)

;hamilton cycle (vertexlist, edgelist);

(defun hamiltoncycle (vertexlist edgelist vertexquantity vertexcount chosenvertex visited nextvertex)

cond (	(if ( eql (vertexquantity vertexcount) 
			(func chosenvertex nextvertex 0) (return nil)
			)
		)	
		
	(setq counter ( 0 ) ) 

	(loop for counter from 1 to vertexquantity)         ; ��������-�������-����
		(if (and not (member (counter visited)) (member ((chosenvertex counter) edgelist))) ; edgelist[counter] - ? mb digits;
			(func chosenvertex nextvertex counter)
			(push counter(cdr visited))
			
			(if (hamilton (vertexlist edgelist vertexquantity (+ vertexcount 1) ) t)
				pop counter visited )                   
	(setq ������� (1+ �������))))          ; ���������, ����������
	)

)

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