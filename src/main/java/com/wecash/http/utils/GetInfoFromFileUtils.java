package com.wecash.http.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Author : LeePuvier
 * @CreateTime : 2020/3/23  2:00 PM
 * @ContentUse :
 */

@Slf4j
public class GetInfoFromFileUtils {

    File filepath;
    JSONObject filecontent;

    public JSONObject getInfoFromFile(String filename){
        try {
            //获取提单参数文件的路径
            this.filepath = new File(System.getProperty("user.dir") + "/src/main/resources/caseconf" + filename);
            log.info("获取参数文件路径为：" + filepath);
            //根据路径获取接口参数
            String input = FileUtils.readFileToString(filepath,"UTF-8");
            //把获取到的接口参数转换为JSON
            this.filecontent= JSONObject.parseObject(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
            return this.filecontent;
    }
}
