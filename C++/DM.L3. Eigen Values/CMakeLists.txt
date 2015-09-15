cmake_minimum_required(VERSION 3.2)
project(DM_L3__Eigen_Values)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES main.cpp)
add_executable(DM_L3__Eigen_Values ${SOURCE_FILES})