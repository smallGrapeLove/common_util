package com.xuhuan.utils.file;

import com.xuhuan.utils.common.StringUtil;
import com.xuhuan.utils.print.PrintUtil;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

/**
 * 文件读取
 *
 * @author 徐欢
 * @Time 2017-07-19 9:21
 **/
public class FileUtil {

    private static Logger logger = Logger.getLogger(FileUtil.class);

    private static String FILE_TYPE_TXT = "txt";
    private static String FILE_TYPE_XLSX = "xlsx";
    private static String FILE_TYPE_XLS = "xls";
    private static String ENCODING_UTF8 = "utf8";

    /**
     * 读取txt文件到string
     *
     * @param filePath
     * @return
     */
    public static String readTxtToString(String filePath) throws Exception {
        String txtContent = "";
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            if (getFileType(file).equalsIgnoreCase(FILE_TYPE_TXT)) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING_UTF8);
                BufferedReader bufferedReader = new BufferedReader(read);
                try {
                    String lineTxt = null;
                    while ((lineTxt = bufferedReader.readLine()) != null) {
                        txtContent += lineTxt + "\r";
                    }
                    read.close();
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return txtContent;
    }

    /**
     * 读取txt文件每一行到set中
     *
     * @param filePath
     * @return
     */
    public static Set<String> readTxtToSet(String filePath) throws Exception {
        Set<String> txtSet = new LinkedHashSet<String>();
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            if (getFileType(file).equalsIgnoreCase(FILE_TYPE_TXT)) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING_UTF8);
                BufferedReader bufferedReader = new BufferedReader(read);
                try {
                    String lineTxt = null;
                    while ((lineTxt = bufferedReader.readLine()) != null) {
                        txtSet.add(lineTxt);
                    }
                    read.close();
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return txtSet;
    }

    /**
     * 写txt文件
     *
     * @param filePath
     * @return
     */

    public static boolean writeToTxt(String content, String filePath) throws Exception {
        boolean flag = false;
        File file = new File(filePath);
        if (getFileType(file).equalsIgnoreCase(FILE_TYPE_TXT)) {
            if (!file.exists()) {
                file.createNewFile();
            }
            String oldTxtContent = readTxtToString(filePath);
            FileOutputStream o = null;
            try {
                o = new FileOutputStream(file);
                //如果要写入的文件不为空，则追加要写入的内容到最后
                if (StringUtil.isNotBlank(oldTxtContent)) {
                    content = oldTxtContent + content;
                }
                o.write(content.getBytes(ENCODING_UTF8));
                o.close();
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new Exception("请传入正确的txt文件");
        }
        return flag;
    }

    /**
     * 获取文件类型
     *
     * @param file
     * @return
     */
    public static String getFileType(File file) throws Exception {
        String type = "";
        String fileName = file.getName();
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            type = fileName.substring(index + 1, fileName.length());
        } else {
            return "";
        }
        return type;
    }

    /**
     * 获取文件去掉文件名的路径
     *
     * @param file
     * @return
     */
    public static String getFileParentPath(File file) {
        String path = file.getPath();
        return path.substring(0, path.lastIndexOf("\\"));
    }

    /**
     * 读取目录下的所有文件到set集合中
     *
     * @param dir
     * @param resultSet 结果集
     * @return
     */
    public static Set<String> readDirFileToSet(Set<String> resultSet, File dir) {
        if (dir.exists()) {
            if (dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    if (file.isDirectory()) {
                        readDirFileToSet(resultSet, file);
                    } else {
                        resultSet.add(file.getPath());
                    }
                }
            }
        }
        return resultSet;
    }


    /**
     * 重命名一个目录下面的文件：替换文件名称中一个字符串
     *
     * @param dirPath   目录路径
     * @param sourceStr 要替换的字符串
     * @param destStr   替换为的字符串
     * @throws FileNotFoundException
     */
    public static void repaceDirFileName(String dirPath, String sourceStr, String destStr) throws FileNotFoundException {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            throw new FileNotFoundException("文件不存在：" + dirPath);
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String resultName = file.getName().replace(sourceStr, destStr);
                String resultFilePath = dirPath + "\\" + resultName;
                boolean flag = file.renameTo(new File(resultFilePath));
                if (!flag) {
                    System.out.println(file.getName() + "重命名为：" + resultName + "失败");
                }
            }
        }
    }

    /**
     * 给目录下的所有文件增加前缀或者后缀
     *
     * @param dirPath 目录路径
     * @param type    类型：0：前缀 1：后缀
     * @param str     字符串
     * @throws FileNotFoundException
     */
    public static void modifyDirFilePrefixOrSuffix(String dirPath, int type, String str) throws FileNotFoundException {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            throw new FileNotFoundException("文件不存在：" + dirPath);
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String resultName = "";
                if (type == 0) {
                    resultName = str + file.getName();
                } else if (type == 1) {
                    int index = file.getName().lastIndexOf(".");
                    String simpFileName = file.getName().substring(0, index);
                    String fileType = file.getName().substring(index, file.getName().length());
                    resultName = simpFileName + str + fileType;
                }
                String resultFilePath = dirPath + "\\" + resultName;
                boolean flag = file.renameTo(new File(resultFilePath));
                if (!flag) {
                    System.out.println(file.getName() + "重命名为：" + resultName + "失败");
                }
            }
        }
    }

    /**
     * 复制文件
     *
     * @param src
     * @param dst
     * @throws Exception
     */
    public static void copyFile(File src, File dst) throws Exception {
        int BUFFER_SIZE = 4096;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 比对文件夹
     *
     * @param sourceDir
     * @param targetDir
     */
    public static void compareDir(File sourceDir, File targetDir) {
        if (sourceDir.exists()) {
            Set<String> sourceFileSet = new HashSet<>();
            Set<String> targetFileSet = new HashSet<>();
            File[] sourceFileList = sourceDir.listFiles();
            File[] targetFileList = targetDir.listFiles();
            for (File sourceFile : sourceFileList) {
                sourceFileSet.add(sourceFile.getName());
            }
            for (File targetFile : targetFileList) {
                targetFileSet.add(targetFile.getName());
            }
            //这里是删除的文件
            System.out.println("下面是删除的文件：");
            for (String str : sourceFileSet) {
                if (!targetFileSet.contains(str)) {
                    System.out.println(str);
                }
            }
            //这里是新增的文件
            System.out.println("下面是新增的文件：");
            for (String str : targetFileSet) {
                if (!sourceFileSet.contains(str)) {
                    System.out.println(str);
                }
            }


        }

    }

    public static void main(String[] args) {
        String sourcePath = "E:\\6.tomcats\\项目源文件包\\协会\\会员端\\hy\\WEB-INF\\lib";
        String targetPath = "E:\\6.tomcats\\apache-tomcat-7.0.55-hy\\webapps\\ROOT\\WEB-INF\\lib";
        compareDir(new File(sourcePath), new File(targetPath));
    }

}
