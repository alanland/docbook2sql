package com.github.alanland.docbook2sql.gen

import com.github.alanland.docbook2sql.DatabaseType
import com.github.alanland.docbook2sql.design.ModuleDesign

/**
 * ＠author 王成义 
 * @created 2015-02-11.
 */
class ModuleGenerator extends Generator {
    String dbType
    ModuleDesign moduleDesign
    Map<String, TableGenerator> tableGeneratorMap = [:]

    ModuleGenerator(ModuleDesign moduleDesign, String dbType) {
        this.moduleDesign = moduleDesign
        this.dbType = dbType
        moduleDesign.tableDesignMap.values().each {
            def tableGen
            switch (dbType) {
                case DatabaseType.MYSQL:
                    tableGen = new MysqlTableGenerator(it)
                    break
                case DatabaseType.POSTGRES:
                    tableGen = new PostgresTableGenerator(it)
                    break
                default:
                    throw new Error("not implemented database type: $dbType")
            }
            if (tableGen)
                tableGeneratorMap.put(tableGen.tableDesign.tableName, tableGen)
        }
    }

    @Override
    String generateSql() {
        def sqls = []
        tableGeneratorMap.values().each {
            sqls.add(it.genSql())
        }
        sqls.join('\n\n')
    }

    @Override
    void writeToDisk(String path) {
        File file = new File(path)
        if (file.exists())
            file.delete()
        file.mkdirs()

        def allFile = new File(file, "index_${moduleDesign.moduleName}.sql")
        allFile.createNewFile()
        allFile.append(generateSql())

        tableGeneratorMap.values().each {
            it.writeToDisk(new File(path, it.tableDesign.tableName).absolutePath + ".sql")
        }
    }
}
