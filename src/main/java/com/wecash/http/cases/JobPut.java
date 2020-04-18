package com.wecash.http.cases;

import com.wecash.http.common.BaseProvider;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.wecash.http.utils.DBUtils;

import lombok.extern.slf4j.Slf4j;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;

import static com.wecash.http.utils.HttpClientUtils.compareJsonAssert;
import static com.wecash.http.utils.HttpClientUtils.httpJSONPost;

/**
 * @Author : LeePuvier
 * @CreateTime : 2019/12/24  4:26 PM
 * @ContentUse :
 */
@Slf4j
public class JobPut {


    
    @Test(dataProvider = "SaveJobInfo", dataProviderClass = BaseProvider.class, description = "提交职业信息")
    public void SaveJobInfo(Map<String, Object> params){
        
    	DBUtils.clearData(params.get("clearDataSQL").toString());
    	//初始化数据
        Assert.assertEquals(true, DBUtils.initData(params.get("preDataSQL").toString()));
        
        String caseComment = params.get("Comment").toString();
        String url = params.get("serviceEnv").toString() + params.get("url").toString();
        String baseParamJson = params.get("baseParamJson").toString();
        String exectResult = params.get("exectResult").toString();

        String result = httpJSONPost(url, baseParamJson);
        log.info("【"+caseComment+"】");
        log.info("-------------> 用例功能描述为：" + caseComment);
        log.info("-------------> 期望接口返回为：" + exectResult);
        log.info("-------------> 接口实际返回为：" + result);
        //对比接口返回数据
        Assert.assertTrue(compareJsonAssert(result, exectResult));
  
        
      //数据库预期结果
        String exceptSQL1= params.get("exceptSQL1").toString();
        //数据库查询语句
        String seSQL1= params.get("selSQL1").toString();
        log.info("预期SQL:"+ exceptSQL1);
        //数据库断言
        if (null != exceptSQL1 && "" != exceptSQL1 && null != seSQL1) {
        	//String actualParam = params.get("actualParam").toString();
            String mysql1 = DBUtils.queryDataSQL(seSQL1);
            log.info("数据库查询结果："+mysql1);
            Assert.assertEquals(exceptSQL1,mysql1);
        }       
        

    	
            //数据库预期结果
       // String exceptSQL2= params.get("exceptSQL2").toString();
            //数据库查询语句
        String seSQL2= params.get("selSQL2").toString();

            //数据库断言
        if (  "" != seSQL2 && null != seSQL2) {
        	
        	//Date dd=new Date();
        	//格式化
        	SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	String time=sim.format(System.currentTimeMillis());
        	String exptime = time.substring(0, 13);
            log.info("预期SQL:"+ exptime);
            	
            String mysql2 = DBUtils.queryDataSQL(seSQL2);
            String createTime = mysql2.substring(0, 13);
            log.info("数据库查询结果："+createTime);
            Assert.assertEquals(exptime,createTime);     
        }       
        
       
        //数据库预期结果
      // String exceptSQL2= params.get("exceptSQL2").toString();
        //数据库查询语句
        String seSQL3= params.get("selSQL2").toString();
  
        //数据库断言
        if (  "" != seSQL3 && null != seSQL3) {
        	
        	//Date day=new Date();  
        	SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	String time=sim.format(System.currentTimeMillis());
        	String exptime = time.substring(0, 13);
            log.info("预期SQL:"+ exptime);
            	        	
            String mysql3 = DBUtils.queryDataSQL(seSQL3);
            String updateTime = mysql3.substring(0, 13);
            log.info("数据库查询结果："+mysql3);
            Assert.assertEquals(exptime,updateTime);     
    }       
        
        
    }
    
}
