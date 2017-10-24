package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.StudentCRUD;
import java.io.IOException;

public class AddStudent implements Action {

    @Override
    public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("in aaaaaaaaaa final");
        return new StudentCRUD().create(request).toString();
    }
}
