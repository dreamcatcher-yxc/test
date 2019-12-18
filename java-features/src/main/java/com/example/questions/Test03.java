package com.example.questions;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.List;
import java.util.Map;


public class Test03 {

    public static class VO {
        private String code;

        private List<Map<String, Object>> data;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<Map<String, Object>> getData() {
            return data;
        }

        public void setData(List<Map<String, Object>> data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "VO{" +
                    "code='" + code + '\'' +
                    ", data=" + data +
                    '}';
        }
    }

    @Test
    public void test1() {
        String data = "{\"code\":\"1\",\"data\":[{\"acceptanceId\":\"1140967996140163072\",\"acceptanceNo\":\"111111111258\",\"isSuccess\":\"1\",\"returnMsg\":\"\"},{\"acceptanceId\":\"1139094888408027136\",\"acceptanceNo\":\"12345678925\",\"isSuccess\":\"0\",\"returnMsg\":\"数据更新失败，该票据已贴现\"}],\"message\":\"\"}";
        VO vo = JSONObject.parseObject(data, VO.class);
        Map map = JSONObject.parseObject(data, Map.class);

        List<Map<String, Object>> tds = (List<Map<String, Object>>) map.get("data");

        // lamda 表达式
        tds.forEach(ele -> {
            System.out.println(ele.get("returnMsg"));
        });

        for (Map<String, Object> td : tds) {

        }
//        System.out.println(map);
    }
}
