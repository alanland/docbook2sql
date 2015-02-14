package com.github.alanland.docbook2sql.design

/**
 * ＠author 王成义 
 * @created 2015-02-11.
 */
class TableDesign {
    String tableName
    String remarks
    Map<String, ColumnDef> columnDefMap = [:]
    Map<String, IndexDef> indexDefMap = [:]

    void addColumnDef(ColumnDef columnDef){
        columnDefMap.putAt(columnDef.columnName, columnDef)
    }
}
