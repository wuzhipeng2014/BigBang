import com.clearspring.analytics.util.Lists;
import com.google.common.base.Joiner;
import com.qunar.work.toutiao.LibsvmToCSV;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by zhipengwu on 17-7-27.
 */
public class Stringtest {
    public static void main(String[] args) {

        StringBuilder sb2=new StringBuilder();
        for (int i = 1; i < 31; i++) {
            sb2.append(String.format("I%s,",i));
        }
        for (int i = 1; i < 4; i++) {
            sb2.append(String.format("C%s,",i));
        }

        System.out.println(sb2);

        String city="model-[OPPO A33m]\t685\n" +
                "model-[HUAWEI MLA-AL10]\t689\n" +
                "model-[vivo Y51A]\t690\n" +
                "allCity-[武汉]\t694\n" +
                "allCity-[西安]\t694\n" +
                "model-[HUAWEI NXT-AL10]\t701\n" +
                "model-[vivo X9]\t704\n" +
                "model-[vivo Y51]\t713\n" +
                "model-[vivo V3Max A]\t739\n" +
                "model-[A31]\t745\n" +
                "model-[vivo Y67]\t780\n" +
                "allCity-[石家庄]\t832\n" +
                "allCity-[天津]\t841\n" +
                "model-[OPPO A59m]\t851\n" +
                "model-[HM NOTE 1LTE]\t864\n" +
                "model-[GN5001S]\t872\n" +
                "allCity-[苏州]\t877\n" +
                "model-[2014813]\t893\n" +
                "allCity-[东莞]\t899\n" +
                "allCity-[深圳]\t950\n" +
                "model-[OPPO R9s]\t967\n" +
                "model-[HUAWEI TAG-AL00]\t1000\n" +
                "model-[OPPO A57]\t1012\n" +
                "allCity-[广州]\t1076\n" +
                "model-[OPPO R9m]\t1098\n" +
                "allCity-[成都]\t1142\n" +
                "model-[HM NOTE 1S]\t1150\n" +
                "model-[OPPO A59s]\t1189\n" +
                "allCity-[重庆]\t1191\n" +
                "model-[vivo Y66]\t1245\n" +
                "allCity-[上海]\t1350\n" +
                "model-[HM 2A]\t1358\n" +
                "model-[OPPO A33]\t1414\n" +
                "model-[Redmi Note 2]\t1448\n" +
                "allCity-[北京]\t1494\n" +
                "model-[OPPO A37m]\t1594\n" +
                "model-[Redmi Note 3]\t1603";
        String[] split1 = city.split("\n");
        StringBuilder sb=new StringBuilder();

        for (String item:split1){
            String[] split = item.split("\t");
            sb.append(String.format("'%s',",split[0]));
        }
        System.out.println(sb);


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
//        System.out.println(line.replaceAll("\n","\",\""));

        String s ="0,[54-56],[10],[0-2],[36-38],[M],[30-39],[adr],[OPPOA33],[7-:),[9-:),[天津]#[石家庄]#[廊坊]#[唐山]#[邯郸]#[沧州]#[邢台]#[晋中]#[衡水]#[吕梁],[1.2-:),[10-:),[0.6-1.2),[10-:),[0.4-0.6),[10-:),[147.41-402.43],[147.41-402.43],[147.41-402.43],[147.41-402.43],[53.60-147.41],[147.41-402.43],[147.41-402.43],[147.41-402.43],[53.60-147.41],[147.41-402.43],[147.41-402.43],[147.41-402.43]";
        String[] split = s.split(",");
        List<String> list= Lists.newArrayList();
        for (String item:split){
            list.add(item);
        }
        List<String> feartureforGBDT = null;
        try {
            feartureforGBDT = LibsvmToCSV.createFeartureforGBDT(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(feartureforGBDT.toString());
    }


    @Test
    public void joinFile(){
        String inputfile="/home/zhipengwu/secureCRT/kylinInputFiles_20170908.txt";
        String outputfile="/home/zhipengwu/secureCRT/kylinoutputFiles_20170908.txt";
        try {
            LineIterator lineIterator = FileUtils.lineIterator(new File(inputfile));
            FileWriter fw=new FileWriter(outputfile);
            List<String> list=Lists.newArrayList();
            while (lineIterator.hasNext()){
                String line=lineIterator.nextLine();
                list.add(line);

            }

            String join = Joiner.on(",").skipNulls().join(list);
            fw.append(join);
            fw.flush();
            fw.close();
            System.out.println(join);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
