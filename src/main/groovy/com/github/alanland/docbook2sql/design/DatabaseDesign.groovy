package com.github.alanland.docbook2sql.design

/**
 * ＠author 王成义 
 * @created 2015-02-11.
 */
class DatabaseDesign {
    CommonDesign commonDesign
    Map<String, ModuleDesign> moduleDesignMap = [:]

    void addModule(ModuleDesign moduleDesign) {
        moduleDesignMap.put(moduleDesign.moduleName, moduleDesign)
    }
}
