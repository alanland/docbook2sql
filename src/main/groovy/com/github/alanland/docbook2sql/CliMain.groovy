package com.github.alanland.docbook2sql

import com.github.alanland.docbook2sql.design.DatabaseDesign
import com.github.alanland.docbook2sql.design.DatabaseDesignParser
import com.github.alanland.docbook2sql.gen.DatabaseGenerator;

/**
 * ＠author 王成义
 *
 * @created 2015-02-11.
 */
public class CliMain {
    // todo 参数
    static databaseTypes = ['mysql','postgres']

    static void main(String args){
        String designDir = args[0]
        String sqlDir = args[1]
        DatabaseDesign databaseDesign = DatabaseDesignParser.parseDatabaseDesignFolder(designDir)
        println databaseDesign

    }

    static DatabaseGenerator getDatabaseGenerator(DatabaseDesign design,String type, String out){
        DatabaseGenerator db = new DatabaseGenerator(target: out)
    }
}
