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
}
