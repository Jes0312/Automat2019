package com.wecash.http.common;

/**
 * @Author : LeePuvier
 * @CreateTime : 2019/12/24  7:47 PM
 * @ContentUse :
 */
public interface ExcelConstant {

    // 渠道订单
    public  String createOrder = "src/main/resources/caseconf/createOrder/Case_channelOrder.xlsx";

    // 资方产品
    public  String capitalProduct = "src/main/resources/caseconf/capitalProduct/Case_capitalProduct.xlsx";
    
    // 创建用户信息
    public  String userCreate = "src/main/resources/caseconf/userCreate/Case_userCreate.xlsx";
    
    //通过租户用户id批量查询用户信息
    public  String userBatchqueryById = "src/main/resources/caseconf/userBatchqueryById/Case_userBatchqueryById.xlsx";
    
    //通过通过手机号MD5值批量查询租户用户id
    public  String userBatchQueryIdByMobileMd = "src/main/resources/caseconf/userBatchQueryIdByMobileMd/Case_userBatchQueryIdByMobileMd.xlsx";
       
    
}
