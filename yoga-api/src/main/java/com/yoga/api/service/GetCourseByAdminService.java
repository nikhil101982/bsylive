package com.yoga.api.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.constant.ApiConstants;
import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.DayEntity;
import com.yoga.api.entity.LectureEntity;
import com.yoga.api.model.DayByCourseId;
import com.yoga.api.model.Lecture;
import com.yoga.api.model.LectureByDay;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.LectureRepository;
import com.yoga.api.util.CompareDates;

@Service
public class GetCourseByAdminService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	LectureRepository lecRepository;

	@Autowired
	DayRepository dayRepository;

	List<CourseEntity> courseEntityList;

	CourseEntity courseEntity;

	List<LectureEntity> lecEntityList;

	LectureEntity lecEntity;

	LectureEntity lecEntity2;

	Lecture lecture;

	LectureByDay lectureByDay;

	List<LectureByDay> lectureByDayList;

	List<DayEntity> dayEntityList;
	
	List<LocalDate> datesBetweenStartDateAndEndDate;

	DayEntity dayEntity;

	DayByCourseId dayByCourseId = new DayByCourseId();

	CompareDates compareDates = new CompareDates();

	public DayByCourseId getCourseByAdmin(Integer courseId, Integer dayId) throws ParseException {

		if (Objects.isNull(courseId) || Objects.isNull(dayId)) {
			return errorResponse("error");
		}

		try {
			courseEntity = courseRepository.getCourseEntityByCourseId(courseId);
		} catch (Exception e) {
			return errorResponse("error");
		}

		if (Objects.isNull(courseEntity)) {
			return errorResponse("error");
		}

		String compareDate = compareDates.compareCourseStartDate(courseEntity.getStartDate());

		if (!compareDate.equals(ApiConstants.TRUE)) {
			return errorResponse("error");

		}

		String startDate = courseEntity.getStartDate();
		String endDate = courseEntity.getEndDate();

		 datesBetweenStartDateAndEndDate = date(startDate, endDate);

		dayEntityList = courseEntity.getDayEntity();

		lectureByDayList = new ArrayList<>();

		// Day
		try {
			dayEntity = dayRepository.getDayEntityByDayId(dayId);
		} catch (Exception e) {
			return errorResponse("error");
		}

		if (Objects.isNull(dayEntity)) {
			return errorResponse("error");
		}

		lecEntityList = dayEntity.getLecEntity();

		int numberOfLecture = dayEntity.getLecEntity().size();

		int[] arrayOfLectureId = new int[numberOfLecture];

		int count = 0;

		for (LectureEntity lectureEntity : lecEntityList) {

			arrayOfLectureId[count] = lectureEntity.getLectureId();
			count = count + 1;
		}

		Arrays.sort(arrayOfLectureId);

		for (int i = 0; i < numberOfLecture; i++) {

			try {
				lectureByDay = new LectureByDay();

				lectureByDay.setLectureByDayId(arrayOfLectureId[i]);

				LectureEntity lectureEntity2 = lecRepository.getLecEntityByLecId(arrayOfLectureId[i]);

				if (!Objects.isNull(lectureEntity2.getVideoIframeDynamicLink())) {

					lectureByDay.setVideoIframeDynamicLink(lectureEntity2.getVideoIframeDynamicLink());
					lectureByDay.setDisableJoinBtn(lectureEntity2.getDisableJoinBtn());
					lectureByDay.setLiveIframeDynamicLink("");

				} else {

					lectureByDay.setLiveIframeDynamicLink(lectureEntity2.getLiveIframeDynamicLink());
					lectureByDay.setDisableJoinBtn(lectureEntity2.getDisableJoinBtn());
					lectureByDay.setVideoIframeDynamicLink("");

				}

				lectureByDay.setSNo(lectureEntity2.getSNo());
				lectureByDay.setCurrentDate(lectureEntity2.getCurrDate());
				lectureByDay.setEndTime(lectureEntity2.getEndTime());
				lectureByDay.setLectureName(lectureEntity2.getLectureName());
				lectureByDay.setStartTime(lectureEntity2.getStartTime());

				lectureByDayList.add(lectureByDay);
			} catch (Exception e) {
				return errorResponse("error");
			}

		}

		DayByCourseId dayByCourseId = successResponse();

		if (Objects.isNull(dayByCourseId)) {
			return errorResponse("Course is not present !");

		}
		return dayByCourseId;

	}

	public static List<LocalDate> getDatesBetweenStartDateAndEndDate(LocalDate startDate, LocalDate endDate) {

		long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
		return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween).mapToObj(i -> startDate.plusDays(i))
				.collect(Collectors.toList());
	}

	private static List<LocalDate> date(String startDate, String endDate) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("DD/mm/yyyy");

		String sDate = convert(startDate);
		String eDate = convert(endDate);

		// convert String to LocalDate
		LocalDate localStartDate = LocalDate.parse(sDate, formatter);
		LocalDate localEndDate = LocalDate.parse(eDate, formatter).plusDays(1);

		List<LocalDate> datesBetweenStartDateAndEndDate = getDatesBetweenStartDateAndEndDate(localStartDate,
				localEndDate);

		return datesBetweenStartDateAndEndDate;

	}

	private static String convert(String startDate) {

		String m = "/";

		char d1 = startDate.charAt(8);
		char d2 = startDate.charAt(9);

		String dd = d1 + "" + d2;

		char m1 = startDate.charAt(5);
		char m2 = startDate.charAt(6);

		String mm = m1 + "" + m2;

		char y1 = startDate.charAt(0);
		char y2 = startDate.charAt(1);
		char y3 = startDate.charAt(2);
		char y4 = startDate.charAt(3);

		String yyyy = y1 + "" + y2 + "" + y3 + "" + y4;

		String date = dd + m + mm + m + yyyy;

		return date;

	}

	private DayByCourseId successResponse() {

		dayByCourseId.setDayName(dayEntity.getDayName());
		dayByCourseId.setDayId(dayEntity.getDayId());
		dayByCourseId.setLecture(lectureByDayList);
		// dayByCourseId.setDate(date);
		dayByCourseId.setStatus(ApiConstants.SUCCESS);
		dayByCourseId.setMessage("course!");
		return dayByCourseId;
	}

	private DayByCourseId errorResponse(String message) {

		dayByCourseId.setMessage(message);
		dayByCourseId.setStatus(ApiConstants.FAILURE);

		return dayByCourseId;
	}

}
