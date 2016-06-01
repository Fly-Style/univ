TARGET = DC_L8
CONFIG += console
CONFIG -= app_bundle
CONFIG -= qt
CONFIG += c++11

TEMPLATE = app

SOURCES += main.cpp

INCLUDEPATH += /usr/local/Cellar/mpich/3.2/include

LIBS += -L/usr/local/Cellar/mpich/3.2/lib -lmpicxx -lmpi -lpmpi

#QMAKE_CXXFLAGS += -flat_namespace \
#                  -use_dylibs

