#ifndef SIMLPEQUERYWIDGET_H
#define SIMLPEQUERYWIDGET_H

#include "QueryDAO.h"
#include <QObject>
#include <QWidget>
#include <QVBoxLayout>
#include <QTextBrowser>
#include <QLabel>
#include <QPushButton>
#include <QLineEdit>

class SimpleQueryWidget : public QWidget
{
    Q_OBJECT

    private :
        QueryDAO *dao;
        QList <QString> tablesList;
        QVector <QList <QString> > columnsList;
        QVBoxLayout *widgetLayout;

        QueryGenerator *generator;

        QLabel *resultLabel, *beginLabel, *equalsLabel;
        QComboBox *tableBox, *columnsBox, *queryTypesBox;

        QLineEdit *chosenContent, *location, *whereEq1, *whereEq2;

        QPushButton *buttonOK, *buttonExit;

        QTextBrowser *textBrowser;

        const QString dbLab = "db_lab";



    public:
        explicit SimpleQueryWidget(Database *db, QWidget *parent = 0);

        void getAllTables();
        void getAllColumns(QString tableName);
        void InitTableBox ();
        void LoadColumnBox (int index);
        QueryType ParseQueryTypeBox ();

    signals:

    public slots:
        void loadCB(int index);
        void MakeQuery();

};

#endif // SIMLPEQUERYWIDGET_H
