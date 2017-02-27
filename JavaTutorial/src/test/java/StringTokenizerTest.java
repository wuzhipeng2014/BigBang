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
}
