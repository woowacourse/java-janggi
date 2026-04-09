package laboratory.time;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

public class CompareJavaPackageUtilAndTime {

    public static void main(String[] args) {
        System.out.println("--- Goal: 주어진 날짜를 2025년 12월 25일로 변경하라. ---");
        System.out.println();

        System.out.println("1. Date는 setter가 열려 있다.");
        Date date = Date.valueOf("2025-12-20");
        System.out.println("date(기존) = " + date);
        date.setTime(1766600000000L);
        System.out.println("date(변경) = " + date);
        System.out.println();

        System.out.println("2. Calendar setter가 열려 있다.");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JANUARY, 20);
        System.out.println("calendar(기존) = " + calendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, 25);
        System.out.println("calendar(변경) = " + calendar.getTime());
        System.out.println();

        System.out.println("3. LocalDate는 불변이다.");
        LocalDate localDate = LocalDate.of(2025, 12, 20);
        System.out.println("localDate(기존) = " + localDate);
        LocalDate converted = localDate.plusDays(5); // 추가로 시간 계산 편의 메서드 다수 제공
        System.out.println("localDate(변경) = " + converted);
    }
}
