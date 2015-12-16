#include "QueryGenerator.h"

/* Some notice about the QVector str names.
*  If @QueryType is SELECT : -> names of columns;
*  If @QueryType is INSERT : -> names of ALL values. Only one row can be added.
*  If @QueryType is UPDATE : -> pairs [column=value], last is [some_col = some_val].
*  If @QueryType is DELETE : -> pairs of identifiers;
*/

QString QueryGenerator::Generate(QueryType type, QString tableName, QVector<QString> str)
{
    QString result;

    switch (type) {
        case QueryType::create :
            result += "INSERT INTO " + tableName + "VALUES (";
            for (auto i = 0; i < str.size()-1; i++)
            {
                result += str[i] + ", ";
            }
            result += str[str.size()-1] + ")";

            break;

        case QueryType::reads :
            result += "SELECT ";
            for (auto i = 0; i < str.size() - 1; i++)
            {
                result += str[i] + ",";
            }
            result += str[str.size()-1];
            result += " FROM " + tableName;
            break;

        case QueryType::update :
            result += "UPDATE " + tableName;
            result += " SET ";
            for (auto i = 0; i < str.size()-2; i++)
            {
                result += str[i] + "=" + str[i+1];
                i++;
            }
            result += " WHERE ";
            result += str[str.size()-2] + "=" + str[str.size()-1];
            break;


        case QueryType::del :
            result += "DELETE FROM " + tableName + " WHERE ";
            if (str.contains("AND"))
            {
                result += str[0] + "=" + str[1] + " AND ";
                result += str[2] + "=" + str[3];
            }
            else result += str[0] + "=" + str[1];

            break;

        case QueryType::err :
            result = "FAIL";

        break;
    }
    return result;
}
