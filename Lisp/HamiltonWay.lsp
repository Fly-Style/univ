; смежная вершина (первая вершина ребра, вторая вершина ребра,

; список найденных вершин, список новых вершин)

(defun neighbour(x y foundedlist newlist)

(cond

((not (member x newlist)) nil) ;x не является новой вершиной

((member y newlist) nil) ;y является новой вершиной

((member y foundedlist) nil) ;y является ранее найденной вершиной

(t t))) ;вершина является новой смежной вершиной

;поиск смежных вершин (список найденных вершин, список новых вершин, список ребер)

(defun adj(foundedlist newlist edgelist)

(cond

((null edgelist) nil) ;конец поиска

((neighbour (caar edgelist) (cadar edgelist) foundedlist newlist) ;смежная вершина

(cons (cadar edgelist) (adj foundedlist newlist (cdr edgelist)))) ;добавление в список

((neighbour (cadar edgelist) (caar edgelist) foundedlist newlist) ;смежная вершина

(cons (caar edgelist) (adj foundedlist newlist (cdr edgelist)))) ;добавление в список

(t (adj foundedlist newlist (cdr edgelist))))) ;пропуск ребра

;поиск в ширину (список найденных вершин, список новых найденных вершин,

; вершина для поиска, список ребер)

(defun bfs(foundedlist newlist y edgelist)

(cond

((null newlist) nil) ;не найдено ни одной новой вершины

((member y newlist) t) ;вершина найдена

(t (bfs (append foundedlist newlist) (adj foundedlist newlist edgelist) y edgelist)))) ;добавление новых вершин

;поиск пути (первая вершина, вторая вершина, список ребер)

(defun findpath(x y edgelist)

(bfs nil (list x) y edgelist)) ;поиск в ширину

;перебор вершин (первая вершина, список вершин, список ребер)

(defun bust(firstvertex vertexlist edgelist)

(cond

((null vertexlist) t) ;конец перебора

((findpath firstvertex (car vertexlist) edgelist) (bust firstvertex (cdr vertexlist) edgelist)) ;путь найден

(t nil))) ;нет пути

;определение связанности графа(список вершин, список ребер)

(defun linkedgraph(vertexlist edgelist)

(bust (car vertexlist) (cdr vertexlist) edgelist)) ;перебор вершин и поиск пути от первой вершины ко всем остальным

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

	(loop for counter from 1 to vertexquantity)         ; проверка-истинна-ложь
		(if (and not (member (counter visited)) (member ((chosenvertex counter) edgelist))) ; edgelist[counter] - ? mb digits;
			(func chosenvertex nextvertex counter)
			(push counter(cdr visited))
			
			(if (hamilton (vertexlist edgelist vertexquantity (+ vertexcount 1) ) t)
				pop counter visited )                   
	(setq счетчик (1+ счетчик))))          ; инкремент, увеличение
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