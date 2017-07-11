package com.qunar.work;

/**
 * Created by zhipengwu on 17-7-4.
 */
public class BluepageConfigure {
    public static void main(String[] args) {

        String scenarios="domestic_city\n" +
                "foreign_city\n" +
                "district\n" +
                "sight_area\n" +
                "sight\n" +
                "traffic\n" +
                "theme_family\n" +
                "continent\n" +
                "country\n" +
                "city_to_country\n" +
                "province\n" +
                "city_to_province\n" +
                "landmark\n" +
                "theme_spring\n";
        String[] split = scenarios.split("\n");

        String line=" <item name=\"####\" type=\"2\" table=\"scenario_productlist_expand_sta\" showystday=\"true\"  orderby=\"scenario\"    limit=\"100\">\n" +
                "            <cond column=\"scenario\">####</cond>\n" +
                "              <field key=\"true\" extend=\"false\" name=\"type\">type</field>\n" +
                "               <field key=\"true\" extend=\"false\" name=\"pos\">pos</field>\n" +
                "                <field name=\"pv\">pv</field>\n" +
                "               <field name=\"uv\">uv</field>\n" +
                "                 <field name=\"click\">click</field>\n" +
                "                <field name=\"ctr\">click/pv</field>\n" +
                "                <field name=\"c1_ctr\">c1_click/c1_pv</field>\n" +
                "                <field name=\"c2_ctr\">c2_click/c2_pv</field>\n" +
                "        </item> ";

        for (String item:split){
            String s = line.replaceAll("####", item);
            System.out.println(s);
        }


    }
}
