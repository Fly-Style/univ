#include "mainwindow.h"
#include "/Users/flystyle/Documents/Databases/db_lab/DB_Lab/ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent) : QMainWindow(parent), ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    dao = new Database("postgres", "flystyle", "password", 5432, "localhost");
    qDebug() << dao->db.isOpen();

    mainLayout = new QVBoxLayout();//this);
    tabWindow = new QTabWidget;

    tabWindow->addTab(new SimpleQueryWidget(dao), "Simple Query");
    tabWindow->addTab(new ExisitingQueriesWidget(dao), "Existing Queries");
    tabWindow->addTab(new CustomQueryWidget(dao), "Custom Query");
    mainLayout->addWidget(tabWindow);


    ui->centralWidget->resize(1200, 900);
    ui->centralWidget->setLayout(mainLayout);
}

MainWindow::~MainWindow()
{
    delete ui;
}
