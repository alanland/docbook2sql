package com.github.alanland.docbook2sql.gen

/**
 * ＠author 王成义 
 * @created 2015-02-11.
 */
abstract class Generator {
    String sql

    abstract String generateSql()

    abstract void writeToDisk(String path)

    String genSql() {
        sql = generateSql()
        sql
    }
}
