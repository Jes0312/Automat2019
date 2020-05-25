package com.wecash.http.cases;

import com.wecash.http.common.BaseProvider;


import com.wecash.http.utils.DBUtils;

import lombok.extern.slf4j.Slf4j;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;

import java.util.Map;

import static com.wecash.http.utils.HttpClientUtils.*;
import static com.wecash.http.utils.RedisUtils.getJedis;

/**
 * @Author : LeePuvier
 * @CreateTime : 2019/12/24  4:26 PM
 * @ContentUse :
 */
@Slf4j
public class userQueryLimit {


    @Test(dataProvider = "GetInfoLimit", dataProviderClass = BaseProvider.class, description = "查询用户信息")
    public void GetInfoLimit(ITestContext context, Map<String, Object> params) {

        try {

//        清理缓存数据

            String rediscommon = params.get("redis").toString();
            if (null != rediscommon && "" != rediscommon) {
                log.info("-------------> 缓存清理开始");
                Jedis redis = getJedis();
                redis.select(10);

                if (!"".equals(rediscommon) || rediscommon.length() != 0) {
                    if (rediscommon.length() == 1)
                        redis.del(rediscommon);
                    else {
                        String[] pareRedis = rediscommon.split(";");
                        for (String pareRed : pareRedis) {
                            redis.del(pareRed);
                        }
                    }
                }
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
//                String url = params.get("serviceEnv").toString() + params.get("url").toString();
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
                compareJsonAssert1(result, exectResult, "tenantUserId");

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
            } finally

        {
            DBUtils.clearData(params.get("clearDataSQL").toString());
            log.info("-------------> case结束--数据清理结束");
        }
        }

    }



