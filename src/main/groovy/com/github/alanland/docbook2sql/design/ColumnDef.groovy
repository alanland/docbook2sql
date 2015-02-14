package com.github.alanland.docbook2sql.design

import java.sql.Types

/**
 * ＠author 王成义 
 * @created 2015-02-11.
 */
class ColumnDef {
    String columnName
    String remarks
    String typeName
    String length
    String dataType
    boolean notNull
    Object defaultValue

    static Map name2Type = [
            'varchar' : Types.VARCHAR,
            'datetime': Types.TIMESTAMP,
            'date'    : Types.DATE,
            'integer' : Types.INTEGER
    ]

    void setTypeName(String typeName) {
        if (!typeName)
            return
        this.typeName = typeName
        this.dataType = name2Type.get(typeName.toLowerCase())
    }

    void setNotNull(String notNull){
        this.notNull = notNull && notNull.trim().equalsIgnoreCase('y')
    }
}
