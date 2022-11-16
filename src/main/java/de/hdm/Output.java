package de.hdm;

import de.hdm.helper.ResultOutput;

public class Output {
    
    public void print(ResultOutput result){
        System.out.println(result.name);
    }

    //maybe noch Request Object oder Result Object zu Output Object ändern
    public void print(String arg){
        System.out.println("Here is how help");
    }
}
