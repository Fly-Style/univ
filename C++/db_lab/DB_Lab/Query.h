#include <QtCore>
#include <QWidget>
#include <QString>
#include <QtSQL>
#include <QPair>
#include "QueryGenerator.h"

using namespace std;


class Query : QWidget {
	Q_OBJECT

    private :
        QueryGenerator *simpleGenerator;
		QString QueryText;
        QueryType type;

	public :
        explicit Query () {simpleGenerator = new QueryGenerator(); }
        Query(QVector <QString> text, QString tableName, QueryType flag);
        Query(QString text, QueryType flag);

        QString getQuery();
        QueryType getType();

};
