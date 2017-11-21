package controller.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import crud.GradeCardCRUD;
import crud.ProgramSemesterDetailCRUD;
import crud.SemesterCRUD;
import pojo.GradeCard;
import pojo.ProgramSemesterDetailPOJO;
import pojo.SemesterPOJO;
import util.GeneralUtility;
import util.Response;

public class GetSPIBaseProgressLineChart implements Action {

	@Override
	public String perform(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// fetch grade card order by semester id and take earned spi
		// fetch required spi for all semester available in the grade card
		List<GradeCard> grade_cards = (List<GradeCard>) ((Response) new GradeCardCRUD().retrive(request)).getData();
		Map<Short, Double> earned = new TreeMap<>(), required = new TreeMap<>();

		for (GradeCard grade_card : grade_cards) {
			short semester_id = grade_card.getSemester();
			double earned_grade = grade_card.getEarn_grade();
			int credits = grade_card.getCourse_id().getCourse_credits();
			if (earned.get(semester_id) == null) {
				earned.put(semester_id, 0d);
				required.put(semester_id, 0d);
			}
			earned.put(semester_id, earned.get(semester_id) + (credits * earned_grade));
			required.put(semester_id, required.get(semester_id) + (credits * 10));
		}
		Set<Short> semesters = earned.keySet();
		Map<Short, Double> actual_performance = new TreeMap<>(), desired_performance = new TreeMap<>();
		for (Short semester : semesters) {
			actual_performance.put(semester, (earned.get(semester) * 10) / required.get(semester));
			request.setAttribute("search", "program_id_semester_id_wise");
			request.setAttribute("semester_id", semester);
			ProgramSemesterDetailPOJO program_semester_detail = (ProgramSemesterDetailPOJO) ((Response) new ProgramSemesterDetailCRUD()
					.retrive(request)).getData();
			desired_performance.put(semester, (double) program_semester_detail.getMin_spi());
		}
		Set<Short> _semesterIds = actual_performance.keySet();
		Set<String> _semesterName = new TreeSet<>();
		request.setAttribute("search", "semester_name_from_id");
		for (Short semester : _semesterIds) {
			request.setAttribute("semester_id", Integer.valueOf(semester));
			SemesterPOJO _semester = (SemesterPOJO) ((Response) new SemesterCRUD().retrive(request)).getData();
			_semesterName.add(_semester.getName());
		}
		Map<String, Object> response_map = new TreeMap<>();
		response_map.put("actual", actual_performance.values());
		response_map.put("desired", desired_performance.values());
		response_map.put("keys", _semesterName);
		Response _response = GeneralUtility.generateSuccessResponse(GeneralUtility.getRedirect(request), response_map);
		return _response.toString();
	}

}
