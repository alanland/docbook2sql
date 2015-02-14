package com.github.alanland.docbook2sql.gen

import com.github.alanland.docbook2sql.design.TableDesign

/**
 * ＠author 王成义 
 * @created 2015-02-11.
 */
class PostgresTableGenerator extends TableGenerator {
    PostgresTableGenerator(TableDesign tableDesign) {
        super(tableDesign)
    }

    @Override
    String generateSql() {
        return null
    }

    @Override
    void writeToDisk(String path) {

    }
}
