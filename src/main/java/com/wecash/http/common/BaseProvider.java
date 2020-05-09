package com.wecash.http.common;

import com.wecash.http.utils.ExcelUitls;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author : LeePuvier
 * @CreateTime : 2019/12/24  7:50 PM
 * @ContentUse :
 */
public class BaseProvider {


//    name 与接口名称一致 驼峰命名

//    src/main/java/com/wecash/http/common/ExcelConstant.java中添加excel的路径；命名规则同name
//    sheetName 为excel中sheet页的名字；命名规则同name

	
    @DataProvider(name = "CeateCashaccount")
    public Iterator<Object[]> CeateCashaccount() throws IOException {
        List<String> lables = new ArrayList<String>();
        //lables.add("0级");
        lables.add("1级");
        ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.CashAccountCreate, "CashAccountCreate", lables);
        return testdata.iterator();
    }
    
    
    @DataProvider(name = "ModifyCashaccount")
    public Iterator<Object[]> ModifyCashaccount() throws IOException {
        List<String> lables = new ArrayList<String>();
        //lables.add("0级");
        lables.add("1级");
        ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.CashAccountModify, "CashAccountModify", lables);
        return testdata.iterator();
    }
    
  
    @DataProvider(name = "QueryAccountInfo")
    public Iterator<Object[]> QueryAccountInfo () throws IOException {
        List<String> lables = new ArrayList<String>();
        //lables.add("0级");
        lables.add("1级");
        ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.CashAccountQueryLimit, "CashAccountQueryLimit", lables);
        return testdata.iterator();
    }  
    
   
    @DataProvider(name = "QueryAccountById")
    public Iterator<Object[]> QueryAccountById () throws IOException {
        List<String> lables = new ArrayList<String>();
        //lables.add("0级");
        lables.add("1级");
        ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.CashAccountQueryById, "CashAccountQueryById", lables);
        return testdata.iterator();
    }    
    
    @DataProvider(name = "userCeateInfo")
    public Iterator<Object[]> userCeateInfo () throws IOException {
        List<String> lables = new ArrayList<String>();
        //lables.add("0级");
        lables.add("1级");
        ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.userCreate, "userCreate", lables);
        return testdata.iterator();
    } 
    
    
    @DataProvider(name = "userBatchQueryMobileMd")
    public Iterator<Object[]> userBatchQueryMobileMd() throws IOException {
        List<String> lables = new ArrayList<String>();
        //lables.add("0级");
        lables.add("1级");
        ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.userBatchQueryIdByMobileMd, "userBatchQueryIdByMobileMd", lables);
        return testdata.iterator();
    }
    
    
    @DataProvider(name = "userInvalid")
    public Iterator<Object[]> userInvalid() throws IOException {
        List<String> lables = new ArrayList<String>();
        //lables.add("0级");
        lables.add("1级");
        ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.userLogout, "userLogout", lables);
        return testdata.iterator();
    }   
  
    
    @DataProvider(name = "userInfoModify")
    public Iterator<Object[]> userInfoModify() throws IOException {
        List<String> lables = new ArrayList<String>();
        //lables.add("0级");
        lables.add("1级");
        ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.userModify, "userModify", lables);
        return testdata.iterator();
    }   
    
    @DataProvider(name = "GetInfoById")
    public Iterator<Object[]> GetInfoById() throws IOException {
        List<String> lables = new ArrayList<String>();
        //lables.add("0级");
        lables.add("1级");
        ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.userQueryById, "userQueryById", lables);
        return testdata.iterator();
    }      
    
    
    @DataProvider(name = "QueryByConditionMd")
    public Iterator<Object[]> QueryByConditionMd() throws IOException {
        List<String> lables = new ArrayList<String>();
        //lables.add("0级");
        lables.add("1级");
        ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.userQueryIdByConditionMd, "userQueryIdByConditionMd", lables);
        return testdata.iterator();
    }     
    
  
    @DataProvider(name = "GetInfoLimit")
    public Iterator<Object[]> GetInfoLimit () throws IOException {
        List<String> lables = new ArrayList<String>();
//        lables.add("0级");
        lables.add("1级");
        ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.userQueryLimit, "userQueryLimit", lables);
        return testdata.iterator();
    }    
    
   
    @DataProvider(name = "GetInfoLimitById")
    public Iterator<Object[]> GetInfoLimitById () throws IOException {
        List<String> lables = new ArrayList<String>();
        //lables.add("0级");
        lables.add("1级");
        ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.userQueryLimitMdById, "userQueryLimitMdById", lables);
        return testdata.iterator();
    }     
    

    @DataProvider(name = "GetInfoByUserId")
    public Iterator<Object[]> GetInfoByUserId () throws IOException {
        List<String> lables = new ArrayList<String>();
        //lables.add("0级");
        lables.add("1级");
        ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.userQueryUserInfoByUserId, "userQueryUserInfoByUserId", lables);
        return testdata.iterator();
    }    
    
    
    
    
    @DataProvider(name = "idSubmitInfo")
    public Iterator<Object[]> idSubmitInfo() throws IOException {
        List<String> lables = new ArrayList<String>();
        //lables.add("0级");
        lables.add("1级");
        ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.identitySubmit, "identitySubmit", lables);
        return testdata.iterator();
    } 
    
    
    @DataProvider(name = "idGetInfo")
    public Iterator<Object[]> idGetInfo() throws IOException {
        List<String> lables = new ArrayList<String>();
        //lables.add("0级");
        lables.add("1级");
        ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.identityGetInfo, "identityGetInfo", lables);
        return testdata.iterator();
    } 
       
 
    @DataProvider(name = "idInvalid")
    public Iterator<Object[]> idInvalid() throws IOException {
        List<String> lables = new ArrayList<String>();
        //lables.add("0级");
        lables.add("1级");
        ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.identityInvalid, "identityInvalid", lables);
        return testdata.iterator();
    }    
    
        
    
    @DataProvider(name = "userBatchQuery")
    public Iterator<Object[]> userBatchQuery() throws IOException {
        List<String> lables = new ArrayList<String>();
        //lables.add("0级");
        lables.add("1级");
        ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.userBatchqueryById, "userBatchqueryById", lables);
        return testdata.iterator();
    }
        
        @DataProvider(name = "SaveGrantInfo")
        public Iterator<Object[]> SaveGrantInfo() throws IOException {
            List<String> lables = new ArrayList<String>();
            //lables.add("0级");
            lables.add("1级");
            ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.GrantSave, "SaveGrant", lables);
            return testdata.iterator();       
              
    }  
    
    
        @DataProvider(name = "GetGrantInfo")
        public Iterator<Object[]> GetGrantInfo() throws IOException {
            List<String> lables = new ArrayList<String>();
            //lables.add("0级");
            lables.add("1级");
            ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.GrantGetById, "GrantGetById", lables);
            return testdata.iterator();
        } 
    
        @DataProvider(name = "GetGrantByType")
        public Iterator<Object[]> GetGrantByType() throws IOException {
            List<String> lables = new ArrayList<String>();
            //lables.add("0级");
            lables.add("1级");
            ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.GrantGetByType, "GrantGetByType", lables);
            return testdata.iterator();
        }    
    
    
  
        @DataProvider(name = "GetGrantConfig")
        public Iterator<Object[]> GetGrantConfig() throws IOException {
            List<String> lables = new ArrayList<String>();
            //lables.add("0级");
            lables.add("1级");
            ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.GrantGetConfigByType, "GrantGetConfigByType", lables);
            return testdata.iterator();
        }    
    
    
        @DataProvider(name = "InvalidGrantInfo")
        public Iterator<Object[]> InvalidGrantInfo() throws IOException {
            List<String> lables = new ArrayList<String>();
            //lables.add("0级");
            lables.add("1级");
            ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.GrantInvalid, "GrantInvalid", lables);
            return testdata.iterator();
        }    
    
   

    @DataProvider(name = "createOrder")
    public Iterator<Object[]> createOrder() throws IOException {
        List<String> lables = new ArrayList<String>();
        lables.add("1级");
//        lables.add("2级");
        ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.createOrder, "createorder", lables);
        return testdata.iterator();
    }



    @DataProvider(name = "capitalProductGetList")
    public Iterator<Object[]> capitalProductGetList() throws IOException {
        List<String> lables = new ArrayList<String>();
        //lables.add("0级");
        lables.add("1级");
        ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.capitalProduct, "getList", lables);
        return testdata.iterator();
    }

    @DataProvider(name = "capitalProductGetAllProduct")
    public Iterator<Object[]> capitalProductGetAllProduct() throws IOException {
        List<String> lables = new ArrayList<String>();
        //lables.add("0级");
        lables.add("1级");
        ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.capitalProduct, "getAllProduct", lables);
        return testdata.iterator();
    }


    @DataProvider(name = "SaveJobInfo")
    public Iterator<Object[]> SaveJobInfo() throws IOException {
        List<String> lables = new ArrayList<String>();
        //lables.add("0级");
        lables.add("1级");
        ArrayList<Object[]> testdata = ExcelUitls.getTestData(ExcelConstant.JobPut, "JobPut", lables);
        return testdata.iterator();
    }   
    
    
    


}
