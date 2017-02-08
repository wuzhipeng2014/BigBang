package com.qunar.commonutil;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

/**
 * Created by zhipengwu on 16-8-22.
 */
public class HadoopReadFile {

    public void readFile() {
        InputStream in = null;
        // 9000端口要与配置文件中配置的端口号相对应
        String uri = "hdfs://localhost:9000/home/data/test/test.txt";
        try {
            in = new URL(uri).openStream();
            IOUtils.copyBytes(in, System.out, 4096, false);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(in);
        }
    }

    // 通过FileSystem以标准输出格式显示Hadoop文件系统中的文件
    public void readFile2() {
        String uri = "hdfs://localhost:9000/home/data/test/user_authority.sql";
        Configuration configuration = new Configuration();
        InputStream in = null;
        try {
            FileSystem fileSystem = FileSystem.get(URI.create(uri), configuration);

            in = fileSystem.open(new Path(uri));
            IOUtils.copyBytes(in, System.out, 4096, false);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(in);
        }
    }

    // 将本地文件复制到hdfs
    public void copyLocalFilewithProgress() {
        String localFilePath = "/home/zhipengwu/secureCRT/flight20160809.log";
        String desFilePath = "hdfs://localhost:9000/home/data/test/flight20160810.log";
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(localFilePath));
            Configuration configuration = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(desFilePath), configuration);
            OutputStream out = fs.create(new Path(desFilePath), new Progressable() {
                @Override
                public void progress() {
                    System.out.println(". ");
                }
            });
            IOUtils.copyBytes(in, out, 1024, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 此函数的运行存在权限问题
    public void createPath() {
        Configuration conf = new Configuration();
        conf.setBoolean("dfs.support.append", true);
        try {
            FileSystem fs = FileSystem.get(conf);
            Path path = new Path("/test");
            fs.create(path);
            fs.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFile(String line, String filename) {
        Path targetPath = new Path(filename);
        Configuration conf = new Configuration();
        conf.setBoolean("dfs.support.append", true);
        FileSystem fs = null;
        FSDataOutputStream fout = null;
        try {
            fs = FileSystem.get(conf);
            if (!fs.exists(targetPath)) {
                fout = fs.create(targetPath);
            } else {
                fout = fs.append(targetPath);
            }
            fout.writeChars(line);
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
