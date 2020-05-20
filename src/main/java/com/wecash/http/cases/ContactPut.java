package com.wecash.http.cases;

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

import static com.wecash.http.utils.HttpClientUtils.compareJsonAssert;
import static com.wecash.http.utils.HttpClientUtils.httpJSONPost;

/**
 * @Author : wangjianmei
 * @CreateTime : 2020/05/14
 * @ContentUse :保存紧急联系人信息
 */


@Slf4j
public class ContactPut {

    @Test(dataProvider = "contactput", dataProviderClass = BaseProvider.class, description = "保存紧急联系人信息")
    public void contactput(Map<String, Object> params){

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
            Assert.assertTrue(compareJsonAssert(result, exectResult));

            //usercontact 数据库预期结果
            String expect_data= params.get("except_data_usercontact").toString();
            log.info("数据库预期结果为:"+ expect_data);
            //数据库保存信息断言
            if (null !=expect_data && "" != expect_data){
                JSONObject sql_field = JSONObject.parseObject(params.get("sql_field").toString());
                String pre_sql = "SELECT $field$ FROM user_contact WHERE tenant_id = \""+ sql_field.getString("tenant_id") + "\" and tenant_user_id = \""+ sql_field.getString("tenant_user_id") + "\";";
                Assert.assertTrue(ComUtils.compareMultiMysqlData(expect_data,pre_sql),"user_contact:valid_result数据对比不一致");

            }
            //数据库查询语句_校验time
            String sql_usercontact= params.get("sql_usercontact").toString();
            log.info("数据库查询语句为:"+ sql_usercontact);
            if (null !=sql_usercontact && "" !=sql_usercontact){
                ResultSet data_mysql = DBUtils.queryDataNoConn(sql_usercontact);
                JSONArray act_result = ComUtils.resultSetToJson(data_mysql);
                log.info("数据库实际查询结果为："+act_result.toString());
                log.info("查询条数为："+ act_result.size());
                if (act_result.size() > 0){
                    for ( int i =0; i<act_result.size(); i++){
                        Assert.assertNotNull(JSONObject.parseObject(act_result.get(i).toString()).get("create_time").toString());
                        Assert.assertNotNull(JSONObject.parseObject(act_result.get(i).toString()).get("update_time").toString());
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
//        finally {
//            if (DBUtils.clearData(params.get("clearDataSQL").toString())){
//                log.info("执行测试用例结束");
//                Assert.assertTrue(true);
//            }else {
//                log.info("执行测试用例失败");
//                Assert.assertTrue(false);
//            }
//        }
    }

//    @Test
//    public void test0111(){
//        JSONObject j= new JSONObject();
//        j.put("aa","bb");
//        j.put("cc",null);
//
//        log.info(j.keySet().toString());
//    }
}
