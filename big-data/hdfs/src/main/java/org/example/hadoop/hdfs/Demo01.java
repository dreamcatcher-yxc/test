package org.example.hadoop.hdfs;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Demo01 {

    private static Log log = LogFactory.getLog(Demo01.class);

    private FileSystem fs = null;

    @Before
    public void init() throws IOException{
        log.info("开始初始化...");
        Configuration conf = new Configuration();
        fs = FileSystem.get(conf);
        log.info("初始化完成...");
    }

    /**
     * 上传文件（比较底层的写法）
     *
     * hadoop 文件上传的时候，由于 hdfs 存在权限校验，可以通过如下的任意一种方式解决这个问题:
     * 1. 取消文件上传的权限校验。
     * 1.1. hdfs-site.xml 添加如下配置:
     *      <property>
     *           <name>dfs.permissions</name>
     *           <value>false</value>
     *      </property>
     * 1.2. 重启服务。
     *
     * 2. 添加 HADOOP_USER_NAME 环境变量为响应的用户名。
     *
     * @throws IOException
     */
    @Test
    public void testUpload() throws IOException {
        log.info("开始上传文件...");
        FSDataOutputStream os = fs.create(new Path("hdfs://hadoop01:9000/word2.txt"));
        FileInputStream in = new FileInputStream("D:/word.xml");
        IOUtils.copy(in, os);
        log.info("文件上传成功...");
    }

    /**
     * 上传文件，使用上层封装的 API 快速实现。
     * @throws IOException
     */
    @Test
    public void testUpload2() throws IOException {
        log.info("开始上传文件...");
        fs.copyFromLocalFile(new Path("D:/word.xml"), new Path("hdfs://hadoop01:9000/word3.txt"));
        log.info("文件上传成功...");
    }

    /**
     * 文件下载
     * @throws IOException
     */
    @Test
    public void testDownload() throws IOException{
        log.info("开始下载文件...");
        FSDataInputStream in = fs.open(new Path("hdfs://hadoop01:9000/word.xml"));
        FileOutputStream os = new FileOutputStream("D:/word.xml");
        IOUtils.copy(in, os);
        log.info("文件下载成功...");
    }

    /**
     * 创建文件夹.
     */
    @Test
    public void testMkdir() throws IOException {
         log.info("开始创建文件夹...");
         boolean ok = fs.mkdirs(new Path("hdfs://hadoop01:9000/aaa/bbb/ccc"));
         log.info(ok ? "创建文件夹成功..." : "创建文件夹失败...");
    }

    /**
     * 递归删除文件或者文件夹
     * @throws IOException
     */
    @Test
    public void testDelete() throws IOException {
        fs.delete(new Path("hdfs://hadoop01:9000/aaa"), true);
    }

    /**
     * 显示文件列表
     * @throws IOException
     */
    @Test
    public  void testListFiles() throws IOException {
        RemoteIterator<LocatedFileStatus> it = fs.listFiles(new Path("hdfs://hadoop01:9000/"), true);
        while (it.hasNext()) {
            System.out.println(it.next().getPath().getName());
        }
    }
}
