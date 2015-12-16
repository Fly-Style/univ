#include "ExisitingQueriesWidget.h"

ExisitingQueriesWidget::ExisitingQueriesWidget(Database *db,QWidget *parent) : QWidget(parent)
{
    dao = new QueryDAO(db);
    widgetLayout = new QVBoxLayout;

    generatorQueries = new QComboBox;

    generator = new SimpleQueriesGenerator();

    queriesList.push_back("1");
    queriesList.push_back("2");
    queriesList.push_back("3");
    queriesList.push_back("4");
    queriesList.push_back("5");

    generatorQueries = new QComboBox();
    generatorQueries->addItems(queriesList);

    resultBrowser = new QTextBrowser();
    resultLabel = new QPushButton("MakeQuery");

    widgetLayout->addWidget(generatorQueries);
    widgetLayout->addWidget(resultBrowser);
    widgetLayout->addWidget(resultLabel);

    setLayout(widgetLayout);

    connect(resultLabel, SIGNAL(clicked()), this, SLOT(MakeQuery()));
}

void ExisitingQueriesWidget::MakeQuery()
{
    QString res = queriesList[generatorQueries->currentIndex()-1];
    int i = res.toInt();
    qDebug() << i;

    res = generator->queries[i];
    qDebug() << res;
    Query *q = new Query(res, QueryType::reads);
    QString result = dao->MakeQuery(q);
    resultBrowser->setText(result);

}

