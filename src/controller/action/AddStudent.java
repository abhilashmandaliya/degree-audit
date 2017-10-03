package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.StudentCRUD;

public class AddStudent implements Action {

    @Override
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("in aaaaaaaaaa final");
        Integer id = new StudentCRUD().create(request);
        return String.valueOf(id);
    }
}
