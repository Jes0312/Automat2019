package com.wecash.http.cases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wecash.http.common.BaseProvider;
import com.wecash.http.utils.ComUtils;
import com.wecash.http.utils.DBUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static com.wecash.http.utils.HttpClientUtils.*;

/**
 * @Author : wangjianmei
 * @CreateTime : 2020/05/11
 * @ContentUse :新增个人信息
 */


@Slf4j
public class InfoPut {

    @Test(dataProvider = "infoput", dataProviderClass = BaseProvider.class, description = "保存个人信息")
    public void userInfoput(Map<String, Object> params){

        try {
            log.info("------------> case编号:"+params.get("用例编号").toString());
//          清理数据
            log.info("-------------> 数据清理开始");
            DBUtils.clearData(params.get("clearDataSQL").toString());
            log.info("-------------> 数据清理结束");

//    	   初始化数据
            log.info("-------------> 数据预至开始");
            Assert.assertEquals(true, DBUtils.initData(params.get("preDataSQL").toString()));
            log.info("-------------> 数据预至结束");


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
//            Assert.assertTrue(compareJsonAssert(result, exectResult));
            compareJsonAssert1(result, exectResult);
            //userinfo 数据库预期结果
            String expect_data= params.get("except_data_userinfo").toString();
            log.info("数据库预期结果为:"+ expect_data);
            //数据库查询语句
            String sql_userinfo= params.get("sql_userinfo").toString();
            log.info("数据库查询语句为:"+ sql_userinfo);
            //数据库断言
            if (null != expect_data && "" != expect_data && null != sql_userinfo) {
                JSONObject data_info = JSON.parseObject(expect_data);
                ResultSet data_mysql = DBUtils.queryDataNoConn(sql_userinfo);
                JSONArray act_result = ComUtils.resultSetToJson(data_mysql);
                log.info("数据库实际查询结果为："+act_result.toString());
                    for (String key :data_info.keySet()){
                        if (null != data_info.get(key) && "" !=data_info.get(key)){
                            Assert.assertTrue(JSONObject.parseObject(act_result.get(0).toString()).get(key).toString().equals(data_info.get(key).toString()),"userInfo数据库字段校验不一致");
                            Assert.assertNotNull(JSONObject.parseObject(act_result.get(0).toString()).get("create_time").toString());
                            Assert.assertNotNull(JSONObject.parseObject(act_result.get(0).toString()).get("update_time").toString());
                        } else {
                            Assert.assertNull(JSONObject.parseObject(act_result.get(0).toString()).get(key), "数据库比对校验不一致");
                        }
                    }
                }
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if (DBUtils.clearData(params.get("clearDataSQL").toString())){
                log.info("执行测试用例结束");
                Assert.assertTrue(true);
            }else {
                log.info("执行测试用例失败");
                Assert.assertTrue(false);
            }
        }
    }
}
