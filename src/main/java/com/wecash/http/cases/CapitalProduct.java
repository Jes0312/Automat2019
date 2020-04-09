package com.wecash.http.cases;

import com.wecash.http.common.BaseProvider;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.Map;

import static com.wecash.http.utils.HttpClientUtils.httpGetWithJson;

/**
 * @Author : LeePuvier
 * @CreateTime : 2019/12/24  4:26 PM
 * @ContentUse :
 */
@Slf4j
public class CapitalProduct {

    @Test(dataProvider = "capitalProductGetList", dataProviderClass = BaseProvider.class, description = "获取产品列表")
    public void capitalProductGetList(ITestContext context, Map<String, Object> params){
        //从【XML】获取环境域名
        String url = context.getCurrentXmlTest().getParameter("serviceEnv") + params.get("url").toString();
        //从【Excel】获取数据
        String caseComment = params.get("Comment").toString();
        String baseParamJson = params.get("baseParamJson").toString();
        String exectResult = params.get("exectResult").toString();
        //发送GET请求
        String result = httpGetWithJson(url, baseParamJson);

        log.info("-------------> 用例功能描述为：" + caseComment);
        log.info("-------------> 期望接口返回为：" + exectResult);
        log.info("-------------> 接口实际返回为：" + result);
    }



    @Test(dataProvider = "capitalProductGetAllProduct", dataProviderClass = BaseProvider.class, description = "根据id获取资方账户下的产品")
    public void capitalProductGetAllProduct(Map<String, Object> params){
        String caseComment = params.get("Comment").toString();
        String url = params.get("serviceEnv").toString() + params.get("url").toString();
        String baseParamJson = params.get("baseParamJson").toString();
        String exectResult = params.get("exectResult").toString();

        String result = httpGetWithJson(url, baseParamJson);
        log.info("-------------> 用例功能描述为：" + caseComment);
        log.info("-------------> 期望接口返回为：" + exectResult);
        log.info("-------------> 接口实际返回为：" + result);
    }

}
