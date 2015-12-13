; row_col - quantity of rows and columns (square matrix)
(setq row_col 0)

;(setq input (open " D:/users/MakeYouHappy/univ/Lisp/matrix.txt" :direction : input))
(setq input (open "/Users/flystyle/Documents/Lisp & Prolog/matrix.txt" :direction :input))
(setf row_col (read input))

; matrix - main object

(setq matrix (make-array (list row_col row_col) :element-type 'integer :initial-element 0))

(setf matrix (read input))

; take free members;

(setq B (make-array row_col :element-type 'integer :initial-element 0))

; take our matrix;

(setq B (read input))

(close input)

(defun method_gaus (matrix array_b r_c)

; declare our iterators;

(declare (special i)) ; iterator i

(declare (special j)) ; iterator j

; make our matrixes and arrays dynamic;

(declare (special A))

(declare (special B))

(declare (special X))

; temporary vars

(declare (special numb))

; A - temporary matrix;

(setq A (make-array (list r_c r_c) :element-type 'integer :initial-element 0))

(setf A matrix)

; ?- free members matrix;

(setq B (make-array r_c :element-type 'integer :initial-element 0))

(setf B array_b)

; x - array of solutions;

(setq X (make-array r_c :element-type 'integer :initial-element 0))

; we changes rows;

(do ( (p 0))
	( (>= p ( - r_c 1)))
	
	(do ( (i (+ p 1)))
		( (>= i r_c))		
		(setq numb (/ (aref A i p) (aref A p p)))

			(do ( (j p))
				( (>= j r_c))
				(setf (aref A i j) ( - (aref A i j) (* (aref A p j) numb)))
				(	setq j (+ j 1))
			)

	(setf (aref B 0 i) ( - (aref B 0 i) (* (aref B 0 p) numb)))

	(setq i (+ i 1))
	)

	(setq p (+ p 1))
)

(setf (aref X ( - r_c 1)) (float (/ (aref B 0 ( - r_c 1)) (aref A ( - r_c 1) ( - r_c 1)))))

; get a stairs matrix;

; begin getting x;

(do ( (i ( - r_c 2)))
	( (< i 0))
	(setq numb 0)

	(do( (j (+ i 1)))
		( (>= j r_c) x)
		(setq numb (+ numb (* (aref a i j) (aref x j))))
		(setq j (+ j 1))
	)

	(setf (aref x i) (float (/ ( - (aref B 0 i) numb) (aref A i i))))
	(setq i ( - i 1))
)
X
)

; for matrix b we start gauss method;

(method_gaus matrix B row_col)

; printing result;

(defun print_res (matr_x len output)

(do( (i 0))
	( (>= i len))
	(print (list 'x i '= (aref matr_x i)) output)
	(setq i (+ i 1))
)
)

; output the array into the file;

(setq output (open "/Users/flystyle/Documents/Lisp & Prolog/result.txt" :direction :output))
;(setq output (open " D:/users/MakeYouHappy/univ/Lisp/result.txt": direction: output))

(print_res x row_col output)

(terpri output)

(close output)