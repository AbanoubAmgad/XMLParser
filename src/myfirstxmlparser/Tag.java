/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myfirstxmlparser;

import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author Abanoub
 */
public class Tag {
    String Name ;
    Vector <Attribute> Attributes = new Vector <Attribute>();
    String Content = "" ; 
    
    void print () {
        System.out.println("Name : " + Name);
        if (!Attributes.isEmpty()){
        System.out.println("Attrs : ") ; 
        for ( int i = 0 ; i < Attributes.size();i++) {
            System.out.print(Attributes.get(i).Name + " : ");
            System.out.print(Attributes.get(i).Value);
        }
        }
        System.out.println();
    }
}
