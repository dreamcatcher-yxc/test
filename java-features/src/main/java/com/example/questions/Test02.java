package com.example.questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

public class Test02 {

    private String str;

    public Test02(String str) {
        this.str = str;
    }

    /**
     * 判断是否对称
     *
     * @param str
     * @return
     */
    private boolean isSymmetric(String str) {
        int size = str.length();
        int len = str.length() / 2;

        for (int i = 0; i < len; i++) {
            if (str.charAt(i) != str.charAt(size - i - 1)) {
                return false;
            }
        }

        return true;
    }

    private void combine(int index, int k, int[] arr, List<List<Integer>> res, List<Integer> tmpArr, Function<List<Integer>, Boolean> validator) {
        if (k == 1) {
            for (int i = index; i < arr.length; i++) {
                tmpArr.add(arr[i]);
                if (validator.apply(tmpArr)) {
                    List<Integer> tis = new ArrayList<>();
                    tis.addAll(tmpArr);
                    res.add(tis);
                }
                tmpArr.remove((Object) arr[i]);
            }
        } else if (k > 1) {
            for (int i = index; i <= arr.length - k; i++) {
                tmpArr.add(arr[i]); //tmpArr都是临时性存储一下
                combine(i + 1, k - 1, arr, res, tmpArr, validator); //索引右移，内部循环，自然排除已经选择的元素
                tmpArr.remove((Object) arr[i]); //tmpArr因为是临时存储的，上一个组合找出后就该释放空间，存储下一个元素继续拼接组合了
            }
        } else {
            return;
        }
    }

    /**
     * @param size
     * @param count
     * @return
     */
    public List<List<Integer>> generateCombination(int size, int count) {

        int[] arr = new int[size];

        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }

        List<List<Integer>> res = new ArrayList<>();

        combine(0,
                count,
                arr,
                res,
                new ArrayList<>(),
                cb -> {
                    char[] chs = str.toCharArray();
                    int len = cb.size();
                    int i1 = 0, i2 = chs.length - 1;
                    int ri1 = 0, ri2 = len - 1;

                    for (int i = 0; i < len; i++) {
                        // 去除从左到右, 从右到左边, 同时开始判断
                        chs[cb.get(ri1)] = '\0';
                        chs[cb.get(ri2)] = '\0';

                        while (chs[i1] == '\0') {
                            i1++;
                        }

                        while (chs[i2] == '\0') {
                            i2--;
                        }

                        if (chs[i1] != chs[i2]) {
                            return false;
                        }

                        ri1++;
                        ri2--;
                    }

                    return true;
                });

        return res;
    }


    public void calculate() {
        if (isSymmetric(this.str)) {
            System.out.println(this.str);
            return;
        }

        int size = this.str.length();

        for (int i = 1; i < size; i++) {
            System.out.println(String.format("尝试去除 %d 个字符", i));
            int flag = 0;
            List<List<Integer>> cbs = generateCombination(size, i);

            for (List<Integer> cb : cbs) {
                StringBuffer sb = new StringBuffer();

                for (int j = 0; j < size; j++) {
                    if (!cb.contains(j)) {
                        sb.append(str.charAt(j));
                    }
                }

                String tStr = sb.toString();

                if (isSymmetric(tStr)) {
                    flag++;
                    System.out.println(String.format("删除位置: %s, 删除个数: %d, 结果: %s", cb.toString(), cb.size(), tStr));
                }
            }

            if (flag > 0) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        String str = "afdafdasdaf";
        System.out.println(str.length() - getResult(str));
        new Test02(str).calculate();
    }

    public static int getResult(String str) {
        StringBuilder sb = new StringBuilder(str);
        String newStr = sb.reverse().toString();
        char[] c1 = str.toCharArray();
        char[] c2 = newStr.toCharArray();
        int n = str.length();
        int[][] dp = new int[n + 1][n + 1];

        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (c1[i - 1] == c2[j - 1]) { //此处应该减1.
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        List<String> yps = new ArrayList<>();

        for(int i = 1; i < n + 1; i++) {
            for(int j = 1; j < n + 1; j++)
            if(dp[i][j] == dp[n][n]) {
                yps.add(i + "," + j);
            }
        }

        List<List<String>> paths = new ArrayList<>();
        int pathIndex = 0;

        for(String yp : yps) {
            String[] tArr = yp.split(",");
            int x = Integer.parseInt(tArr[0]), y = Integer.parseInt(tArr[1]);
            calculatePaths(c1, c2, dp, x, y, pathIndex++, paths);
        }

        paths.forEach(path -> {
            StringBuffer tsb = new StringBuffer();
            path.forEach(ele -> tsb.append(ele.split(",")[2]));
            System.out.println(tsb.toString());
        });

        System.out.println(paths.size());

        return dp[n][n];
    }

    /**
     * 根据 LCS 表递归查询路径
     *
     * @param dp
     * @param i
     * @param j
     * @param pathIndex
     * @param paths
     */
    public static void calculatePaths(char[] chs1, char[] chs2, int[][] dp, int i, int j, int pathIndex, List<List<String>> paths) {
        if (i == 0 || j == 0) {
            return;
        }

        if (paths.size() < pathIndex + 1) {
            List<String> newPath = new ArrayList<>();
            paths.add(newPath);
        }

        List<String> path = paths.get(pathIndex);

        // 相等, 推断上一个坐标是 (i - 1, j - 1)
        if (chs1[i - 1] == chs2[j - 1]) {
            calculatePaths(chs1, chs2, dp, i - 1, j - 1, pathIndex, paths);
            path.add(String.format("%d,%d,%s", i, j, chs1[i - 1]));
//            System.out.println(pathIndex + ": " + chs1[i - 1]);
        } else {
            // 不等, 则向着 (i, j - 1)--左方向, (i - 1, j)--上方向, 查找
            int cl = dp[i][j - 1];
            int ct = dp[i - 1][j];

            // 说明有分支
            if (cl > ct) {
                // 路径向左边走
                calculatePaths(chs1, chs2, dp, i, j - 1, pathIndex, paths);
            } else if (cl < ct) {
                // 路径向上走
                calculatePaths(chs1, chs2, dp, i - 1, j, pathIndex, paths);
            } else {
                // 新建一条路径, 克隆当前路径前方状态, 向上走
//                List<String> newPath = new ArrayList<>();
//                path.forEach(ele -> newPath.add(ele));
//                paths.add(newPath);
//                calculatePaths(chs1, chs2, dp, i - 1, j, pathIndex + 1, paths);
                // 路径从左边走
                calculatePaths(chs1, chs2, dp, i - 1, j, pathIndex, paths);
            }
        }
    }
}
