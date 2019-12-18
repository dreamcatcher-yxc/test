package com.example.questions;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test01 {

    private String str;

    public Test01(String str) {
        this.str = str;
    }

    /**
     * 判断是否对称
     * @param str
     * @return
     */
    private boolean isSymmetric(String str) {
        int size = str.length();
        int len = str.length() / 2;

        for(int i = 0; i < len; i++) {
            if(str.charAt(i) != str.charAt(size - i - 1)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 长度为 size, 删除其中的 count 个字符
     * @param size
     * @param count
     * @return
     */
    public List<List<Integer>> generateCombination(int size, int count) {
        int[] arr = new int[size];

        for(int i = 0; i < size; i++) {
            arr[i] = i;
        }

        List<List<Integer>> res = new ArrayList<>();
        combine(0, count, arr, res, new ArrayList<>());

        return res;
    }

    private void combine(int index, int k, int[] arr, List<List<Integer>> res, List<Integer> tmpArr) {
        if(k == 1){
            for (int i = index; i < arr.length; i++) {
                tmpArr.add(arr[i]);
                List<Integer> tis = new ArrayList<>();
                tmpArr.forEach(ti -> tis.add(ti));
                res.add(tis);
                tmpArr.remove((Object)arr[i]);
            }
        } else if(k > 1){
            for (int i = index; i <= arr.length - k; i++) {
                tmpArr.add(arr[i]); //tmpArr都是临时性存储一下
                combine(i + 1,k - 1, arr, res, tmpArr); //索引右移，内部循环，自然排除已经选择的元素
                tmpArr.remove((Object)arr[i]); //tmpArr因为是临时存储的，上一个组合找出后就该释放空间，存储下一个元素继续拼接组合了
            }
        } else{
            return ;
        }
    }

    public void calculate() {
        if(isSymmetric(this.str)) {
            System.out.println(this.str);
            return;
        }

        int size = this.str.length();

        for(int i = 1; i < size; i++) {
            int flag = 0;
            List<List<Integer>> cbs = generateCombination(size, i);

            for(List<Integer> cb : cbs) {
                StringBuffer sb = new StringBuffer();

                for(int j = 0; j < size; j++) {
                    if(!cb.contains(j)) {
                        sb.append(str.charAt(j));
                    }
                }

                String tStr = sb.toString();

                if(isSymmetric(tStr)) {
                    flag++;
                    System.out.println(String.format("删除位置: %s, 删除个数: %d, 结果: %s", cb.toString(), cb.size(), tStr));
                }
            }

            if(flag > 0) {
                break;
            }
        }
    }

//    public static void main(String[] args) {
//        new Test01("abkfdafdadfsdafadfsafojifnda").calculate();
//    }

    public static String md5(String dataStr) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte s[] = m.digest();
            String result = "";
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void main(String[] args) {
        String url = "http://115.236.191.156:10480/mobile/areaPicker.jsp",
                ak = "test", secretKey = "test",
                qfh = "39D37F143C2E0760B12F50DB72F5D767",
                qfqd = "9131333002";
                // 生产
//                ak = "2a1ba466cbd840fa93c6c91e15537b46";
//                secretKey = "xe9htcJNTGlr/ZnXlvWp0JZDFiY=";

        String time  = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String sbsign = md5(ak + secretKey + time).toLowerCase();
        String res = String.format("%s?ak=%s&sbsign=%s&time=%s&qfh=%s&qfqd=%s", url, ak, sbsign, time, qfh, qfqd);
        System.out.println(res);
    }

//    public static void main(String[] args) {
//        String url = "http://115.236.191.130:7003/basic-osb-service/proxy/CbzmService",
//                ak = "53177a5f-daa6-415d-86f3-e8b0ae868965",
//                secretKey = "0efda3d2-7756-4424-a477-7156b4d04470",
//                qfh = "39D37F143C2E0760B12F50DB72F5D767",
//                qfqd = "9131333002";
//
//        // 生产
//        ak = "2a1ba466cbd840fa93c6c91e15537b46";
//        secretKey = "xe9htcJNTGlr/ZnXlvWp0JZDFiY=";
//
//        String time  = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//        String sbsign = md5(ak + secretKey + time).toLowerCase();
//        String res = String.format("%s?ak=%s&sbsign=%s&atime=%s&qfh=%s&qfqd=%s&areaak=%s&sjgb=%s&model=%s&month=%s", url, ak, sbsign, time, qfh, qfqd, "NGQ3LWE2ZDQtNjcwMzc3Mzg0YjA0", "1", "1", "12");
//        System.out.println(res);
////        connection(url);
//
//        Object obj = null;
//        String str = (String)obj;
//    }
//
//    public static void connection(String targetUrl) {
//        try {
//            URL url = new URL(targetUrl);
//            // 创建代理服务器
//            InetSocketAddress addr = new InetSocketAddress("12.99.235.1",8080);
//            Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
//            HttpURLConnection conn = (HttpURLConnection)url.openConnection(proxy);
////            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setConnectTimeout(60000);
//            conn.setReadTimeout(60000);
//
//            InputStream in = conn.getInputStream();
//            int len;
//            byte[] buf = new byte[1024];
//            StringBuffer sb = new StringBuffer();
//
//            while ((len = in.read(buf)) > 0) {
//                sb.append(new String(buf, 0, len));
//            }
//
//            System.out.println(sb.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
