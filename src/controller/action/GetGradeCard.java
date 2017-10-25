package controller.action;

import controller.Action;
import crud.GradeCardCRUD;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetGradeCard implements Action {

    @Override
    public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String data = (String) new GradeCardCRUD().retrive(request).toString();
        System.err.println("data");
        return data;
    }

}
