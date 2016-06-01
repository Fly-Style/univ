package main

import (
	"fmt"
	"math/rand"
	"time"
)

type Matrix struct {
	size int
	data []int
}

func newMatrix(size int) *Matrix{
	m := new(Matrix)
	m.size = size
	m.data = make([]int, size*size)
	return m
}

func (mat *Matrix) fill() {
	for i := 0; i < mat.size; i++ {
		for j := 0; j < mat.size; j++ {
			mat.data[i*mat.size + j] = rand.Intn(100)
		}
	}
}

func (m *Matrix) multSequential(m2 *Matrix) (r *Matrix) {
	r = newMatrix(m.size)

	for i := 0; i < m.size; i++ {
		for j := 0; j < m.size; j++ {

			r.data[i*m.size + j] = 0
			for k := 0; k < m.size; k++ {
				r.data[i*m.size + j] += m.data[i*m.size + k] * m2.data[k*m.size + j]
			}
		}
	}

	return
}

func (m *Matrix) multParallel(m2 *Matrix) (r *Matrix) {
	r = newMatrix(m.size)

	done := make(chan bool, m.size)

	for i := 0; i < m.size; i++ {
		go func(i int) {
			for j := 0; j < m.size; j++ {
				r.data[i*m.size + j] = 0
				for k := 0; k < m.size; k++ {
					r.data[i*m.size + j] += m.data[i*m.size + k] * m2.data[k*m.size + j]
				}
			}
			done <- true
		}(i)
	}

	for i := 0; i < m.size; i++{
		<- done
	}

	return
}

func (m *Matrix) print() {
	for i := 0; i < m.size; i++ {
		for j := 0; j < m.size; j++ {
			fmt.Printf("%d ", m.data[i*m.size + j])
		}
		fmt.Println()
	}
}

func main() {

	rand.Seed(100)

	size := 1000

	m1 := newMatrix(size)
	m2 := newMatrix(size)

	m1.fill()
	m2.fill()

	clock := time.Now()
	_ = m1.multSequential(m2)
	fmt.Println(time.Since(clock))
	// m1.print()
	// fmt.Println()

	// m2.print()
	// fmt.Println()

	// r.print()
	// fmt.Println()

	clock = time.Now()
	_ = m1.multParallel(m2)
	fmt.Println(time.Since(clock))

	// r.print()
	// fmt.Println()
}
