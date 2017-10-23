/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import controller.Action;
import crud.audi_crud;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ankit
 */
public class delete_degree_audit implements Action
{

    @Override
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        Integer id = new audi_crud().delete(request);
        return String.valueOf(id);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
