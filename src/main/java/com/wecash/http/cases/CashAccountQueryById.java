package com.wecash.http.cases;

import com.wecash.http.common.BaseProvider;
import org.testng.annotations.Test;

import com.wecash.http.utils.DBUtils;

import lombok.extern.slf4j.Slf4j;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;


import java.util.Map;

import static com.wecash.http.utils.HttpClientUtils.*;

/**
 * @Author : LeePuvier
 * @CreateTime : 2019/12/24  4:26 PM
 * @ContentUse :
 */
@Slf4j
public class CashAccountQueryById {


    @Test(dataProvider = "QueryAccountById", dataProviderClass = BaseProvider.class, description = "通过中台id查询账户信息")
    public void QueryAccountById(ITestContext context, Map<String, Object> params) {
        try {

            log.info("-------------> 数据清理开始");
            DBUtils.clearData(params.get("clearDataSQL").toString());
            log.info("-------------> 数据清理结束");

//    	初始化数据
            log.info("-------------> 数据预至开始");
            Assert.assertEquals(true, DBUtils.initData(params.get("preDataSQL").toString()));
            log.info("-------------> 数据预至结束");


            String caseComment = params.get("Comment").toString();
            String baseParamJson = params.get("baseParamJson").toString();
            String exectResult = params.get("exectResult").toString();
            String serviceEnv = context.getCurrentXmlTest().getParameter("serviceEnv");
            String url=serviceEnv+params.get("url").toString();
            String result = httpJSONPost(url, baseParamJson);
            log.info("【" + caseComment + "】");
            log.info("-------------> 用例功能描述为：" + caseComment);
            log.info("-------------> 期望接口返回为：" + exectResult);
            log.info("-------------> 接口实际返回为：" + result);
            //对比接口返回数据
//        Assert.assertTrue(compareJsonAssert(result, exectResult));
            compareJsonAssert1(result, exectResult);

            //数据库预期结果
            String exceptSQL = params.get("exceptSQL").toString();
            //数据库查询语句
            String seSQL = params.get("selSQL").toString();
            log.info("预期SQL:" + exceptSQL);
            //数据库断言
            if (null != exceptSQL && "" != exceptSQL && null != seSQL) {
                //String actualParam = params.get("actualParam").toString();
                String mysql = DBUtils.queryDataSQL(seSQL);
                log.info("数据库查询结果：" + mysql);
                Assert.assertEquals(exceptSQL, mysql);

            }

            //清理数据
            //DBUtils.clearData(params.get("clearDataSQL").toString());
        } finally {
            DBUtils.clearData(params.get("clearDataSQL").toString());
        }
    }

}
