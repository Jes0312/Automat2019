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
	
	
    @DataProvider(name = "userCeateInfo")
    public Iterator<Object[]> userCeateInfo() throws IOException {
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




}
