package com.wecash.http.cases;

import com.google.gson.internal.$Gson$Preconditions;
import com.wecash.http.common.BaseProvider;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

import static com.wecash.http.utils.RedisUtils.getJedis;


import redis.clients.jedis.Jedis;
import com.wecash.http.utils.DBUtils;
import static com.wecash.http.utils.HttpClientUtils.compareJsonAssert;
import static com.wecash.http.utils.HttpClientUtils.httpJSONPost;

/**
 * @Author : LeePuvier
 * @CreateTime : 2019/12/24  4:26 PM
 * @ContentUse :
 */
@Slf4j
public class AAdemo {
//    dataProvider  与接口名称一致   在/src/main/java/com/wecash/http/common/BaseProvider.java
//    description    可以尽量写详细

    //    步骤1 cases中创建于接口名称一致java文件
//    步骤2 BaseProvider创建用例执行规则
//    步骤3 ExcelConstant中添加excel路径
//    步骤4 创建TestSuit-接口名的xml文件
//    步骤5 testNG中添加TestSuit的xml文件路径
//
//
//
    @Test(dataProvider = "userCeateInfo", dataProviderClass = BaseProvider.class, description = "创建用户信息")
//    @Test
    public void userCeateInfo(Map<String, Object> params) {


//        清理缓存数据


        try {


            //        清理缓存数据

            String rediscommon = params.get("redis").toString();

            if (null != rediscommon && "" != rediscommon) {
                log.info("-------------> 缓存清理开始");
                Jedis redis = getJedis();
//            切换库目前都在10
                redis.select(10);
                redis.del(params.get("redis").toString());
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
//          实际访问的开始
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


        } finally {
            DBUtils.clearData(params.get("clearDataSQL").toString());
            log.info("-------------> case结束--数据清理结束");
        }


    }

}
