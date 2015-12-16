#ifndef EXISITINGQUERIESWIDGET_H
#define EXISITINGQUERIESWIDGET_H

#include "QueryDAO.h"
#include "SimpleQueriesGenerator.h"

#include <QComboBox>
#include <QLabel>
#include <QObject>
#include <QPushButton>
#include <QTextBrowser>
#include <QVBoxLayout>
#include <QWidget>

class ExisitingQueriesWidget : public QWidget
{
    Q_OBJECT
    public:
        explicit ExisitingQueriesWidget(Database *db, QWidget *parent = 0);

    signals:

    public slots:
        void MakeQuery();

    private :
        QueryDAO *dao;
        QStringList queriesList;
        QVBoxLayout *widgetLayout;
        QComboBox *generatorQueries;
        SimpleQueriesGenerator *generator;
        QPushButton *resultLabel;

        QTextBrowser *resultBrowser;


};

#endif // EXISITINGQUERIESWIDGET_H
