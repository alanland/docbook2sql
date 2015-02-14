package com.github.alanland.docbook2sql.gen

import com.github.alanland.docbook2sql.design.ColumnDef
import com.github.alanland.docbook2sql.design.TableDesign

/**
 * ＠author 王成义 
 * @created 2015-02-11.
 */
class MysqlTableGenerator extends TableGenerator {
    MysqlTableGenerator(TableDesign tableDesign) {
        super(tableDesign)
    }

    @Override
    String generateSql() {
        def cols = []
        tableDesign.columnDefMap.values().each {
            cols.add(generatorCol(it))
        }
        """

CREATE TABLE `$tableDesign.tableName` (
${cols.join(',\n')}
);
"""
    }

    @Override
    void writeToDisk(String path) {
        File file = new File(path)
        if (file.exists())
            file.delete()
        file.createNewFile()
        file.append(generateSql())
    }

    String generatorCol(ColumnDef columnDef) {
        String formatName = String.format("%${maxColumnNameLength}s", columnDef.columnName)
        StringBuilder res = new StringBuilder(" $formatName")
        res.append(getColumnTypeSql(columnDef))
        if (columnDef.length)
            res.append("($columnDef.length)")
        if (columnDef.notNull)
            res.append(" NOT NULL")
        if (columnDef.defaultValue != null)
            res.append(" DEFAULT '$columnDef.defaultValue'")
        res.toString()
    }

    String getColumnTypeSql(ColumnDef columnDef) {
        switch (columnDef.typeName) {
            case ['varchar']:
                " varchar"
                break
            case ['int']:
                " int"
                break
            case ['number']:
                ' numeric'
                break
            case ['date']:
                ' date'
                break
            case ['datetime']:
                ' datetime'
                break
            default:
                " $columnDef.typeName"
        }
    }
}
