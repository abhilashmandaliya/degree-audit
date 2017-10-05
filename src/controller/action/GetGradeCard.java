package controller.action;

import controller.Action;
import crud.GradeCardCRUD;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetGradeCard implements Action {

    @Override
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        String data = (String) new GradeCardCRUD().retrive(request);
        System.err.println("data");
        return data;
    }

}
