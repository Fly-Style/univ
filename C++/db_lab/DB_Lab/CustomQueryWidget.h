#ifndef CUSTOMQUERYWIDGET_H
#define CUSTOMQUERYWIDGET_H

#include <QLabel>
#include <QObject>
#include <QWidget>
#include <QTextEdit>
#include <QTextBrowser>
#include <QPushButton>
#include <QVBoxLayout>
#include "QueryDAO.h"

class CustomQueryWidget : public QWidget
{
    Q_OBJECT

    private:
        QueryDAO *dao;

        QVBoxLayout *widgetLayout;

        QLabel *input, *output;
        QPushButton *buttonOK, *buttonCancel;

    public:
        QTextEdit *simpleQuery;
        QTextBrowser *simpleQueryResult;
        explicit CustomQueryWidget(Database *db, QWidget *parent = 0);
        QueryType ParseText(QString str);

    signals:

    public slots:
        QString SetQuery(QTextEdit *textEdit);
        void StayQuery();
};

#endif // CUSTOMQUERYWIDGET_H
