package controller.action;

import controller.Action;
import crud.GradeCardCRUD;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateGradeCard implements Action {

    @Override
    public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return new GradeCardCRUD().update(request).toString();
    }
}
