package crud;

import com.google.gson.Gson;
import pojo.GradeCard;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import util.GeneralUtility;
import util.HibernateSessionFactory;
import util.Response;

public class GradeCardUtility {

    protected Session session;
    protected Transaction tx;
    protected Response response;
    protected Gson json;

    public GradeCardUtility() {
        session = HibernateSessionFactory.getSession();
        tx = session.beginTransaction();
        response = new Response();
        json = new Gson();
    }

    public Object getStudentGradeCard(HttpServletRequest request) {
        //Transaction tx = null;
        Integer id = null;
        response = null;
        try {
            //tx = session.beginTransaction();
            String student_id = request.getParameter("student_id");
            //String course_id = request.getParameter("course_id");
            String semester = request.getParameter("semester");
            String hql = null;
            List resuList = null;

            if (semester == null) {
                /*hql = "select student_id, semester, sum(obtain_grade) as obtain_total, sum(total_grade) as total_grade from grade_card "
                        + "where student_id = " + Long.valueOf(student_id) + " group by semester,student_id;"; */
                resuList = session.createCriteria(GradeCard.class)
                        .add(Restrictions.eq("student_id", Long.valueOf(student_id)))
                        .setProjection(
                                Projections.projectionList()
                                        .add(Projections.groupProperty("student_id"))
                                        .add(Projections.groupProperty("semester"))
                                        .add(Projections.sum("obtain_grade"))
                                        .add(Projections.sum("total_grade"))
                        )
                        .list();
            } else {
                /* hql = "select student_id, semester, sum(obtain_grade) as obtain_total, sum(total_grade) as total_grade from grade_card "
                        + "where student_id = " + Long.valueOf(student_id) + " and semester = " + Short.valueOf(semester)
                        + " group by semester,student_id;";*/
                resuList = session.createCriteria(GradeCard.class)
                        .add(Restrictions.eq("student_id", Long.valueOf(student_id)))
                        .add(Restrictions.eq("semester", Short.valueOf(semester)))
                        .setProjection(
                                Projections.projectionList()
                                        .add(Projections.groupProperty("student_id"))
                                        .add(Projections.groupProperty("semester"))
                                        .add(Projections.sum("obtain_grade"))
                                        .add(Projections.sum("total_grade"))
                        )
                        .list();
            }

            /*Query query = session.createSQLQuery(hql);
            List<Object[]> list = query.list();
            for (Object[] arr : list) {
                System.out.println(Arrays.toString(arr));
            }*/
            response = GeneralUtility.generateSuccessResponse(null, resuList);
            tx.commit();
        } catch (NumberFormatException | HibernateException e1) {
            tx.rollback();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return response.toString();
    }
}
