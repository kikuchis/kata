package kata.ex01;

import kata.ex01.util.HolidayUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;

class DrivePeriod {

	private LocalDateTime startTm;
	private LocalDateTime endTm;

	DrivePeriod(LocalDateTime startTm, LocalDateTime endTm) {
		this.startTm = startTm;
		this.endTm = endTm;
	}

	boolean isBetween(int startHour, int endHour) {
		final var rs = LocalDateTime.of(endTm.toLocalDate(), LocalTime.of(startHour, 0));
		final var re = LocalDateTime.of(endTm.toLocalDate(), LocalTime.of(endHour, 0));
		return startTm.isBefore(re) && endTm.isAfter(rs);
	}

	boolean isHoliday() {
		var startDate = startTm.toLocalDate();
		return HolidayUtils.isHoliday(startDate) && HolidayUtils.isHoliday(startDate.plusDays(1));
	}
}
