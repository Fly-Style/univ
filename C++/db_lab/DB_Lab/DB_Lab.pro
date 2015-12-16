#-------------------------------------------------
#
# Project created by QtCreator 2015-11-22T19:25:28
#
#-------------------------------------------------

QT       += core gui
QT       += sql
QT       += network

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = DB_Lab
TEMPLATE = app

CONFIG += c++11

SOURCES += main.cpp\
        mainwindow.cpp \
    QueryGenerator.cpp \
    Query.cpp \
    QueryDAO.cpp \
    SimpleQueriesGenerator.cpp \
    CustomQueryWidget.cpp \
    SimpleQueryWidget.cpp \
    Database.cpp \
    ExisitingQueriesWidget.cpp

HEADERS  += mainwindow.h \
    Query.h \
    QueryDAO.h \
    QueryGenerator.h \
    ui_mainwindow.h \
    SimpleQueriesGenerator.h \
    CustomQueryWidget.h \
    SimpleQueryWidget.h \
    Database.h \
    ExisitingQueriesWidget.h

FORMS    += mainwindow.ui

LIBS += -stdlib=libc++

QMAKE_CXXFLAGS += -std=c++11
QMAKE_CXXFLAGS += -mmacosx-version-min=10.10
QMAKE_LFLAGS += -mmacosx-version-min=10.10
