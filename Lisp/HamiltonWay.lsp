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
; START - первая вершина графа ;
; VISITED - список пройденных вершин ;
; SONS - соседи просматриваемой вершины ;

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
; GRAPH - граф в виде структуры смежности ;
; Результат: гамильтонов цикл в виде списка вершин, ;
; NIL - если гамильтонова цикла не существует ;
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