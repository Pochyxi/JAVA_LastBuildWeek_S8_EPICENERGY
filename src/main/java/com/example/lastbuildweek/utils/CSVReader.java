package com.example.lastbuildweek.utils;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;


import java.io.File;
import java.io.IOException;

@Getter
@Setter
public class CSVReader {
    private static final String CSC_FILE_PATH_PROVINCE = "CSVcomuni&provicie/province-italiane.csv";
    private static final String CSC_FILE_PATH_COMUNI = "CSVcomuni&provicie/comuni-italiani.csv";

    public  String[] listProvince() throws IOException {

        File file = new File(CSC_FILE_PATH_PROVINCE);
        String readString = FileUtils.readFileToString(file, "UTF-8");

        return readString.split("\r?\n");
    }

    public  String[] listComuni() throws IOException {

        File file = new File(CSC_FILE_PATH_COMUNI);
        String readString = FileUtils.readFileToString(file, "UTF-8");

        return readString.split("\r?\n");
    }

}
