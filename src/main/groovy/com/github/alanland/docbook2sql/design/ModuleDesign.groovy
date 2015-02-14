package com.github.alanland.docbook2sql.design

/**
 * ＠author 王成义 
 * @created 2015-02-11.
 */
class ModuleDesign {
    String moduleName
    Map<String, TableDesign> tableDesignMap = [:]

    void addTableDesign(TableDesign tableDesign) {
        tableDesignMap.put(tableDesign.tableName, tableDesign)
    }
}
