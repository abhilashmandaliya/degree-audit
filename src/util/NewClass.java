/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author wolverine
 */
public class NewClass {

    public static void main(String[] args) throws FileNotFoundException {
        System.err.println("In a main meethod");
        Scanner sc = new Scanner(new File("hibernate.cfg.xml"));
        while (sc.hasNext()) {
            System.err.println(sc.next());
        }
    }
}
