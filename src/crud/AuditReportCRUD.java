package crud;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import pojo.AuditReportPOJO;
import pojo.CoursePOJO;
import pojo.GradeCard;
import pojo.ProgramPOJO;
import pojo.SemesterPOJO;
import pojo.StudentPOJO;
import util.GeneralUtility;
import util.Response;

public class AuditReportCRUD extends CRUDCore {

	@Override
	public Response create(HttpServletRequest request) throws IOException {
		try {
			Integer id = null;
			Integer student_id = Integer.parseInt(request.getParameter("student_id"));
			ProgramPOJO enrolledProgram = getEnrolledProgram(student_id);
			List<GradeCard> gradesObtained = getGradesByStudentID(student_id);
			int totalCredits = enrolledProgram.getMin_credits();

			int earnedCredits = getEarnedCredits(gradesObtained);
			StudentPOJO student = session.get(StudentPOJO.class, student_id);
			double degreeCompleted = (100 * earnedCredits / totalCredits);
			int requiredCredits = totalCredits - earnedCredits;
			int presentCourses = gradesObtained.size();
			int requiredCourses = enrolledProgram.getMin_courses() - presentCourses;
			double requiredCpi = enrolledProgram.getMin_cpi();
			double currentCpi = getCurrentCPI(gradesObtained);
			short sem = getSemester(gradesObtained);
			double timeLeft = getTimeLeft(sem, enrolledProgram);
			
			AuditReportPOJO auditReport = new AuditReportPOJO(student, degreeCompleted, earnedCredits, requiredCredits,
					currentCpi, requiredCpi, presentCourses, requiredCourses, timeLeft, sem);
			
			id = (Integer) session.save(auditReport);
			tx.commit();
			response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), id);

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public Object retrive(HttpServletRequest request) throws IOException {

		try {
			Integer student_id = Integer.parseInt(request.getParameter("student_id"));
			String hql = "FROM AuditReportPOJO ar WHERE ar.student_id = " + student_id + " ORDER BY ar.id DESC";
			Query query = session.createQuery(hql);
			List<AuditReportPOJO> audit_list = query.list();
			response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), audit_list);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public Response update(HttpServletRequest request) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response delete(HttpServletRequest request) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	// private functions for utility
	private ProgramPOJO getEnrolledProgram(Integer student_id) {
		Query totalCreditsQuery = session
				.createQuery("SELECT s.program_id FROM StudentPOJO s WHERE s.student_id = " + student_id);
		ProgramPOJO program = (ProgramPOJO) totalCreditsQuery.list().get(0);
		return program;
	}

	private List<GradeCard> getGradesByStudentID(Integer student_id) {
		Query earnedCreditsQuery = session
				.createQuery("FROM GradeCard WHERE student_id = " + student_id);

		List<GradeCard> grades = earnedCreditsQuery.list();

		return grades;
	}

	private double getCurrentCPI(List<GradeCard> grades) {
		
		double gradpoints = 0, totalCredits = 0;
		for(GradeCard grade : grades) {
			gradpoints += (grade.getEarn_grade() * grade.getCourse_id().getCourse_credits());
			totalCredits += grade.getCourse_id().getCourse_credits();
		}
		
		return (gradpoints / totalCredits);
	}

	private int getEarnedCredits(List<GradeCard> grades) {
		int earned_credits = 0;

		for (GradeCard grade : grades) {
			earned_credits += grade.getCourse_id().getCourse_credits();
		}

		return earned_credits;
	}
	
	private short getSemester(List<GradeCard> grades) {
		short maxSem = -1;
		for(GradeCard grade : grades) {
			if(grade.getSemester() >= maxSem)
				maxSem = grade.getSemester();
		}
		return (short) (maxSem + 1);
	}
	
	private double getTimeLeft(short sem, ProgramPOJO enrolledProgram) {
		double time = 0;
		time = (enrolledProgram.getMin_duration() * 2) - sem + 1;
		return (time / 2);
	}

}
