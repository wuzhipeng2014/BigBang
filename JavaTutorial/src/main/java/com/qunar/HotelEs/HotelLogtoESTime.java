package com.qunar.HotelEs;

import com.clearspring.analytics.util.Lists;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by zhipengwu on 17-7-25. 统计酒店日志收集到es的时间
 */
public class HotelLogtoESTime {
    private static TransportClient client = null;

    private static String ipStr = "";
    private static String portStr = "";
    private static String clusterName = "";
    private static final Logger logger = LoggerFactory.getLogger(HotelLogtoESTime.class);

    public static void main(String[] args) throws InterruptedException, IOException {
        String configPath = "data/hotelCluster.properties";
        // 创建client
        client = createHotelClient(configPath);
        // 创建索引
        String index = "logs_logger_wireless_m_invocation_hotdog-2017.07.25";

//         String targetGid = "E35F45E9-3F7F-E4E9-B98F-9F5E861406BC";
         String targetGid = "3FCBB5AD-FD25-EBFE-B835-38D6ECF5C6DE"; //我的gid
//        String targetGid = "4F85B612-E860-7FC0-F3EA-3D875E2B5323";
        String preActiontime=null;
        FileWriter fileWriter=new FileWriter("/home/zhipengwu/secureCRT/output/hotel_log/hotel_log_to_es_time"+DateTime.now().getMillis()+".txt");
        while (true) {
            String s = excuteQuery(index, client, targetGid);
            if (s.contains("actionTime:")) {
                int nowIndex = s.indexOf("gid");
                int actionTimeIndex = s.indexOf("actionTime:");
                String actiontime = s.substring(11, nowIndex).trim();
//                if (preActiontime==null||!preActiontime.equalsIgnoreCase(actiontime)){
                if (preActiontime==null||Long.valueOf(preActiontime)<(Long.valueOf(actiontime))){
                    preActiontime=actiontime;
                    fileWriter.append(s);
                    fileWriter.append("\n");
                    fileWriter.flush();
                }else {
                    System.out.println(String.format("%s   无最新数据",DateTime.now().toString("yyyy-MM-dd HH:mm:ss:SSS")));
                }
            }else {
                    System.out.println(s);

            }
//            Thread.sleep(1000);
        }


    }

    public static String excuteQuery(String index, TransportClient client, String targetGid) {
        SearchResponse searchResponse = client.prepareSearch(index).setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setScroll(new TimeValue(60000))
                .setQuery(QueryBuilders.boolQuery().filter(QueryBuilders.matchPhraseQuery("gid", targetGid)))
                .setSize(100).get();

        List<HotelLog> hotelLogList = Lists.newArrayList();
        SearchHit[] searchHits;
        do {
            for (SearchHit searchHit : searchResponse.getHits().getHits()) {
                HotelLog hotelLog = resolveHotelLog(searchHit);
                if (hotelLog != null) {
                    hotelLogList.add(hotelLog);
                    // System.out.println(hotelLog.gid);
                }
            }
            searchResponse = client.prepareSearchScroll(searchResponse.getScrollId()).setScroll(new TimeValue(60000))
                    .execute().actionGet();
        } while (searchResponse.getHits().getHits().length != 0);

        hotelLogList.sort(new Comparator<HotelLog>() {
            @Override
            public int compare(HotelLog o1, HotelLog o2) {
                return o2.actionTime.compareTo(o1.actionTime);
            }
        });
        long now_millis = DateTime.now().getMillis();
        if (hotelLogList.size()>0) {
            for (HotelLog hotelLog : hotelLogList) {
                String format = String.format("actionTime:%s\tgid:%s\tnow:%s\tdiff(ms):%s\tdiff(s):%s", hotelLog.actionTime,
                        targetGid,now_millis, now_millis - hotelLog.actionTime, (now_millis - hotelLog.actionTime) / 1000);
//                System.out.println(format);
                return format;
            }
        }
            return String.format("%s, 查询结果为空, gid=%s, index=%s",DateTime.now().toString("yyyy-MM-dd HH:mm:ss:SSS"),targetGid,index);


    }

    public static TransportClient createHotelClient(String configFile) {
        try {

            PropertyUtil.init(configFile);
            Map<String, String> configMap = PropertyUtil.getAllProps();
            ipStr = configMap.get("host.ip");
            portStr = configMap.get("host.port");
            clusterName = configMap.get("cluster.name");

            if (StringUtils.isBlank(ipStr) || StringUtils.isBlank(portStr) || StringUtils.isBlank(clusterName)) {
                throw new RuntimeException("创建ES客户端失败:[ES配置文件未找到] 请联系开发人员");
            }

            // 设置
            Settings settings = Settings.builder().put("client.transport.sniff", true).put("cluster.name", clusterName)
                    .build();
            client = new PreBuiltTransportClient(settings);

            String[] ipArray = ipStr.split(",");
            for (String ip : ipArray) {
                client.addTransportAddress(
                        new InetSocketTransportAddress(InetAddress.getByName(ip), Integer.parseInt(portStr)));
            }
        } catch (Exception e) {
            logger.error("----------createClient --- create client failed", e);
            throw new RuntimeException("创建ES客户端失败 请联系开发人员");
        }
        return client;
    }

    public static HotelLog resolveHotelLog(SearchHit searchHit) {
        Map<String, Object> source = searchHit.getSource();
        String gid = source.get("gid").toString();
        String actionTime = source.get("actiontime").toString();
        String sendTime = source.get("send_time").toString();
        HotelLog hotelLog = new HotelLog();
        hotelLog.sendTime = sendTime;
        hotelLog.gid = gid;
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMddHHmmssSSS");
        hotelLog.actionTime = fmt.parseDateTime(actionTime).getMillis();
        return hotelLog;
    }

    public static class HotelLog {
        public String gid;
        public Long actionTime;
        public String sendTime;
    }

}
