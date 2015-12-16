#include <QtCore>
#include <QObject>
#include <QString>

enum QueryType {
    create = 0,
    reads   = 1,
    update = 2,
    del = 3,
    err = 99
};

class QueryGenerator : public QObject
{
	Q_OBJECT

	private :

	public : 
		QueryGenerator() {}

        static QString Generate(QueryType type, QString tableName,  QVector <QString> str);

};
