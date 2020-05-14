package com.wecash.http.cases;

import com.wecash.http.common.BaseProvider;


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
public class CashAccountModify {


    @Test(dataProvider = "ModifyCashaccount", dataProviderClass = BaseProvider.class, description = "修改账户")
    public void ModifyCashaccount(Map<String, Object> params) {
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
            Assert.assertTrue(compareJsonAssert(result, exectResult));


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


            }
        } finally {
            DBUtils.clearData(params.get("clearDataSQL").toString());
            log.info("case结束后清理数据结束");
        }

    }
}



















