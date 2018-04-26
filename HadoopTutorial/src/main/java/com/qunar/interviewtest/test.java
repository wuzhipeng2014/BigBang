package com.qunar.interviewtest;

import com.google.common.base.Joiner;

/**
 * Created by zhipengwu on 17-11-17.
 */
public class test {
    public static void main(String[] args) {
        String line ="a54de833416ddfafda9a68a7a361a115\t0 43:1.0 74:1.0 100:1.0 152:1.0 183:1.0 187:1.0 193:1.0 302:1.0 599:1.0 606:1.0 610:1.0 643:1.0 1013:1.0 1022:1.0 1026:1.0 1035:1.0 1037:1.0 1047:1.0 1252:1.0 1261:1.0 1270:1.0 1278:1.0 1284:1.0 1293:1.0 1302:1.0 1310:1.0 1314:1.0 1325:1.0 1330:1.0 1342:1.0 1347:1.0 1365:1.0 1369:1.0 1397:1.0 1424:1.0 1433:1.0 1446:1.0 1463:1.0 1473:1.0 1496:1.0 1550:1.0 2768:1.0 2801:1.0 4786:1.0 4804:1.0 4841:1.0 4883:1.0 5114:1.0 7697:1.0 7760:1.0 7773:0";
        String[] split = line.split("\t| ");
        String keyid=split[0];
        String label=split[1];
        split[0]=null;
        split[1]=null;
        String join = Joiner.on("#").skipNulls().join(split);
        System.out.println(join);
    }
}
