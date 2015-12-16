#ifndef SIMPLEQUERIESGENERATOR_H
#define SIMPLEQUERIESGENERATOR_H

#include <QObject>
#include <QVector>

class SimpleQueriesGenerator : public QObject
{
    Q_OBJECT

    private :


    public:
        explicit SimpleQueriesGenerator(QObject *parent = 0);
        QVector <QString> queries;


};

#endif // SIMPLEQUERIESGENERATOR_H
