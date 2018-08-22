package com.pigtom.study.introduction_algorithms.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Description
 * @Author tangDunhong@163.com
 * @Date Created in 2018/8/22 10:04
 */
public class ImageSave {
    public static void save(BufferedImage image) {
        JFileChooser chooser = new JFileChooser();
        String fileName = "new.png";
        File selectedFile;
        File directory = chooser.getCurrentDirectory();//获得当前目录
        String path = directory.getPath()+java.io.File.separator+fileName;
        selectedFile =new File(path);
        int i = 1;
        while (selectedFile.exists()) {
            fileName = "new " + i+".png";
            path = directory.getPath()+java.io.File.separator+fileName;
            selectedFile =new File(path);
            i ++;
        }
        FileFilter fileFilter = new FileNameExtensionFilter("png file", "png", "jpeg");

        try {
            if (image != null)
                ImageIO.write(image, "PNG", selectedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }


//        chooser.setSelectedFile(selectedFile);
//        chooser.addChoosableFileFilter(fileFilter);
//        int returnVal = chooser.showSaveDialog(null);
//        if(returnVal == JFileChooser.APPROVE_OPTION) {
//            selectedFile = chooser.getSelectedFile();
//            System.out.println("You chose to save this file: " +
//                    selectedFile.getName());
//
//            if(selectedFile.getName().trim().length()==0){
//                JOptionPane.showMessageDialog(null, "文件名为空！");
//            }
//            directory = chooser.getCurrentDirectory();//获得当前目录
//
//            path = directory.getPath()+java.io.File.separator+selectedFile.getName();
//            selectedFile =new File(path);
//
//            if(selectedFile.exists()) {  //若选择已有文件----询问是否要覆盖
//                int con = JOptionPane.showConfirmDialog(null, "该文件已经存在，确定要覆盖吗？");
//                if(con == JOptionPane.YES_OPTION)   ;
//                else if (con == JOptionPane.NO_OPTION) {
//                    save(image);
//                } else {
//                    return;
//                }
//
//            }
//            try {
//                if (image != null)
//                    ImageIO.write(image, "PNG", selectedFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


    }

    public static File open() {
        JFileChooser chooser = new JFileChooser();
        FileFilter fileFilter = new FileNameExtensionFilter("数据", "txt", "shp");
        chooser.setFileFilter(fileFilter);
        chooser.showOpenDialog(null);
        return chooser.getSelectedFile();
    }

    public static void main(String[] args) {
//        save(null);
        System.out.println(open());
    }
}
