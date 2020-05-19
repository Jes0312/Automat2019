package com.wecash.http.cases;

import com.wecash.http.common.BaseProvider;
import org.testng.annotations.Test;

import com.wecash.http.utils.DBUtils;

import lombok.extern.slf4j.Slf4j;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import redis.clients.jedis.Jedis;


import java.util.Map;

import static com.wecash.http.utils.HttpClientUtils.compareJsonAssert;
import static com.wecash.http.utils.HttpClientUtils.httpJSONPost;
import static com.wecash.http.utils.RedisUtils.getJedis;

/**
 * @Author : LeePuvier
 * @CreateTime : 2019/12/24  4:26 PM
 * @ContentUse :
 */
@Slf4j
public class identitySubmit {


    @Test(dataProvider = "idSubmitInfo", dataProviderClass = BaseProvider.class, description = "提交实名认证信息")
    public void idSubmitInfo(Map<String, Object> params) {

        try {

            //        清理缓存数据

            String rediscommon = params.get("redis").toString();
            if (null != rediscommon && "" != rediscommon) {


                log.info("-------------> 缓存清理开始");
                Jedis redis = getJedis();
                redis.select(10);
//                log.info("-------------取出的是啥> " + params.get("redis").toString());

                redis.del(rediscommon);
                log.info("-------------> 缓存清理结束");
            }


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


                //数据库预期结果
                String exceptSQL2 = params.get("exceptSQL2").toString();
                //数据库查询语句
                String seSQL2 = params.get("selSQL2").toString();
                log.info("预期SQL:" + exceptSQL2);
                //数据库断言
                if (null != exceptSQL2 && "" != exceptSQL2 && null != seSQL2) {
                    //String actualParam = params.get("actualParam").toString();
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
                        //String actualParam = params.get("actualParam").toString();
                        String mysql3 = DBUtils.queryDataSQL(seSQL3);
                        log.info("数据库查询结果：" + mysql3);
                        Assert.assertEquals(exceptSQL3, mysql3);


                    }


                }
            }
        } finally {
            log.info("测试结束清理数据");

//            DBUtils.clearData(params.get("clearDataSQL").toString());
        }
    }
}