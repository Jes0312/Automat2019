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
     
    //用户注销
    public  String userLogout = "src/main/resources/caseconf/userLogout/Case_userLogout.xlsx";   
    
    //修改用户信息
    public  String userModify = "src/main/resources/caseconf/userModify/Case_userModify.xlsx";
    
    //根据Id查询用户信息
    public  String userQueryById = "src/main/resources/caseconf/userQueryById/Case_userQueryById.xlsx";   
    
    //根据用户租户Id查询用手机号证件号
    public  String userQueryIdByConditionMd = "src/main/resources/caseconf/userQueryIdByConditionMd/Case_userQueryIdByConditionMd.xlsx";     
    
    //根据手机号证件号查询用户信息
    public  String userQueryLimit = "src/main/resources/caseconf/userQueryLimit/Case_userQueryLimit.xlsx";     
      
   
    //根据用户租户Id查询用手机号证件号Md5值
    public  String userQueryLimitMdById = "src/main/resources/caseconf/userQueryLimitMdById/Case_userQueryLimitMdById.xlsx";    
    
    //根据userId查询用用户信息
    public  String userQueryUserInfoByUserId = "src/main/resources/caseconf/userQueryUserInfoByUserId/Case_userQueryUserInfoByUserId.xlsx";
    
    
    
  //提交实名认证信息
    public  String identitySubmit = "src/main/resources/caseconf/identitySubmit/Case_identitySubmit.xlsx";
    
    
  //查询实名认证信息
    public  String identityGetInfo = "src/main/resources/caseconf/identityGetInfo/Case_identityGetInfo.xlsx";
    

    //实名认证信息失效
    public  String identityInvalid = "src/main/resources/caseconf/identityInvalid/Case_identityInvalid.xlsx";
     
  
    
    
}
