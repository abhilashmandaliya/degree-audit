package crud;

import pojo.GradeCard;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.StudentPOJO;
import util.GeneralUtility;
import util.Response;

public class GradeCardCRUD extends CRUDCore {

    @Override
    public Integer delete(HttpServletRequest request) {
        Transaction tx = null;
        Integer id = null;
        System.out.println("in crud man -----------");
        try {
            tx = session.beginTransaction();
            long student_id = Long.valueOf(request.getParameter("student_id"));
            int course_id = Integer.valueOf(request.getParameter("course_id"));
            short semester = Short.valueOf(request.getParameter("semester"));
            boolean status = Boolean.valueOf(request.getParameter("status"));
            double total_credit = Double.valueOf(request.getParameter("total_credit"));
            double obtain_credit = Double.valueOf(request.getParameter("obtain_credit"));
            GradeCard gc = new GradeCard(student_id, course_id, semester, status, total_credit, obtain_credit);
            try {
                session.delete(gc);
                id = 1;
            } catch (Exception e) {
                id = -1;
            }
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
    public Integer update(HttpServletRequest request) {
        Transaction tx = null;
        Integer id = null;
        System.out.println("in crud man -----------");
        try {
            tx = session.beginTransaction();
            long student_id = Long.valueOf(request.getParameter("student_id"));
            int course_id = Integer.valueOf(request.getParameter("course_id"));
            short semester = Short.valueOf(request.getParameter("semester"));
            boolean status = Boolean.valueOf(request.getParameter("status"));
            double total_credit = Double.valueOf(request.getParameter("total_credit"));
            double obtain_credit = Double.valueOf(request.getParameter("obtain_credit"));
            GradeCard gc = new GradeCard(student_id, course_id, semester, status, total_credit, obtain_credit);
            try {
                session.update(gc);
                id = 1;
            } catch (Exception e) {
                id = -1;
            }
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
            long student_id = Long.valueOf(request.getParameter("student_id"));
            int course_id = Integer.valueOf(request.getParameter("course_id"));
            short semester = Short.valueOf(request.getParameter("semester"));
            try {
                Criteria criteria = session.createCriteria(GradeCard.class);
                criteria.add(Restrictions.eq("student_id", (long) student_id));
                criteria.add(Restrictions.eq("course_id", course_id));
                criteria.add(Restrictions.eq("semester", (short) semester));

                List<GradeCard> list = criteria.list();
                response = GeneralUtility.generateSuccessResponse(null, list);
            } catch (HibernateException e) {
                tx.rollback();
                e.printStackTrace();
            } finally {
                if (session.isOpen()) {
                    session.close();
                }
            }
        } catch (Exception e1) {
            try {
                List<GradeCard> programs = session.createQuery("FROM GradeCard").list();
                response = GeneralUtility.generateSuccessResponse(null, programs);
            } catch (HibernateException e) {
                tx.rollback();
                e.printStackTrace();
            } finally {
                if (session.isOpen()) {
                    session.close();
                }
            }
        }
        return response.toString();
    }

    @Override
    public Integer create(HttpServletRequest request) {
        Transaction tx = null;
        Integer id = null;
        System.out.println("in crud man -----------");
        try {
            tx = session.beginTransaction();
            long student_id = Long.valueOf(request.getParameter("student_id"));
            int course_id = Integer.valueOf(request.getParameter("course_id"));
            short semester = Short.valueOf(request.getParameter("semester"));
            boolean status = Boolean.valueOf(request.getParameter("status"));
            double total_credit = Double.valueOf(request.getParameter("total_credit"));
            double obtain_credit = Double.valueOf(request.getParameter("obtain_credit"));
            GradeCard gc = new GradeCard(student_id, course_id, semester, status, total_credit, obtain_credit);
            try {
                id = (Integer) session.save(gc);
            } catch (Exception e) {
                id = -1;
            }
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

}
