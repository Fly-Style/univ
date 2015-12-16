#include "SimpleQueriesGenerator.h"

SimpleQueriesGenerator::SimpleQueriesGenerator(QObject *parent) : QObject(parent)
{
    // 1
    queries.push_back(QString("SELECT DISTINCT db_lab.loot.loot_id FROM db_lab.loot INNER JOIN db_lab.quests ON db_lab.quests.loot_id = db_lab.loot.loot_id WHERE NOT EXISTS ( SELECT db_lab.quests.loot_id FROM db_lab.quests  WHERE (db_lab.quests.name = 'destroy') )"));
    // 2
    queries.push_back(QString("SELECT db_lab.backpack.item_id FROM db_lab.backpack INNER JOIN db_lab.item ON db_lab.item.item_id = db_lab.backpack.item_id WHERE (db_lab.item.item_id > 3 AND db_lab.item.rarity = 'legendary')"));
    // 3
    queries.push_back(QString("SELECT db_lab.quests.name FROM db_lab.quests WHERE ( db_lab.quests.prise = (SELECT DISTINCT db_lab.quests.prise FROM db_lab.quests WHERE db_lab.quests.name = 'Archemond Devour'))"));
    // 4
    queries.push_back(QString("SELECT DISTINCT db_lab.item.item_id FROM db_lab.item INNER JOIN db_lab.loot AS L ON L.item_id = item.item_id WHERE (L.item_id = item.item_id)"));
    // 5
    queries.push_back(QString("SELECT db_lab.player.nick FROM  db_lab.player INNER JOIN db_lab.backpack ON db_lab.backpack.b_id = db_lab.player.b_id INNER JOIN db_lab.item ON db_lab.item.item_id = db_lab.backpack.item_id GROUP BY (db_lab.player.nick) HAVING COUNT(item.item_id) > 1 "));

}

