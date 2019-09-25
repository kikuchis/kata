package kata.ex01;

import kata.ex01.model.HighwayDrive;
import kata.ex01.model.RouteType;
import kata.ex01.model.VehicleFamily;
import kata.ex01.util.HolidayUtils;

import java.util.EnumSet;

/**
 * @author kawasima
 */
public class DiscountServiceImpl implements DiscountService {
    @Override
    public long calc(HighwayDrive drive) {
        //        平日朝夕割引
        //        平日「朝:6時〜9時」、「夕:17時〜20時」
        //        地方部　
        //        当月の利用回数が5回〜9回 30%割引、10回以上 50%割引
        //        休日割引
        //        普通車、軽自動車等(二輪車)限定
        //        土曜・日曜・祝日
        //                地方部
        //        30%割引
        //        深夜割引
        //                すべての車種
        //        毎日0〜４時
        //        30%割引
        DrivePeriod period = new DrivePeriod(drive.getEnteredAt(), drive.getExitedAt());

        RouteType routeType = drive.getRouteType();
        VehicleFamily vehicleFamily = drive.getVehicleFamily();
        final int countPerMonth = drive.getDriver().getCountPerMonth();

        if (isRural(routeType)) {
            // 平日「朝:6時〜9時」、「夕:17時〜20時」かつ地方部かつ当月の利用回数が10回以上
            // => 50%割引
            if (isWeekdaysMorningOrEvening(period)) {
                if (countPerMonth >= 10) {
                    return 50;
                } else if (countPerMonth >= 5) {
                    return 30;
                }
            } else if (isHolidayDiscount(vehicleFamily, period)) {
                // 休日割
                return 30;
            }
        }
        // 深夜
        // => 30%
        if (isMidnight(period)) {
            return 30;
        }
        return 0;
    }

    private boolean isWeekdaysMorningOrEvening(DrivePeriod period) {
        boolean isMorning = !period.isHoliday() && period.isBetween(6, 9);
        boolean isEvening = !period.isHoliday() && period.isBetween(17, 20);
        return isMorning || isEvening;
    }

    private boolean isRural(RouteType routeType) {
        return RouteType.RURAL.equals(routeType);
    }

    private boolean isHolidayDiscount(VehicleFamily vehicleFamily, DrivePeriod period) {
        boolean isHoliday = period.isHoliday();
        boolean isHolidayDiscountVehicle = EnumSet.of(VehicleFamily.STANDARD, VehicleFamily.MOTORCYCLE, VehicleFamily.MINI).contains(vehicleFamily);
        return isHoliday && isHolidayDiscountVehicle;
    }

    private boolean isMidnight(DrivePeriod period) {
        return period.isBetween(0, 4);
    }
}
