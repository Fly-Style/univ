#include "Database.h"

Database::Database(QObject *parent) : QObject(parent)
{
    this->db = QSqlDatabase::addDatabase("QPSQL");
}

Database::Database(QString dbName, QString userName, QString password, int port, QString hostType)
{
    db = QSqlDatabase::addDatabase("QPSQL");
    db.setDatabaseName(dbName);
    db.setUserName(userName);
    db.setPassword(password);
    db.setHostName(hostType);
    db.setPort(port);

    bool opened = db.open();
    if (!opened)
    {
        QSqlError err = this->db.lastError();
        qDebug() << err.databaseText();
    }
    qDebug() << opened;

}

