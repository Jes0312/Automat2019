package com.wecash.http.cases;

import com.wecash.http.common.BaseProvider;
import com.wecash.http.utils.DBUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

import static com.wecash.http.utils.HttpClientUtils.compareJsonAssert;
import static com.wecash.http.utils.HttpClientUtils.httpJSONPost;

/**
 * @Author : wangjianmei
 * @CreateTime : 2020/05/12
 * @ContentUse :查询个人信息
 */

@Slf4j
public class InfoQueryInfoByPii {

    @Test(dataProvider = "infoqueryInfoByPii", dataProviderClass = BaseProvider.class, description = "查询个人信息")
    public void infoqueryInfoByPii(Map<String, Object> params){

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
        }catch (Exception e){
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
