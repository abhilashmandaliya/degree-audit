/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import controller.Action;
import crud.ProgramCRUD;
import crud.audi_crud;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pojo.Audit_Report;

/**
 *
 * @author Ankit
 */
public class add_degree_audit implements Action {

    @Override
    public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return new audi_crud().create(request).toString();
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
