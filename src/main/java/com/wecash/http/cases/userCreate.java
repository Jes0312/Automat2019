package com.wecash.http.cases;

import com.wecash.http.common.BaseProvider;



import com.wecash.http.utils.DBUtils;

import lombok.extern.slf4j.Slf4j;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;

import static com.wecash.http.utils.HttpClientUtils.*;

/**
 * @Author : LeePuvier
 * @CreateTime : 2019/12/24  4:26 PM
 * @ContentUse :
 */
@Slf4j
public class userCreate {


    
    @Test(dataProvider = "userCeateInfo", dataProviderClass = BaseProvider.class, description = "创建用户信息")
    public void userCeateInfo(Map<String, Object> params){

        try {

            log.info("-------------> 数据清理开始");
            DBUtils.clearData(params.get("clearDataSQL").toString());
            log.info("-------------> 数据清理结束");

//    	初始化数据
            log.info("-------------> 数据预至开始");

            Assert.assertEquals(true, DBUtils.initData(params.get("preDataSQL").toString()));
            log.info("-------------> 数据预至结束");


            String caseComment = params.get("Comment").toString();
            String url = params.get("serviceEnv").toString() + params.get("url").toString();
            String baseParamJson = params.get("baseParamJson").toString();
            String exectResult = params.get("exectResult").toString();

            String result = httpJSONPost(url, baseParamJson);
            log.info("【" + caseComment + "】");
            log.info("-------------> 用例功能描述为：" + caseComment);
            log.info("-------------> 期望接口返回为：" + exectResult);
            log.info("-------------> 接口实际返回为：" + result);
            //对比接口返回数据
//            Assert.assertTrue(compareJsonAssert(result, exectResult));
            compareJsonAssert1(result, exectResult);


            //数据库预期结果
            String exceptSQL1 = params.get("exceptSQL1").toString();
            //数据库查询语句
            String seSQL1 = params.get("selSQL1").toString();
            log.info("预期SQL:" + exceptSQL1);
            //数据库断言
            if (null != exceptSQL1 && "" != exceptSQL1 && null != seSQL1) {
                //String actualParam = params.get("actualParam").toString();
                String mysql1 = DBUtils.queryDataSQL(seSQL1);
                log.info("数据库查询结果：" + mysql1);
                Assert.assertEquals(exceptSQL1, mysql1);
            }

            //数据库预期结果
            String exceptSQL2 = params.get("exceptSQL2").toString();
            //数据库查询语句
            String seSQL2 = params.get("selSQL2").toString();
            log.info("预期SQL:" + exceptSQL2);
            //数据库断言
            if (null != exceptSQL2 && "" != exceptSQL2 && null != seSQL2) {

                String mysql2 = DBUtils.queryDataSQL(seSQL2);
                log.info("数据库查询结果：" + mysql2);
                Assert.assertEquals(exceptSQL2, mysql2);
            }

            //数据库预期结果
            String exceptSQL3 = params.get("exceptSQL3").toString();
            //数据库查询语句
            String seSQL3 = params.get("selSQL3").toString();
            log.info("预期SQL:" + exceptSQL3);
            //数据库断言
            if (null != exceptSQL3 && "" != exceptSQL3 && null != seSQL3) {
                String mysql3 = DBUtils.queryDataSQL(seSQL3);
                log.info("数据库查询结果：" + mysql3);
                Assert.assertEquals(exceptSQL3, mysql3);
            }

            //数据库预期结果
            String exceptSQL4 = params.get("exceptSQL4").toString();
            //数据库查询语句
            String seSQL4 = params.get("selSQL4").toString();
            log.info("预期SQL:" + exceptSQL4);
            //数据库断言
            if (null != exceptSQL4 && "" != exceptSQL4 && null != seSQL4) {
                String mysql4 = DBUtils.queryDataSQL(seSQL4);
                log.info("数据库查询结果：" + mysql4);
                Assert.assertEquals(exceptSQL4, mysql4);
            }


            //数据库预期结果
            String exceptSQL5 = params.get("exceptSQL5").toString();
            //数据库查询语句
            String seSQL5 = params.get("selSQL5").toString();
            log.info("预期SQL:" + exceptSQL5);
            //数据库断言
            if (null != exceptSQL5 && "" != exceptSQL5 && null != seSQL5) {
                String mysql5 = DBUtils.queryDataSQL(seSQL5);
                log.info("数据库查询结果：" + mysql5);
                Assert.assertEquals(exceptSQL5, mysql5);
            }
        }  finally
        {  DBUtils.clearData(params.get("clearDataSQL").toString());
            log.info("-------------> case结束--数据清理结束" );
        }






        
    }
    
}
