#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QWidget>
#include <QTabWidget>
#include "SimpleQueryWidget.h"
#include "CustomQueryWidget.h"
#include "ExisitingQueriesWidget.h"

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();

private:
    Ui::MainWindow *ui;
    Database *dao;
    QVBoxLayout *mainLayout;

    QTabWidget *tabWindow;
};

#endif // MAINWINDOW_H
