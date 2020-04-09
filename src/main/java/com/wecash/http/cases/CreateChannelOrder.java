package com.wecash.http.cases;

import com.alibaba.fastjson.JSONObject;

import com.wecash.http.common.BaseProvider;
import com.wecash.http.utils.GetInfoFromFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;

import static com.wecash.http.utils.HttpClientUtils.compareJsonAssert;
import static com.wecash.http.utils.HttpClientUtils.httpPostParamWithJSON;

/**
 * @Author : LeePuvier
 * @CreateTime : 2019/12/24  4:26 PM
 * @ContentUse :
 */
@Slf4j
public class CreateChannelOrder {

    public JSONObject baseParamJson = null;

    @BeforeTest(description = "获取创建渠道订单参数")
    public void getBaseParam(){
        baseParamJson = new GetInfoFromFileUtils().getInfoFromFile("/createOrder/Param_createOrder.json");
        log.info("-------------> 获取创建渠道订单参数为：" + baseParamJson);
    }


    @Test(description = "创建订单", dataProvider = "createOrder", dataProviderClass = BaseProvider.class)
    public void createOrder(ITestContext context, Map<String, Object> params){
        //从【XML】获取环境域名
        String url = context.getCurrentXmlTest().getParameter("serviceEnv") + params.get("url").toString();
        //从【Excel】获取数据
        String caseComment = params.get("Comment").toString();
        String expectParamJson = params.get("expectParamJson").toString();
        String exectResult = params.get("exectResult").toString();
        //发送POST请求
        String result = httpPostParamWithJSON(url, baseParamJson, expectParamJson, "", "");
        log.info("-------------> 用例功能描述为：" + caseComment);
        log.info("-------------> 期望接口返回为：" + exectResult);
        log.info("-------------> 接口实际返回为：" + result);

        //对比接口返回数据
        Assert.assertTrue(compareJsonAssert(result, exectResult));

        //todo 对比数据库数据
//        Assert.assertEquals(result, exectResult);
        
        
        
    }
    
    

}
