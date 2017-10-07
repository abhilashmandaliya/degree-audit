package controller.action;

import controller.Action;
import crud.GradeCardCRUD;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddGradeCard implements Action {

    @Override
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        Integer id = new GradeCardCRUD().create(request);
        return String.valueOf(id);
    }
}
