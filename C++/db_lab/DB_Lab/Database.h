#ifndef DATABASE_H
#define DATABASE_H

#include <QObject>
#include <QSqlDatabase>
#include <QSqlError>
#include <QDebug>
#include "Query.h"

class Database : public QObject
{
    Q_OBJECT
public:
    explicit Database(QObject *parent = 0);
    Database(QString dbName,  QString userName,
                        QString password, int port, QString hostType);

    QSqlDatabase db;

signals:

private:

};

#endif // DATABASE_H
