package com.kjms.xmlparser.elements;

import com.kjms.xmlparser.Exception.HandleException;
import com.kjms.xmlparser.Tag;
import com.kjms.xmlparser.util.ClassNameSingleTon;
import com.kjms.xmlparser.util.Util;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class TableWrapFoot implements Tag {

    public static Boolean IMPLEMENT = true;

    public static String ELEMENT_FULL = "<table-wrap-foot>";
    public static String ELEMENT = "table-wrap-foot";

    Node node = null;
    NodeList childNodes = null;

    List<Node> nodeList = new ArrayList<>();

    String html = "";


    public TableWrapFoot(Node node) {
        try {
            this.node = node;

            elementFilter();

            List<String> tagNames = ClassNameSingleTon.getInstance().tagNames;

//            String id = node.getAttributes().getNamedItem("id") != null? node.getAttributes().getNamedItem("id").getNodeValue() : "";

//            this.html += "<div class='bottom-nav-data' data-head='Table' data-name='' order='5' data-id='"+ id +"'>";

            this.html += "<div class='tableWrapFoot'>";

            String tableName = "";

            for (Node node1 : nodeList) {

                if (tagNames.contains(node1.getNodeName())) {

                    String className = ClassNameSingleTon.tagToClassName(node1.getNodeName());
                    if (Boolean.TRUE.equals(ClassNameSingleTon.isImplement(className))) {

                            Object instanceFromClassName = ClassNameSingleTon.createInstanceFromClassName(className, node1);
                            this.html += ClassNameSingleTon.invokeMethod(instanceFromClassName, "element");

                    }
                } else if (!node1.getNodeName().equals("#text")){


                    this.html += "<pre style='color:red'>'''" + Util.convertToString(node1).replace("<","&lt;").replace(">","&gt;") + "'''</pre>";
                }

            }

//            this.html += tableName;

            this.html += "</div>";

//            this.html = this.html.replace("data-name=''", "data-name='" + tableName.split("-")[0].replace("<span class='label'>", "").trim() + "'");

        } catch (Exception e) {
            HandleException.processException(e);
        }
    }

    @Override
    public String element() {

        return html;
    }

    @Override
    public List<String> elements() {

        return null;
    }

    @Override
    public Boolean isChildAvailable() {
        return this.node.hasChildNodes();
    }


    public List<Node> elementFilter() {

        this.childNodes = this.node.getChildNodes();

        for (int i = 0; i < this.childNodes.getLength(); i++) {

            Node firstChild = this.childNodes.item(i);

            if (firstChild != null && !firstChild.getNodeName().equals("#text")) {
                nodeList.add(firstChild);
            }

        }
        return nodeList;
    }
}
