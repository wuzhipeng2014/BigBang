/**
 * Created by zhipengwu on 17-7-27.
 */
public class Stringtest {
    public static void main(String[] args) {
        String line ="label\n" +
                "activeDays\n" +
                "activeWeeks\n" +
                "activeDaysOfTopCity\n" +
                "activeDaysOfEndCity\n" +
                "gender\n" +
                "age\n" +
                "platform\n" +
                "model\n" +
                "citeNum\n" +
                "areaNum\n" +
                "allCity\n" +
                "shiftCitys0\n" +
                "shiftCitys1\n" +
                "shiftCitysOnWorkingDay0\n" +
                "shiftCitysOnWorkingDay1\n" +
                "shiftCitysOnWeekend0\n" +
                "shiftCitysOnWeekend1\n" +
                "avgActiveRadius0\n" +
                "avgActiveRadius1\n" +
                "maxActiveRadius0\n" +
                "maxActiveRadius1\n" +
                "avgActiveRadiusOnWorkingDay0\n" +
                "avgActiveRadiusOnWorkingDay1\n" +
                "maxActiveRadiusOnWorkingDay0\n" +
                "maxActiveRadiusOnWorkingDay1\n" +
                "avgActiveRadiusOnWeekend0\n" +
                "avgActiveRadiusOnWeekend1\n" +
                "maxActiveRadiusOnWeekend0\n" +
                "maxActiveRadiusOnWeekend1";
        System.out.println(line.replaceAll("\n","\",\""));

    }
}
