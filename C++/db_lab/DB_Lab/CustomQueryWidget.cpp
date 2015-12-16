#include "CustomQueryWidget.h"

CustomQueryWidget::CustomQueryWidget(Database *db, QWidget *parent) : QWidget(parent)
{
    this->dao = new QueryDAO(db);


    widgetLayout = new QVBoxLayout;

    input  = new QLabel("Input your Query : ");
    output = new QLabel("Result of your Query ");

    buttonOK = new QPushButton("OK");
    buttonCancel = new QPushButton("Cancel");
    
    simpleQuery = new QTextEdit();
    simpleQueryResult = new QTextBrowser();
    
    ////////////////////////////////////////////
    
    input->move(10, 10);
    input->resize(30,10);
    output->move(10, 220);
    output->move(30, 10);

    buttonOK->move(300, 300);
    buttonOK->resize(30,10);

    buttonCancel->move(335, 300);
    buttonCancel->resize(40,10);

    simpleQuery->move(20,10);
    simpleQuery->resize(100, 100);

    simpleQueryResult->move(150,10);
    simpleQueryResult->resize(100, 100);

    widgetLayout->addWidget(input);
    widgetLayout->addWidget(simpleQuery);
    widgetLayout->addWidget(output);
    widgetLayout->addWidget(simpleQueryResult);
    widgetLayout->addWidget(buttonOK);
    widgetLayout->addWidget(buttonCancel);

    this->setLayout(widgetLayout);
    connect(buttonOK, SIGNAL(clicked()), this, SLOT(StayQuery()));
    connect(buttonCancel, SIGNAL(clicked()), this, SLOT(close()));
}

QueryType CustomQueryWidget::ParseText(QString str)
{
    int i = 0;
    while(str[i] != ' '){
        i++;
    }
    QString a = str.left(i);
    if (a == "INSERT")
        return QueryType::create;
    if (a == "SELECT")
        return QueryType::reads;
    if (a == "UPDATE")
        return QueryType::update;
    if (a == "DELETE")
        return QueryType::del;

    return QueryType::err;
}


QString CustomQueryWidget::SetQuery(QTextEdit *textEdit) {
    QString data = textEdit->toPlainText();
    QueryType flag = ParseText(data);
    Query *currentQuery = new Query(data, flag);
    data = dao->MakeQuery(currentQuery);
    if (data == "Query done")
        data = QString("null");
    return data;
}

void CustomQueryWidget::StayQuery() {
    QString res = SetQuery(simpleQuery);
    simpleQueryResult->setText(res);
}

