#include "SimpleQueryWidget.h"

SimpleQueryWidget::SimpleQueryWidget(Database *db, QWidget *parent) : QWidget(parent)
{
    dao = new QueryDAO(db);
    generator = new QueryGenerator();
    getAllTables();
    for(auto i = 0; i < tablesList.size(); i++)
    {
        getAllColumns(tablesList[i]);
    }

    QStringList typesList;
    typesList.push_back("INSERT");
    typesList.push_back("SELECT");
    typesList.push_back("UPDATE");
    typesList.push_back("DELETE");

    ////


    /////

    widgetLayout = new QVBoxLayout;

    beginLabel  = new QLabel("Input data for your Query");
    resultLabel = new QLabel("Result of your Query");
    equalsLabel = new QLabel(" = ");

    tableBox   = new QComboBox();
    columnsBox = new QComboBox();



    chosenContent = new QLineEdit();
    location      = new QLineEdit();
    location->setText("FROM");
    whereEq1      = new QLineEdit();
    whereEq2      = new QLineEdit();


    buttonOK   = new QPushButton("Make");
    buttonExit = new QPushButton("Exit");

    queryTypesBox = new QComboBox;
    queryTypesBox->addItems(typesList);

    textBrowser = new QTextBrowser;

    InitTableBox();

    widgetLayout->addWidget(beginLabel);
    widgetLayout->addWidget(queryTypesBox);
    widgetLayout->addWidget(tableBox);
    widgetLayout->addWidget(columnsBox);
//    widgetLayout->addWidget(chosenContent);
//    widgetLayout->addWidget(location);
//    widgetLayout->addWidget(whereEq1);
//    widgetLayout->addWidget(equalsLabel);
    widgetLayout->addWidget(resultLabel);
    widgetLayout->addWidget(textBrowser);
    widgetLayout->addWidget(buttonOK);
    widgetLayout->addWidget(buttonExit);


    connect(tableBox, SIGNAL(currentIndexChanged(int)), this, SLOT(loadCB(int)));
    connect(buttonOK, SIGNAL(clicked()), this, SLOT (MakeQuery()));
    connect(buttonExit, SIGNAL(clicked()), this, SLOT(close()));

    setLayout(widgetLayout);

}

void SimpleQueryWidget::getAllTables()
{
    QString getter = QString("SELECT DISTINCT information_schema.tables.table_name FROM information_schema.tables WHERE table_schema = 'db_lab' ");
    Query *getterQuery = new Query (getter, QueryType::reads);
    tablesList = dao->MakeQueryV(getterQuery);
}

void SimpleQueryWidget::getAllColumns(QString tableName)
{
    QString getter = QString ("SELECT DISTINCT information_schema.columns.column_name FROM information_schema.columns ");
    getter.append(QString("WHERE (table_schema = 'db_lab' AND table_name = '"));
    getter.append(tableName);
    getter.append(QString("')"));
    Query *getterQuery = new Query (getter, QueryType::reads);
    columnsList.push_back(dao->MakeQueryV(getterQuery));
}

void SimpleQueryWidget::InitTableBox()
{
    qDebug() << tablesList.size();
    QStringList temp;
    qDebug() << "here";
    for(auto i = 0; i < tablesList.size(); i++) {
        temp.push_back(tablesList[i]);
    }
    tableBox->addItems(temp);
}

void SimpleQueryWidget::LoadColumnBox(int index)
{
    columnsBox->clear();
    QStringList temp;
    for(auto i = 0; i < columnsList[index].size(); i++)
        temp.push_back(columnsList[index][i]);

    columnsBox->addItems(temp);
}

QueryType SimpleQueryWidget::ParseQueryTypeBox()
{
    if (queryTypesBox->currentText() == "SELECT")
        return QueryType::reads;
    if (queryTypesBox->currentText() == "INSERT")
        return QueryType::create;
    if (queryTypesBox->currentText() == "UPDATE")
        return QueryType::update;
    if (queryTypesBox->currentText() == "DELETE")
        return QueryType::del;
    return QueryType::err;
}

void SimpleQueryWidget::loadCB(int index)
{
    LoadColumnBox(index);
}

void SimpleQueryWidget::MakeQuery()
{
    QString a = dbLab;
    QString b = dbLab;
    QVector<QString> qStr;

    // SELECT

    qStr.push_back(a.append(".").append(tableBox->currentText().append(".").append(columnsBox->currentText())));

    Query *query = new Query(qStr, b.append(".").append(tableBox->currentText()), ParseQueryTypeBox());
    for (int i = 0; i < qStr.size(); i++)
        qDebug() << qStr[i];

    QString answer = dao->MakeQuery(query);
    textBrowser->setText(answer);
}
