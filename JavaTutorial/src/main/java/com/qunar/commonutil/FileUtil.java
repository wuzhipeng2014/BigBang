package com.qunar.commonutil;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.compress.utils.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

/**
 * Created by zhipengwu on 16-8-12.
 */
public class FileUtil {

    /**
     * 从resources文件夹读取文件
     * 
     * @param fileName
     */
    public static void readFile(String fileName) {
        String filePath = FileUtil.class.getClassLoader().getResource(fileName).getPath();
        System.out.println(filePath);
        String line;
        InputStream resourceAsStream = FileUtil.class.getClassLoader().getResourceAsStream(fileName);
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取指定文件内容到list
     * 
     * @param fileName 待读取文件路径
     * @return list
     */
    public static List<String> readFileToList(String fileName) {
        String filePath = FileUtil.class.getClassLoader().getResource(fileName).getPath();
        List<String> list = Lists.newArrayList();
        System.out.println(filePath);
        String line;
        InputStream resourceAsStream = FileUtil.class.getClassLoader().getResourceAsStream(fileName);
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void writeFile(List<String> list, String fileName) {
        Preconditions.checkNotNull(list, "输入列表为空");
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fileWritter = new FileWriter(fileName, true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            for (String s : list) {
                bufferWritter.write(s);
                bufferWritter.newLine();
            }
            bufferWritter.flush();
            bufferWritter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Iterable<String> getFileIterator(String fileName) throws IOException {
        String filePath = FileUtil.class.getClassLoader().getResource(fileName).getPath();
        final LineIterator lineIterator = FileUtils.lineIterator(new File(filePath), Charsets.UTF_8.name());
        return new Iterable<String>() {
            public Iterator<String> iterator() {
                return new Iterator<String>() {
                    public boolean hasNext() {
                        boolean hasNext = lineIterator.hasNext();
                        if (!hasNext) {
                            LineIterator.closeQuietly(lineIterator);
                        }
                        return hasNext;
                    }

                    public String next() {
                        if (!hasNext()) {
                            throw new NoSuchElementException("No more lines");
                        }
                        return lineIterator.next();
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    public static Scanner getGzFileScaner(String fileName) throws IOException {
        String filePath="";
        try {
             filePath = FileUtil.class.getClassLoader().getResource(fileName).getPath();
        }catch (Exception e){
            filePath=fileName;
        }


        InputStream in = new GZIPInputStream(new FileInputStream(filePath));
        Scanner sc=new Scanner(in);
        return sc;
    }

}
