package com.xuhuan.compare;

import com.xuhuan.utils.common.StringUtil;
import com.xuhuan.utils.file.FileUtil;
import com.xuhuan.utils.print.PrintUtil;
import org.apache.commons.lang.ArrayUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 比较工具类
 *
 * @author huan.xu
 * @Time 2020-06-18 20:02
 */
public class CompareUtil {

    private static String[] ignoreFileTypeArr = {"log", "properties", "png","jar"};

    public static void main(String[] args) throws Exception {
        String sourcePath = "F:\\sinitek_git\\fb_project_git\\fia_aims_bx_jxbxs-moth6\\src\\aim\\aim-all\\target\\aim-all-3.1.5.5";
        String targetPath = "C:\\Users\\admin\\Desktop\\ROOT - 副本";
        String savePath = "C:\\Users\\admin\\Desktop\\deploy\\compare-dir";


        PrintUtil.printlnList(compareDir(sourcePath, targetPath,savePath));
    }

    /**
     * 比较文件夹
     *
     * @param sourcePath 源文件夹
     * @param targetPath 要比较的文件夹
     * @param savePath 要保存到的目录
     * @return
     * @throws Exception
     */
    private static List<String> compareDir(String sourcePath, String targetPath,String savePath) throws Exception {
        List<String> compareResultList = new ArrayList<>();
        if (StringUtil.isNotBlank(sourcePath) && StringUtil.isNotBlank(targetPath)) {
            File sourceFile = new File(sourcePath);
            if (sourceFile.exists()) {
                iterationFile(compareResultList, sourceFile, sourcePath, targetPath,savePath);
            }
        }
        return compareResultList;
    }

    /**
     * 遍历文件
     *
     * @param compareResultList 结果集合
     * @param sourceFile        要遍历的文件
     * @param sourcePath        源文件夹
     * @param targetPath        要比较的文件夹
     * @param savePath        要保存到的目录
     * @throws Exception
     */
    private static void iterationFile(List<String> compareResultList, File sourceFile, String sourcePath, String targetPath,String savePath) throws Exception {
        if (sourceFile.isDirectory()) {
            //文件夹 需要继续迭代
            File[] listFiles = sourceFile.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    iterationFile(compareResultList, file, sourcePath, targetPath,savePath);
                }
            }
        } else {
            String fileType = FileUtil.getFileType(sourceFile);
            if (StringUtil.isNotBlank(fileType)) {
                boolean contains = ArrayUtils.contains(ignoreFileTypeArr, fileType);
                if (!contains) {
                    //需要做比较
                    String targetFilePath = handleTargetFilePath(sourceFile.getPath(), sourcePath, targetPath);
                    File targetFile = new File(targetFilePath);
                    StringBuilder errorMsg = new StringBuilder();
                    if (!targetFile.exists()) {
                        errorMsg.append("缺失[").append(targetFilePath).append("]");
                    } else {
                        long targetFileSize = targetFile.length();
                        long sourceFileSize = sourceFile.length();

                        if (targetFileSize != sourceFileSize) {
                            errorMsg.append("大小不一致[").append(targetFilePath).append("]");
                        }
                    }
                    if (StringUtil.isNotBlank(errorMsg.toString())) {
                        compareResultList.add(errorMsg.toString());
                        if(StringUtil.isNotBlank(savePath)){
                            //组装要复制的目标路径
                            String saveFilePath = handleTargetFilePath(sourceFile.getPath(), sourcePath, savePath);
                            File saveFile=new File(saveFilePath);
                            //目标文件父目录
                            String saveFileParendDirPath = FileUtil.getFileParentPath(saveFile);
                            File saveFileParentDir=new File(saveFileParendDirPath);
                            if(!saveFileParentDir.exists()){
                                saveFileParentDir.mkdirs();
                                saveFile.createNewFile();
                            }
                            FileUtil.copyFile(sourceFile,saveFile);
                        }
                    }
                }
            }
        }
    }


    /**
     * 通过当前文件找到目标文件应该存在的文件
     *
     * @param currentPath
     * @param sourcePath
     * @param targetPath
     * @return
     */
    private static String handleTargetFilePath(String currentPath, String sourcePath, String targetPath) {
        return targetPath + currentPath.replace(sourcePath, "");
    }


}
