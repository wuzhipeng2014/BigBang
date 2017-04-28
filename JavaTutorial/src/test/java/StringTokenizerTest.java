import org.joda.time.DateTime;
import org.junit.Test;

import java.util.StringTokenizer;

/**
 * Created by zhipengwu on 17-2-8.
 */
public class StringTokenizerTest {
    public static void main(String[] args) {
        StringTokenizer stringTokenizer=new StringTokenizer("this,is, a test",",",false);
        while (stringTokenizer.hasMoreTokens()){
            System.out.println(stringTokenizer.nextToken());
        }
    }


    @Test
    public void test(){
        String  MONTH = new DateTime().toString("M")+"月";
        System.out.println(MONTH);
    }

    @Test
    public void Test2(){
        String line = "{\"actionTime\":\"20170223 09:42:01.050\",\"productId\":\"694626682\",\"productName\":\"【当日可订】熊猫基地成人票\",\"orderId\":\"161079811124000007\",\"sightId\":10265,\"sightName\":\"成都熊猫基地\",\"sightCity\":\"成都\",\"quantity\":1,\"price\":53.0,\"sightItemList\":[{\"sightId\":10265,\"name\":\"成都熊猫基地\",\"city\":\"成都\",\"address\":\"四川省成都市成华区外北熊猫大道1375号\",\"level\":\"4A\",\"marketPrice\":20.0,\"qunarPrice\":20.0,\"commentCount\":11612,\"commentAvgScore\":4.8,\"isInFavorite\":false}],\"uid\":\"863102035464908\",\"gid\":\"01924675-A355-B111-F026-00552B72C39D\",\"vid\":\"60001156\",\"pid\":\"10010\",\"cid\":\"C2022\",\"un\":\"\",\"userId\":-1,\"lat\":\"30.738887\",\"lgt\":\"104.152921\",\"userCityName\":\"成都\",\"model\":\"vivoX7\"}";

        String[] split = line.split(",");


        System.out.println();
    }


    @Test
    public void testString(){
        String type="search_analysis_click";
        String typeStart=String.format("\"%s\": { ",type);
        String mapping = typeStart
                .concat("   \"properties\" : {")
                .concat("       \"query\" : { \"type\" : \"string\", \"index\" : \"not_analyzed\",\"store\" : \"yes\"},")
                .concat("       \"poi\" : { \"type\" : \"string\", \"index\" : \"not_analyzed\", \"store\" : \"yes\"},")
                .concat("       \"platform\" : {\"type\" : \"string\", \"index\" : \"not_analyzed\" },\"store\" : \"yes\"}")
                .concat("} }");
        System.out.println(mapping);
    }



}
