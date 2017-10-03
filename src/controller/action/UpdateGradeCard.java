package controller.action;

import controller.Action;
import crud.GradeCardCRUD;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateGradeCard implements Action{

    @Override
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        Integer id = new GradeCardCRUD().update(request);
        return String.valueOf(id);
    }
    
}
