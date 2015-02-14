package com.github.alanland.docbook2sql.gen

import com.github.alanland.docbook2sql.DatabaseType
import com.github.alanland.docbook2sql.design.DatabaseDesign

/**
 * ＠author 王成义 
 * @created 2015-02-11.
 */
class DatabaseGenerator extends Generator {

    DatabaseDesign databaseDesign
    Map<String, ModuleGenerator> moduleGeneratorMap = [:]

    DatabaseGenerator(DatabaseDesign databaseDesign) {
        this.databaseDesign = databaseDesign
        databaseDesign.moduleDesignMap.values().each {
            def moduleGen = new ModuleGenerator(it, DatabaseType.MYSQL)
            moduleGeneratorMap.put(moduleGen.moduleDesign.moduleName, moduleGen)
        }
    }

    void addModuleGenerator(ModuleGenerator moduleGenerator) {
        moduleGeneratorMap.put(moduleGenerator.moduleDesign.moduleName, moduleGenerator)
    }

    @Override
    String generateSql() {
        def sqls = []
        moduleGeneratorMap.values().each {
            sqls.add(it.generateSql())
        }
        sqls.join('\n\n\n')
    }

    @Override
    void writeToDisk(String path) {
        File file = new File(path)
        if (file.exists())
            file.deleteDir()
        file.mkdirs()
        def allFile = new File(file, 'all.sql')
        allFile.createNewFile()
        allFile.append(genSql())

        moduleGeneratorMap.values().each {
            it.writeToDisk(new File(path, it.moduleDesign.moduleName).absolutePath)
        }
    }
}
