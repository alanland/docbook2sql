package com.github.alanland.docbook2sql.gen

import com.github.alanland.docbook2sql.design.TableDesign

/**
 * ＠author 王成义 
 * @created 2015-02-11.
 */
abstract class TableGenerator extends Generator {
    TableDesign tableDesign
    int maxColumnNameLength

    TableGenerator(TableDesign tableDesign) {
        this.tableDesign = tableDesign
    }

    String setMaxColumnSize() {
        this.maxColumnNameLength = tableDesign.columnDefMap.values().max({ it.columnName.length() }).columnName.length()
    }

    @Override
    String genSql() {
        setMaxColumnSize()
        sql = generateSql()
        sql
    }

    @Override
    void writeToDisk(String path) {
        println genSql()

    }
}
