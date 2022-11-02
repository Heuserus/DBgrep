package de.hdm;

import de.hdm.helper.Result;

public class Output {
    
    public void print(Result result){
        System.out.println(result.name);
    }

    //maybe noch Request Object oder Result Object zu Output Object ändern
    public void print(String arg){
        System.out.println("Here is how help");
    }
}
