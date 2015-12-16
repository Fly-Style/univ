#include "Query.h"

Query::Query(QVector<QString> text, QString tableName, QueryType flag)
{
    simpleGenerator = new QueryGenerator();
    this->QueryText = simpleGenerator->Generate(flag, tableName, text);
    this->type = flag;
}

Query::Query(QString text, QueryType flag)
{
    simpleGenerator = new QueryGenerator();
    this->QueryText = text;
    this->type = flag;

}

QString Query::getQuery()
{
    return QueryText;
}

QueryType Query::getType()
{
    return type;
}
