package crud;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pojo.StudentPOJO;
import util.GeneralUtility;
import util.HibernateSessionFactory;
import util.Response;

public class StudentCRUD extends CRUDCore {

    @Override
    public Response create(HttpServletRequest request) throws IOException {
        Transaction tx = null;
        Integer id = null;
        System.out.println("in crud man -----------");
        try {
            tx = session.beginTransaction();
            String s_name = request.getParameter("student_name");
            System.err.println("nannanananannnnne,ee " + s_name);
            Long s_id = Long.parseLong(request.getParameter("student_id"));
            Integer p_id = Integer.parseInt(request.getParameter("program_id"));
            Integer year_of_enrolment = Integer.parseInt(request.getParameter("year_of_enrolment"));
            StudentPOJO student = new StudentPOJO(s_id, s_name, p_id, year_of_enrolment);
            try {
                id = (Integer) session.save(student);
            } catch (Exception e) {
                id = 1;
            }
            tx.commit();
            response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), id);
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return response;
    }

    @Override
    public Object retrive(HttpServletRequest request) {
        Response response = null;
        try {
            long s_id = Integer.valueOf(request.getParameter("student_id"));
            try {
                String hql = "FROM StudentPOJO s WHERE s.student_id = " + s_id;
                Query query = session.createQuery(hql);
                List<StudentPOJO> programs = query.list();
                response = GeneralUtility.generateSuccessResponse(null, programs);
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
                List<StudentPOJO> programs = session.createQuery("FROM StudentPOJO").list();
                System.err.println("List size " + programs.size());
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
    public Response update(HttpServletRequest request) throws IOException {
        Transaction tx = null;
        Integer id = null;
        System.out.println("in crud man -----------");
        try {
            tx = session.beginTransaction();
            String s_name = request.getParameter("student_name");
            System.err.println("nannanananannnnne,ee " + s_name);
            Long s_id = Long.parseLong(request.getParameter("student_id"));
            Integer p_id = Integer.parseInt(request.getParameter("program_id"));
            Integer year_of_enrolment = Integer.parseInt(request.getParameter("year_of_enrolment"));
            StudentPOJO student = new StudentPOJO(s_id, s_name, p_id, year_of_enrolment);
            try {
                session.update(student);
                id = 1;
            } catch (Exception e) {
                id = -1;
            }
            tx.commit();
            response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), id);
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            id = -1;
        } finally {
            session.close();
        }
        return response;
    }

    @Override
    public Response delete(HttpServletRequest request) throws IOException {
        Transaction tx = null;
        Integer id = null;
        System.out.println("in crud man -----------");
        try {
            tx = session.beginTransaction();
            Long s_id = Long.parseLong(request.getParameter("student_id"));

            try {
                String s_name = request.getParameter("student_name");
                System.err.println("nannanananannnnne,ee " + s_name);
                Integer p_id = Integer.parseInt(request.getParameter("program_id"));
                Integer year_of_enrolment = Integer.parseInt(request.getParameter("year_of_enrolment"));
                StudentPOJO student = new StudentPOJO(s_id, s_name, p_id, year_of_enrolment);
                session.delete(student);
                id = 1;
                response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), id);
            } catch (Exception e) {
                id = -1;
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            id = -1;
        } finally {
            session.close();
        }
        return response;
    }
}
