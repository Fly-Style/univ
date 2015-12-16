#include "QueryDAO.h"

QueryDAO::QueryDAO()
{
     this->simpleQuery = new QSqlQuery(db->db);
    //this->bigQuery = new QSqlQuery(db);
}

QueryDAO::QueryDAO(Database *db)
{
    this->db = db;
    this->simpleQuery = new QSqlQuery(db->db);
    qDebug() << db->db.open();
}


QString QueryDAO::MakeQuery(Query *query)
{
    QString result;
    simpleQuery->exec(query->getQuery());
    if (query->getType() == QueryType::reads)
    {
        while(simpleQuery->next())
        {
            QString name = simpleQuery->value(0).toString();
            result += name + ", ";
        }
        return result;
    }
    result = QString("Query done");
    return result;
}

QList<QString> QueryDAO::MakeQueryV(Query *query)
{
    QList<QString> result;
    simpleQuery->exec(query->getQuery());
    if (query->getType() == QueryType::reads)
    {
        while(simpleQuery->next())
        {
            QString name = simpleQuery->value(0).toString();
            result.push_back(QString(name));
        }
        return result;
    }
    result.clear();
    result.push_front("null");
    return result;
}

bool QueryDAO::CheckCorrectQuery(Query *q)
{
    int i = 0;
    QString str = q->getQuery();
    for (auto j = 0; j < str.size(); i++)
    {
        if (str[j] == '(')
            i++;
        if (str[j] == ')')
            i--;
    }
    qDebug() << i;
    return (i == 0);
}

