#
#   CREATING OF THE TABLES ;
#

CREATE TABLE public.test_L
(
  CL VARCHAR(3) PRIMARY KEY,
  surname VARCHAR(20),
  org VARCHAR(20),
  phone VARCHAR(12),
  degree VARCHAR(12)
);

CREATE TABLE public.test_S
(
  CS VARCHAR(3) PRIMARY KEY,
  name VARCHAR(20),
  tip VARCHAR(20),
  hours INTEGER,
  ctrl VARCHAR(7)
);

CREATE TABLE public.test_G
(
  CG VARCHAR(3) PRIMARY KEY,
  fac VARCHAR(15),
  caf VARCHAR(7),
  course VARCHAR(1),
  qnt int
);

CREATE TABLE public.test_R
(
  CL VARCHAR(3),
  CG VARCHAR(3),
  CS VARCHAR(3),
  day VARCHAR(10),
  lesson VARCHAR(20),
  room VARCHAR(7)
);

# QUERY TYPE ONE;

SELECT public.test_s.name FROM test_s
INNER JOIN test_r ON test_r.cs = test_s.cs
INNER JOIN test_l ON test_r.cl = test_l.cl
INNER JOIN test_g ON test_r.cg = test_g.cg
WHERE (
        test_l.surname = 'Ivan' AND test_r.room = '205'
      )

#QUERY TYPE TWO;

SSELECT test_l.surname FROM test_l
  INNER JOIN test_r ON test_r.cl = test_l.cl
  INNER JOIN test_s ON test_r.cs = test_s.cs
  INNER JOIN test_g ON test_r.cg = test_g.cg
  WHERE test_g.course = '1' AND test_l.cl NOT IN
  (
    SELECT test_s.cs FROM test_s
      WHERE
      (
        test_s.name = 'math'
      )
  )

  #QUERY TYPE THREE

########  SQL multiple comparisons;

SELECT test_L.surname FROM test_L
  WHERE NOT EXISTS 
  (
      SELECT test_G.CG FROM test_G
        WHERE test_G.course = '1' AND test_G.CG NOT IN
          
      (SELECT test_R.CG FROM test_R
        WHERE test_R.CL = test_L.CL
      )
  )

