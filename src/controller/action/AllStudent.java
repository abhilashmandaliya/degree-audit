package controller.action;

import controller.Action;
import crud.StudentCRUD;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AllStudent implements Action {

    @Override
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        return (String) new StudentCRUD().retrive(request);
    }

}
