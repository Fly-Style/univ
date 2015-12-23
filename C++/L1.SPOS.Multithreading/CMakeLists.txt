cmake_minimum_required(VERSION 3.3)
project(L1_SPOS_Multithreading)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES main.cpp subclass/help.cpp subclass/help.h subclass/copy.cpp subclass/copy.h)
add_executable(L1_SPOS_Multithreading ${SOURCE_FILES})