package crud;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pojo.CoursePOJO;
import pojo.ProgramPOJO;
import util.GeneralUtility;
import util.HibernateSessionFactory;
import util.Response;

public class CourseCRUD extends CRUDCore {

    @Override
    public Integer create(HttpServletRequest request) {

        Integer id = null;

        try {
//			tx = session.beginTransaction();
            String course_name = request.getParameter("course_name");
            String course_id = request.getParameter("course_id");
            Integer course_credits = Integer.parseInt(request.getParameter("course_credits"));
            CoursePOJO course_obj = new CoursePOJO(course_name, course_id, course_credits);
            id = (Integer) session.save(course_obj);
            System.out.println(id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    @Override
    public Object retrive(HttpServletRequest request) {
        Response response = null;
        try {
            List<CoursePOJO> courses = session.createQuery("FROM CoursePOJO").list();
            response = GeneralUtility.generateSuccessResponse(null, courses);
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return response.toString();
    }

}
