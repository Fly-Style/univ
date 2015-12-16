#include <QtCore>
#include <QtSQL>
#include <QSqlDatabase>
#include <QSqlQuery>
#include "Database.h"

#ifndef QUERYDAO
#define QUERYDAO
class QueryDAO : QWidget
{
    Q_OBJECT
    private :
        Database *db;
        QSqlQuery *simpleQuery;
        QSqlQuery *bigQuery;

        Query *currentQuery;

    public :

        QueryDAO ();
        QueryDAO(Database *db);

        virtual ~QueryDAO() {}
	
        virtual QString MakeQuery(Query *query);
        QList<QString> MakeQueryV(Query *query);
        bool CheckCorrectQuery(Query *q);

        bool isOpen() {
            return db->db.isOpen();
        }
};

#endif
