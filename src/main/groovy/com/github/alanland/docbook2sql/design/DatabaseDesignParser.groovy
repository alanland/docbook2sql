package com.github.alanland.docbook2sql.design

import com.github.alanland.docbook2sql.gen.DatabaseGenerator
import org.w3c.dom.Node
import org.w3c.dom.NodeList

import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

/**
 * ＠author 王成义 
 * @created 2015-02-11.
 */
class DatabaseDesignParser {
    def xpath
    static final String SECTION = 'section'
    static final String BOOK = 'book'
    static final String CHAPTER = 'chapter'

    DatabaseDesignParser() {
        xpath = XPathFactory.newInstance().newXPath()
    }

    def getXmlNode(File file) {
        DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file)
    }

    String getRootName(File file) {
        new XmlParser().parseText(file.text).name().localPart
    }

    DatabaseDesign parseDatabaseDesignFolder(String filePath) {
        DatabaseDesign database = new DatabaseDesign()
        new File(filePath).listFiles().each { File file ->
            if (file.isFile() && file.name.endsWith('.xml')) {
                if (getRootName(file) == 'section') {
                    println file.absolutePath
                    database.commonDesign = parseCommonDesignFile(file)
                }
            } else if (file.isDirectory()) {
                println file.absolutePath
                database.addModule(parseModuleDesignFolder(file))
            }
        }
        database
    }


    ModuleDesign parseModuleDesignFolder(File folder) {
        ModuleDesign moduleDesign = new ModuleDesign()
        moduleDesign.setModuleName(folder.name)
        folder.listFiles().each { File file ->
            println file.absolutePath
            if (getRootName(file) == SECTION) {
                def xml = getXmlNode(file)
                NodeList tables = xpath.evaluate('//table', xml, XPathConstants.NODESET) as NodeList
                for (int i = 0; i < tables.length; i++) {
                    moduleDesign.addTableDesign(parseTableDesign(tables.item(i)))
                }
            }
        }
        moduleDesign
    }

    CommonDesign parseCommonDesignFile(File file) {
//        def xpath = XPathFactory.newInstance().newXPath()
//        def xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file)
//
//        NodeList tables = xpath.evaluate('//table', xml, XPathConstants.NODESET) as NodeList
//        for (int i = 0; i < tables.getLength(); i++) {
//            Node table = tables.item(i)
//            parseTableDesign(table)
//        }
//
//        NodeList tbodies = xpath.evaluate('//tbody', xml, XPathConstants.NODESET) as NodeList
//        for (int n = 0; n < tbodies.getLength(); n++) {
//            NodeList rows = tbodies.item(n).getChildNodes()
//            for (int i = 0; null != rows && i < rows.getLength(); i++) {
//                ColumnDef columnDef = parseColumnDef(rows.item(i))
//            }
//        }
        null
    }

    TableDesign parseTableDesign(Node table) {
        TableDesign tableDesign = new TableDesign()
        Node title = xpath.evaluate('//title', table, XPathConstants.NODE) as Node
        String titleValue = title.getChildNodes().item(0).getNodeValue()
        tableDesign.tableName = titleValue

        NodeList rows = xpath.evaluate('//tbody/row', table, XPathConstants.NODESET) as NodeList
        for (int i = 0; null != rows && i < Math.max(1, rows.getLength()); i++) {
            ColumnDef columnDef = parseColumnDef(rows.item(i))
            if (columnDef)
                tableDesign.addColumnDef(columnDef)
        }
        tableDesign
    }

    ColumnDef parseColumnDef(Node row) {
        def attrs = [:]
        if (row.getNodeType() == Node.ELEMENT_NODE) {
            NodeList entries = row.getChildNodes()
            for (int j = 0; null != entries && j < entries.getLength(); j++) {
                Node entry = entries.item(j)
                if (entry.getNodeType() == Node.ELEMENT_NODE && entry.getFirstChild()) {
                    attrs.put(j, entry.getFirstChild().getNodeValue())
                }
            }
        }
        if (!attrs[1] && !attrs[5])
            return null
        def columnDef = new ColumnDef(
                columnName: attrs[1],
                remarks: attrs[3],
                typeName: attrs[5],
                length: attrs[7],
                notNull: attrs[9],
                defaultValue: attrs[11]
        )
        if (!columnDef.remarks)
            columnDef.remarks = columnDef.columnName
        columnDef
    }

    static void main(args) {
        def database = new DatabaseDesignParser().parseDatabaseDesignFolder('/home/journey/day/svn/x-oms/trunk/document/design/test/database')
        def generator = new DatabaseGenerator(database)
        generator.writeToDisk('/home/journey/day/svn/x-oms/trunk/document/design/test/xxxout')
    }
}
