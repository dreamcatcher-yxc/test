package org.example.groovy

/**
 * @DATE 2017/11/30 10:01
 * @author yangxiuchu
 */
class Demo01 {

    def static void baseTest() {
//        def x = 5;
//        println(x);
//        int a = 5;
//        float b = 5.1231f;
//        double c = 10.5e40;
//        BigInteger d =  30g;
//        BigDecimal e = 3.5g;
//        println(a);
//        println(b);
//        println(c);
//        println(d);
//        println(e);

        // 运算符测试
        /*int a1 = 0011100;
        a1 = ~a1;
        println(Integer.toBinaryString(a1)); // 0*/

        // range 测试
//        def range = 5..10;
//        println(range);
//        println(range.get(2));

        // 循环测试
        // while
        int x = 5;
        while (x > 0) {
            println(x--);
        }

        // for
        int sum = 0;
        for (int i = 0; i <= 100; i++) {
            sum += i;
        }
        println(sum);

        // for...in
        def employee = ["Ken": 21, "John": 25, "Sally": 22];
        for (emp in employee) {
            println(emp);
        }
    }

    static def sayHello(name) {
        println('Hello: ' + name)
    }

    // 设置参数默认值
    static void sum(int a, int b = 2) {
        println( a + ' + ' + b + ' = ' + (a + b));
    }

    static void readFile() {
//        int num = 1;
//        new File('D:/temp.txt').eachLine {
//            line ->
//                println("$num : $line");
//                num++;
//        };
        println(new File('D:/temp.txt').text);
    }

    static void writeFile() {
        new File('D:/', 'temp2.txt').withWriter('utf-8', {
            writer -> writer.write 'hello world!';
        });
        println 'ok';
    }

    static def readFileProperties() {
        File file = new File('D:/temp.txt');
        return [absolutePath : file.absolutePath]
    }

    static def copyFile() {
        def src = new File('D:/temp.txt');
        def dist = new File('D:/temp.copy.txt');
        dist << src.text;
    }

    static void readDirContent() {
        // 获取所有盘符
//        new File('test').listRoots().each {
//            file -> println file.absolutePath
//        };
        // 获取 C 目录下的文件(递归)
        // eachFile(非递归)
        new File('C:').eachFileRecurse {
            file -> println file.absolutePath
        };
    }

    static void stringTest() {
        String str = 'hello world!';
        String s1 = 'abc', s2 = 'bcd';
        println str[4]; // o
        println str[1..3]; // ell
        println str[-1]; // !
        println str * 2; // hello world!hello world!
        println str.length(); // 12
        println str.center(30).length(); // 左右填充空格
        println s1.compareToIgnoreCase(s2); // 1
        println s1.concat(s2); // abcbcd
        // a  b  c
        s1.eachMatch('.', {
            ch -> println ch;
        });
        println str.getAt(1);  // e
        // 删除字符串指定的部分.
        println str.minus('hello'); // world!
        // 获取最后一个字符, 并且取其编码表里面的后一个值, 再拼接到前面的字符串中.
        println str[0..(str.length() - 2)].next(); // hello worle
        // padLeft : 左边填充
        // padRight: 右边填充
        println str.plus(' ').plus(' hello china!'); // hello world! hello china!
        // 相对于 str.next() 方法.
        println str[0..(str.length() - 2)].previous(); // hello worlc
        println str.reverse(); // 反转
    }

    static void rangeTest() {
        println 1..3; // [1, 2, 3]
        println 1..<3; // [1, 2]
        println 'a'..'c'; // [a, b, c]
        println 3..1; // [3, 2, 1]
        println 'c'..'a'; // [c, b, a]
        // 范围方法测试
        def range = 1..10;
        println range.contains(2); // true
        println range.get(2); // 3
        println range.getFrom(); // 1
        println range.getTo(); // 10
        println range.isReverse(); // false
        println range.size(); // 10
        println range.subList(0, 3); // [1, 2, 3]
    }

    static void listTest() {
        def list = [12, 45, 89, 70, 68, 19, 40];
        println list.sort(); // 升序, 默认
        println list.sort {a,b -> b.compareTo(a)}; // 倒序
        println list.minus([45, 19, 40]);  // [89, 70, 68, 12]
        println list.plus(100);
    }

    static void mapTest() {
        def map = ['username' : 'zhangsan', 'password' : '123456', 'age' : 20, 'gender' : 'male', 'email' : 'zhangsan@sina.com'];
        println map.containsKey('username'); // true
        println map.get('username'); // zhangsan
        println map.keySet(); // [username, password, age, gender, email]
        // map.put
        println map.size(); // 5
        println map.values(); // [zhangsan, 123456, 20, male, zhangsan@sina.com]
    }

    static void dateTest() {
        def date = Calendar.getInstance();
        println date.get(Calendar.YEAR);
        println date.get(Calendar.MONTH);
        println date.get(Calendar.DAY_OF_MONTH);
        println date.get(Calendar.HOUR_OF_DAY);
        println date.get(Calendar.MINUTE);
        println date.get(Calendar.SECOND);
    }

    static void regTest() {
        def reg = ~'[abc]';
        println 'Groovy' =~ 'Groovy'
        println 'Groovy' =~ 'oo'
        println 'Groovy' ==~ 'Groovy'
        println 'Groovy' ==~ 'oo'
        println 'Groovy' =~ '∧G'
        println 'Groovy' =~ 'G$'
        println 'Groovy' =~ 'Gro*vy'
        println 'Groovy' =~ 'Gro{2}vy'
    }

    static void testException() {
        int[] arr = [1, 2, 3];
        try {
            println arr[3];
        }catch (Exception e) {
//            e.printStackTrace(); // java.lang.ArrayIndexOutOfBoundsException
            println "cause: $e.cause, message: $e.message";
        } finally {
            println 'this is finally run block';
        }
        println 'after program run finish...';
    }

    static void main(String[] args) {
//        baseTest()
//        sayHello('杨秀初');
//        sum(1);
//        readFile();
//        writeFile();
//         println readFileProperties().absolutePath;
//        copyFile()
//        readDirContent();
//        stringTest();
//        rangeTest();
//        listTest();
//        mapTest();
//        dateTest();
//        regTest();
        testException();
    }


}
