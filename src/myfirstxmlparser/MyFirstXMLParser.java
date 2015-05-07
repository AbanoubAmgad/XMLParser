/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myfirstxmlparser;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Abanoub
 */
public class MyFirstXMLParser extends DefaultHandler { //SAX Default handler

    String content = ""; //content that will be written on file 
    static PrintStream result; //printstream to stream to file
    static Vector<Tag> XML = new Vector<Tag>();  //Data structure to index the xml file in 
    Tag T;
    Attribute A;
    String[] RS = {"//", "/", "[", "]", "@", "=", "\"", ">", "<", "*"}; //query relationships

    @Override
    public void startDocument() throws SAXException {
        System.out.println("start document   : ");
        result.append("start document: \r\n");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("end document. ");
        result.append("End Document.");
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        content = new String(ch, start, length);
        System.out.print(content);
        content = content.replaceAll("\n", "\r\n"); //to keep the formating
        XML.get(XML.size() - 1).Content += content; //puting the tag content
        result.append(content);
    }

    @Override
    public void startElement(String namespaceURI,
            String localName,
            String qName,
            Attributes atts)
            throws SAXException {
        System.out.print(qName + " : ");
        T = new Tag();
        T.Name = qName;

        result.append(qName + " : ");
        for (int i = 0; i < atts.getLength(); i++) { //get all attributes with it's value
            System.out.print("[" + atts.getLocalName(i) + "]" + " -> " + atts.getValue(i));
            A = new Attribute();
            A.Name = atts.getLocalName(i);
            A.Value = atts.getValue(i);
            T.Attributes.add(A);
            result.append("[" + atts.getLocalName(i) + "]" + " -> " + atts.getValue(i));
            if (i + 1 != atts.getLength()) {
                System.out.print(" , ");
                result.append(" , ");
            }
        }

        XML.add(T);
    }

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws FileNotFoundException {
        try {
            // TODO code application logic here
            result = new PrintStream("D:\\myXMLoutput.txt"); // creating the file
            //choosing the XML file
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Input.xml", "XML");
            chooser.setFileFilter(filter);
            chooser.showOpenDialog(null);

            File file = chooser.getSelectedFile();
            //sax parser method
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser saxParser = spf.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(new MyFirstXMLParser());
            xmlReader.parse(file.getAbsolutePath());


        } catch (Exception e) {
            System.exit(1);
        }
        result.close();
        //assuring the data structure holds all the tags and values
        for (int i = 0; i < XML.size(); i++) {
            System.out.print(XML.get(i).Name + " ");
            for (int k = 0; k < XML.get(i).Attributes.size(); k++) {
                System.out.print(XML.get(i).Attributes.get(k).Name + " : ");
                System.out.print(XML.get(i).Attributes.get(k).Value + " ");
            }

            System.out.println(XML.get(i).Content);
            //System.out.println();
        }

        String Query;



        System.out.println("Please Enter your XPath Query : ");
        Query = new Scanner(System.in).nextLine().toString();
        Query = Query.replaceAll("//", "/");


        while (Query != "Exit") {
            String[] lvls = Query.substring(1).split("/");
            if (lvls[0].contains("[")) { //if the root has [number]
                int number;
                int flag = 0;
                number = Integer.parseInt(lvls[0].substring(lvls[0].indexOf("[") + 1, lvls[0].indexOf("]")));           
                for (int i = 0; i < XML.size(); i++) {
                    if (XML.get(i).Name.matches(lvls[0].substring(0,lvls[0].indexOf("[")))) { //only in this lvl
                        flag++ ;
                    }
                    if (XML.get(i).Name.matches(lvls[lvls.length - 1])&& flag == number) { //show the contents !
                        System.out.println(XML.get(i).Content);
                    }
                    
                }
                Query = new Scanner(System.in).nextLine().toString();
                Query = Query.replaceAll("//", "/");

            } else {
                for (int i = 0; i < XML.size(); i++) {
                    if (XML.get(i).Name.matches(lvls[lvls.length - 1])) {
                        System.out.println(XML.get(i).Content);
                    }
                }
                Query = new Scanner(System.in).nextLine().toString();
                Query = Query.replaceAll("//", "/");
            }
        }
    }
}
