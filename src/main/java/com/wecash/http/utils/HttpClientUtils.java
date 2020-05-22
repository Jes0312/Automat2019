package com.wecash.http.utils;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import com.sun.org.apache.xpath.internal.objects.XNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import com.wecash.http.utils.SortJsonArray;
import sun.print.PSPrinterJob;


import java.io.*;
import java.nio.charset.Charset;
import java.sql.ClientInfoStatus;
import java.util.*;


//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import org.json.JSONArray;
//import org.json.JSONException;


import static com.alibaba.fastjson.JSON.parse;
import static com.wecash.http.utils.DataUtils.channelOrderNoCreate;

/**
 * @Author : LeePuvier
 * @CreateTime : 2019/12/24  2:35 PM
 * @ContentUse :
 */
@Slf4j
public class HttpClientUtils {


    /**
     * 请求编码
     */
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 执行HTTP POST请求
     *
     * @param url   url
     * @param param 参数
     * @return
     */
    public static String httpPostWithParam(String url, Map<String, ?> param) {
        CloseableHttpClient client = null;
        try {
            if (url == null || url.trim().length() == 0) {
                throw new Exception("URL is null");
            }
            HttpPost httpPost = new HttpPost(url);
            client = HttpClients.createDefault();
            if (param != null) {
                StringEntity entity = new StringEntity(JSON.toJSONString(param), DEFAULT_CHARSET);//解决中文乱码问题
                entity.setContentEncoding(DEFAULT_CHARSET);
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            HttpResponse resp = client.execute(httpPost);
            if (resp.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(resp.getEntity(), DEFAULT_CHARSET);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(client);
        }
        return null;
    }


    /**
     * @param url
     * @param baseParamJson   基础数据
     * @param expectParamJson 需要替换的数据
     * @return
     */
    public static String httpPostParamWithString(String url, String baseParamJson, String expectParamJson, String replaceFlag, String replaceContent) {

        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpost = new HttpPost(url);
        try {

            if (url == null || url.trim().length() == 0) {
                throw new Exception("URL 为空，请确认配置内容是否正确！！！");
            }
            if (baseParamJson != null) {
                if (expectParamJson != null && expectParamJson.length() != 0) {
                    //替换 baseParamJson Json 中 存在 expectParamJson Json Key 对应的值
                    JSONObject replaceJson = compareJsonReplace(JSON.parse(baseParamJson), JSON.parse(expectParamJson));
//                    log.info(" 替换后提交数据为：" + replaceJson.toString());
                    httpost.setEntity(new StringEntity(replaceJson.toString(), DEFAULT_CHARSET));
                } else if (replaceFlag.equals("ON") && replaceContent != null && replaceContent.length() > 0) {
                    // 用数据库查询结果 替换 baseParamJson 基础数据
                    Map<String, String> map = JSONObject.parseObject(baseParamJson, new TypeReference<Map<String, String>>() {
                    });
                    map.put("channelOrderNo", replaceContent);
                    baseParamJson = JSONObject.toJSONString(map);
                    httpost.setEntity(new StringEntity(baseParamJson, DEFAULT_CHARSET));
                } else {
                    httpost.setEntity(new StringEntity(baseParamJson, DEFAULT_CHARSET));
                }
            }
            // 执行POST请求，并拿到结果
            CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpost);
            // 获取响应结果实体
            HttpEntity entity = httpResponse.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");//DEFAULT_CHARSET
            log.info(" 接口实际返回为：" + result);


            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(closeableHttpClient);
        }
        return null;
    }

    /**
     * @param url
     * @param baseParamJson   基础数据
     * @param expectParamJson 需要替换的数据
     * @return
     */
    public static String httpPostParamWithJSON(String url, JSONObject baseParamJson, String expectParamJson, String replaceFlag, String replaceContent) {

        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpost = new HttpPost(url);
        try {

            if (url == null || url.trim().length() == 0) {
                throw new Exception("URL 为空，请确认配置内容是否正确！！！");
            }
            if (baseParamJson != null) {
                if (expectParamJson != null && expectParamJson.length() != 0) {
                    //替换 baseParamJson Json 中 存在 expectParamJson Json Key 对应的值
                    JSONObject replaceJson = compareJsonReplace(baseParamJson, JSON.parse(expectParamJson));
//                    log.info(" 替换后提交数据为：" + replaceJson.toString());
                    httpost.setEntity(new StringEntity(replaceJson.toString(), DEFAULT_CHARSET));
                } else if (replaceFlag.equals("ON") && replaceContent != null && replaceContent.length() > 0) {
                    // 用数据库查询结果 替换 baseParamJson 基础数据
                    Map<String, String> map = JSONObject.toJavaObject(baseParamJson, Map.class);
                    map.put("channelOrderNo", replaceContent);
                    String baseParamJsonRe = JSONObject.toJSONString(map);
                    httpost.setEntity(new StringEntity(baseParamJsonRe.toString(), DEFAULT_CHARSET));
                } else {
                    httpost.setEntity(new StringEntity(baseParamJson.toString(), DEFAULT_CHARSET));
                }
            }
            // 执行POST请求，并拿到结果
            CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpost);
            // 获取响应结果实体
            HttpEntity entity = httpResponse.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");//DEFAULT_CHARSET
            log.info(" 接口实际返回为：" + result);


            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(closeableHttpClient);
        }
        return null;
    }

    /**
     * 发送 http post 请求，参数以form表单键值对的形式提交。
     */
    public static String httpPostForm(String url, String baseParamJson, String expectParamJson, Map<String, String> headers, String encode) {
        if (encode == null) {
            encode = "utf-8";
        }
        //HttpClients.createDefault()等价于 HttpClientBuilder.create().build();
        CloseableHttpClient closeableHttpClient = null;
        String content = null;
        CloseableHttpResponse httpResponse = null;

        try {

            closeableHttpClient = HttpClients.createDefault();
            HttpPost httpost = new HttpPost(url);

            //设置header
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpost.setHeader(entry.getKey(), entry.getValue());
                }
            }
//            httpost.setHeader("Content-type", "multipart/form-data");
//            httpost.setHeader("Content-type", "application/x-www-form-urlencoded");
            if (baseParamJson != null) {
                if (expectParamJson != null && expectParamJson.length() > 0) {
                    //替换 baseParamJson Json 中 存在 expectParamJson Json Key 对应的值
                    JSONObject replaceJson = compareJsonReplace(JSON.parseObject(baseParamJson), JSON.parseObject(expectParamJson));
//                    log.info(" 替换后提交数据为：" + replaceJson.toString());
                    httpost.setEntity(new StringEntity(replaceJson.toString()));
                } else {
                    httpost.setEntity(new StringEntity(baseParamJson));
                }
            }
            httpResponse = closeableHttpClient.execute(httpost);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            log.info(" 接口实际返回为：" + content);

        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭连接、释放资源
            try {
                httpResponse.close();
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return content;
    }

    /**
     * 执行HTTP GET请求
     *
     * @param url   url
     * @param param 参数
     * @return
     */
    public static String httpGetWithParm(String url, Map<String, ?> param) {
        CloseableHttpClient client = null;
        try {
            if (url == null || url.trim().length() == 0) {
                throw new Exception("URL is null");
            }
            client = HttpClients.createDefault();
            if (param != null) {
                StringBuffer sb = new StringBuffer("?");
                for (String key : param.keySet()) {
                    sb.append(key).append("=").append(param.get(key)).append("&");
                }
                url = url.concat(sb.toString());
                url = url.substring(0, url.length() - 1);
            }

            HttpGet httpGet = new HttpGet(url);
            HttpResponse resp = client.execute(httpGet);

            if (resp.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(resp.getEntity(), DEFAULT_CHARSET);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(client);
        }
        return null;
    }

    /**
     * 执行HTTP GET请求
     *
     * @param url           url
     * @param baseParamJson 参数
     * @return
     */
    public static String httpGetWithJson(String url, String baseParamJson) {
        CloseableHttpClient client = null;
        try {
            if (url == null || url.trim().length() == 0) {
                throw new Exception("URL is null");
            }
            client = HttpClients.createDefault();

            if (baseParamJson != null && baseParamJson.length() != 0) {
                StringBuffer sb = new StringBuffer("?");
                JSONObject jsonObject = JSON.parseObject(baseParamJson);
                for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                    sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");

                }
                url = url.concat(sb.toString());
                url = url.substring(0, url.length() - 1);
                log.info(" URL为：" + url);

            }

            HttpGet httpGet = new HttpGet(url);
//            httpGet.addHeader("Content-Type", "application/json;charset=UTF-8");
            httpGet.addHeader("Content-Type", "text/plain");
//            httpGet.addHeader("Content-Type", "multipart/form-data");
            CloseableHttpResponse response = client.execute(httpGet);
            String result = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);

            log.info(" 接口实际返回为：" + result);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(client);
        }
        return null;
    }


    /**
     * 关闭HTTP请求
     *
     * @param client
     */
    private static void close(CloseableHttpClient client) {
        if (client == null) {
            return;
        }
        try {
            client.close();
        } catch (Exception e) {
        }
    }

    /**
     * 判断第一个Json中Key是否包含第二个Json中Key，包含则替换指定Key 的 Value
     *
     * @param baseObject
     * @param exectObject
     */
    private static JSONObject compareJsonReplace(Object baseObject, Object exectObject) {
        //遍历第二个Json Key，在第一个Json中取对应Key，替换对应的Value
        if (baseObject instanceof JSONObject && exectObject instanceof JSONObject) {
            JSONObject baseJsonObject = (JSONObject) baseObject;
            JSONObject exectJsonObject = (JSONObject) exectObject;
            for (Map.Entry<String, Object> entry : exectJsonObject.entrySet()) {
                // 判断下一级Json格式
                Object o2 = entry.getValue();
                if (o2 instanceof String) {
                    // 随机渠道订单号
                    if (entry.getKey().equals("channelOrderNo")) {
                        String channelOrderNo = channelOrderNoCreate();
                        log.info(" 渠道订单号为: " + channelOrderNo);
                        baseJsonObject.put(entry.getKey(), channelOrderNo);
                        log.info(" 已经替换内容为：Key：" + entry.getKey() + "  ： " + channelOrderNo);
                    } else {
                        log.info(" 期望替换内容为：Key：" + entry.getKey() + "  ： " + entry.getValue());
                        baseJsonObject.put(entry.getKey(), entry.getValue());
                        log.info("已经替换内容：Key：" + entry.getKey() + "  对应的Value为： " + baseJsonObject.get(entry.getKey()));
                    }
                } else if (o2 instanceof JSONArray) {
                    JSONArray baseJsonArray = (JSONArray) baseJsonObject.get(entry.getKey());
                    JSONArray exectJsonArray = (JSONArray) o2;
                    //JsonArray 替换
                    for (int i = 0; i < exectJsonArray.size(); i++) {
//                        log.info("JsonArray中第" + i +"个内容为：" + exectJsonArray.get(i));
                        compareJsonReplace(baseJsonArray.get(i), exectJsonArray.get(i));
                    }
                } else {
                    compareJsonReplace(baseJsonObject.get(entry.getKey()), o2);
                }
            }
            return baseJsonObject;
        }

        //TODO: JsonArray替换逻辑待完善
        return null;
    }

    /**
     * 遍历Json 兼容多层级的Json
     * <p>
     * 需要考虑的就是 JSONObject 和 JSONArray 两种情况，
     * <p>
     * 对这两种情况做处理，采用递归向下遍历，用instanceof判断递归到的类型，做不同处理
     *
     * @param object
     */

    private static void jsonLoop(Object object) {

        if (object instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) object;
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                Object o = entry.getValue();
                if (o instanceof String) {
                    log.info("Key：" + entry.getKey() + "  对应的Value为： " + entry.getValue());
                } else {
                    jsonLoop(o);
                }
            }
        }

        if (object instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) object;
            for (int i = 0; i < jsonArray.size(); i++) {
                jsonLoop(jsonArray.get(i));
            }
        }
    }

    /**
     * 对比实际接口返回与期望接口返回
     *
     * @param string1 实际接口返回
     * @param string2 期望接口返回
     * @return
     */
    public static Boolean compareJsonAssert(String string1, String string2) {
        boolean flag = false;

        List<Boolean> assertResult = new ArrayList<Boolean>();
        Object object1 = parse(string1.trim());
        Object object2 = parse(string2.trim());
        //遍历第二个Json Key，在第一个Json中取对应Key，替换对应的Value
        if (object1 instanceof JSONObject && object2 instanceof JSONObject) {
//            1是真实的
            JSONObject jsonObject1 = (JSONObject) object1;
//            2是excel
            JSONObject jsonObject2 = (JSONObject) object2;
            for (Map.Entry<String, Object> entry : jsonObject2.entrySet()) {
                // 判断下一级Json格式 取出预期值中的value
                Object o2 = entry.getValue();

                log.info(" 期望字段内容为：Key：" + entry.getKey() + "  ： " + entry.getValue());
                log.info(" 实际字段内容为：Key：" + entry.getKey() + "  ： " + jsonObject1.get(entry.getKey()));
                if (o2 instanceof String) {
                    flag = jsonObject1.get(entry.getKey()).toString().equals(entry.getValue().toString());
                    assertResult.add(flag);
                    log.info(" 实际字段内容为：" + jsonObject1.get(entry.getKey()) + "  期望字段内容为：" + entry.getValue() + "  对比结果为：" + flag);
                    if (!flag) {
                        return false;
                    }
                } else if (o2 instanceof Integer) {
                    flag = jsonObject1.get(entry.getKey()).equals(entry.getValue());
                    assertResult.add(flag);
                    log.info(" 实际字段数值为：" + jsonObject1.get(entry.getKey()) + "  期望字段数值为：" + entry.getValue() + "  对比结果为：" + flag);
                    if (!flag) {
                        return false;
                    }
                } else if (o2 == null) {
                    flag = jsonObject1.get(entry.getKey()) == null && entry.getValue() == null;
                    assertResult.add(flag);
                    log.info(" 实际字段内容为：" + jsonObject1.get(entry.getKey()) + "  期望字段内容为：" + entry.getValue() + "  对比结果为：" + flag);
                    if (!flag) {
                        return false;
                    }
                } else if (o2 instanceof JSONArray) {
                    log.info(" 获取到的Json类型为：JsonArray ~ ~ ~");
//                    转换成JSONArray
                    JSONArray jsonArray2 = (JSONArray) o2;
//                    转换成JSONArray  取出相同key的 JSONArray值
                    JSONArray jsonArray1 = (JSONArray) jsonObject1.get(entry.getKey());
//                 Assert.assertEquals(jsonArray1, jsonArray2);

                    if (jsonArray2.size() == 0 && jsonArray2.size() == 0) {
                        log.error(" JsonArray为空 ~ ~ ");
                    }

                    if (jsonArray2.size() != (jsonArray1.size())) {
                        log.error(" JsonArray的长度不相等 ~ ~ ");
                        return false;
                    }

                    if (jsonArray2.size() > 0) {
//                        i是取出的第几个要比较的json串
                        for (int i = 0; i < jsonArray2.size(); i++) {
//                            jsonObject4为待比较的json串
                            JSONObject jsonObject4 = jsonArray2.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                            log.info(" 获取到的Json类型为：array中的JSONObject ~ ~ ");
//                    4是预期的excel
//                          entry2为取出的json中的某个字段
                            for (Map.Entry<String, Object> entry2 : jsonObject4.entrySet()) {

//                                用excel中的每一个 都和实际的返回值做比较，有几个json object
//                                j 代表和第几组在比较
                                for (int j = 0; j < jsonArray1.size(); j++) {
                                    //  3是实际的返回值   jsonObject3为 第j个 jsonobject
                                    JSONObject jsonObject3 = jsonArray1.getJSONObject(j);
                                    flag = jsonObject3.get(entry2.getKey()).toString().equals(entry2.getValue().toString());
//                                    log.info(" flag是什么 "+flag);


                                    if (flag) {
                                        assertResult.add(flag);
                                        log.info("  对比字段为  " + entry2.getKey());
                                        log.info("  实际Json字段内容为： " + jsonObject3.get(entry2.getKey()));
                                        log.info("  期望Json字段内容为： " + entry2.getValue());
                                        log.info("  对比结果为： " + flag);
                                        break;
                                    } else {
                                        log.info("   失败的字段是    " + j + entry2.getKey());
                                        log.info("   对比失败换一个吧  ");


                                    }
                                }
                                if (!flag) {
                                    return false;

                                }
                            }
                        }
                    }


                } else if (o2 instanceof JSONObject) {
                    log.error(" 获取到的Json类型为：JSONObject ~ ~ ");
//                    4是预期的excel
                    JSONObject jsonObject4 = (JSONObject) o2;
//                    3是实际的
                    JSONObject jsonObject3 = (JSONObject) jsonObject1.get(entry.getKey());

                    for (Map.Entry<String, Object> entry2 : jsonObject4.entrySet()) {
                        if (entry2.getValue() instanceof List) {
                            log.error(" 获取到的Json类型为：List ~ ~ ");

                            List<String> list1 = new ArrayList<>();
                            List<String> list2 = new ArrayList<>();

                            list1 = (List<String>) jsonObject3.get(entry2.getKey());
                            list2 = (List<String>) entry2.getValue();


//                            对list做排序
                            Collections.sort(list1);
                            Collections.sort(list2);
//                            判断两个list是否相等
                            flag = list1.containsAll(list2);


                        } else {
                            log.info("  不是list呦  ");

                            String str1 = new String();
                            String str2 = new String();

                            str1 = jsonObject3.get(entry2.getKey()).toString();
//                            预期excel的
                            str2 = entry2.getValue().toString();
//                            对比结果是否相同
                            flag = str1.equals(str2);

                            assertResult.add(flag);
                            log.info("  对比字段为  " + entry2.getKey());
                            log.info("  实际Json字段内容为： " + jsonObject3.get(entry2.getKey()));
                            log.info("  期望Json字段内容为： " + entry2.getValue());
                            log.info("  对比结果为： " + flag);
                        }
                        if (!flag) {
                            return false;

                        }


                    }


//                } else if (o2 instanceof JSObject) {
//
//                    flag = jsonObject1.get(entry.getKey()).equals(entry.getValue());
//                    assertResult.add(flag);
//                    log.info("  对比字段为  " + entry.getKey());
//                    log.info("  实际Json字段内容为： " + jsonObject1.get(entry.getKey()));
//                    log.info("  期望Json字段内容为： " + entry.getValue());
//                    log.info("  对比结果为： " + flag);
////                    return compareJsonAssert(jsonObject1.get(entry.getKey()).toString(), o2.toString());
                } else {
                    log.error(" 请完善Json对比类型 ~ ~ ~");
                }

            }


        }

        //TODO: JsonArray替换逻辑待完善
        return flag;

    }


    public static List getJSONList(String jsonstring, Class cls) {
        List list = new ArrayList();
        try {
            list = JSON.parseArray(jsonstring, cls);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }


    /**
     * 发送http get请求
     */
    public static String httpGet(String url, Map<String, String> headers, String encode) {
//        HttpResponse response = new HttpResponse();
        if (encode == null) {
            encode = "utf-8";
        }
        String content = null;
        //since 4.3 不再使用 DefaultHttpClient
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);

        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
        }

        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity;
            entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
//            response.setBody(content);
//            response.setHeaders(httpResponse.getAllHeaders());
//            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
//            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }


    /**
     * 发送 http post 请求，参数以原生字符串进行提交
     *
     * @param url
     * @param encode
     * @return
     */
    public static String httpPostRaw(String url, String stringJson, Map<String, String> headers, String encode) {
        if (encode == null) {
            encode = "utf-8";
        }
        //HttpClients.createDefault()等价于 HttpClientBuilder.create().build();
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpost = new HttpPost(url);

        //设置header
        httpost.setHeader("Content-type", "application/json");
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        //组织请求参数
        StringEntity stringEntity = new StringEntity(stringJson, encode);
        httpost.setEntity(stringEntity);
        String content = null;
        CloseableHttpResponse httpResponse = null;
        try {
            //响应信息
            httpResponse = closeableHttpClient.execute(httpost);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 发送 http post 请求，支持文件上传
     */
    public static String httpPostFormMultipart(String url, Map<String, String> params, List<File> files, Map<String, String> headers, String encode) {
        if (encode == null) {
            encode = "utf-8";
        }
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpost = new HttpPost(url);

        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
        mEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        mEntityBuilder.setCharset(Charset.forName(encode));

        // 普通参数
        ContentType contentType = ContentType.create("text/plain", Charset.forName(encode));//解决中文乱码
        if (params != null && params.size() > 0) {
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                mEntityBuilder.addTextBody(key, params.get(key), contentType);
            }
        }
        //二进制参数
        if (files != null && files.size() > 0) {
            for (File file : files) {
                mEntityBuilder.addBinaryBody("file", file);
            }
        }
        httpost.setEntity(mEntityBuilder.build());
        String content = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpost);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }


    public void testGetCookies1(String url, String name, String password) {

        // 全局请求设置
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        // 创建cookie store的本地实例
        CookieStore cookieStore = new BasicCookieStore();
        // 创建HttpClient上下文
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);

        // 创建一个HttpClient
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
                .setDefaultCookieStore(cookieStore).build();

        CloseableHttpResponse res = null;

        // 创建本地的HTTP内容
        try {
            try {
                // 创建一个get请求用来获取必要的Cookie，如_xsrf信息
                HttpGet get = new HttpGet("http://www.zhihu.com/");

                res = httpClient.execute(get);
                // 获取常用Cookie,包括_xsrf信息,放在发送请求之后
                System.out.println("访问知乎首页后的获取的常规Cookie:===============");
                for (Cookie c : cookieStore.getCookies()) {
                    System.out.println(c.getName() + ": " + c.getValue());
                }
                res.close();

                // 构造post数据
                List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
                valuePairs.add(new BasicNameValuePair("email", name));
                valuePairs.add(new BasicNameValuePair("password", password));
                valuePairs.add(new BasicNameValuePair("remember_me", "true"));
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
                entity.setContentType("application/x-www-form-urlencoded");

                // 创建一个post请求
                HttpPost post = new HttpPost("https://www.zhihu.com/login/email");
                // 注入post数据
                post.setEntity(entity);
                res = httpClient.execute(post);

                // 打印响应信息，查看是否登陆是否成功
                System.out.println("打印响应信息===========");
                res.close();
                for (Cookie c : context.getCookieStore().getCookies()) {
                    System.out.println(c.getName() + ": " + c.getValue());
                }

                // 构造一个新的get请求，用来测试登录是否成功
                HttpGet newGet = new HttpGet("http://www.zhihu.com/question/following");
                res = httpClient.execute(newGet, context);
                String content = EntityUtils.toString(res.getEntity());
                System.out.println("登陆成功后访问的页面===============");
                System.out.println(content);
                res.close();

            } finally {
                httpClient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String postWithParamsForString(String url, List<NameValuePair> params) {
        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String result = "";
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            HttpResponse response = client.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
                log.info("--> 接口实际返回为：" + result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String trasferToData(String url, Map<String, ?> param) {

        if (url != null && param != null) {
            StringBuffer sb = new StringBuffer("?");
            for (String key : param.keySet()) {
                sb.append(key).append("=").append(param.get(key)).append("&");
            }
            url = url.concat(sb.toString());
            url = url.substring(0, url.length() - 1);
            return url;
        }

        return null;
    }

    public static String postWithParams(String url, Map<String, ?> param) {
        url = trasferToData(url, param);
        log.info("--> 传入参数为：" + url);
        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String result = "";
        try {
//            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            HttpResponse response = client.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
                log.info("--> 接口实际返回为：" + result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 执行HTTP POST请求
     *
     * @param url       url
     * @param jsonParam json格式参数
     * @return
     */
    public static String httpJSONPost(String url, String jsonParam) {
        CloseableHttpClient client = null;
        try {
            if (url == null || url.trim().length() == 0) {
                throw new Exception("URL is null");
            }
            HttpPost httpPost = new HttpPost(url);
            client = HttpClients.createDefault();
            if (jsonParam != null) {
                StringEntity entity = new StringEntity(jsonParam, DEFAULT_CHARSET);//解决中文乱码问题
                entity.setContentEncoding(DEFAULT_CHARSET);
                entity.setContentType("application/json");
                httpPost.setHeader("token", "qsL0FxqzPwuApNb9");
                httpPost.setHeader("fromTenantId", "1001");


                httpPost.setEntity(entity);
            }
            HttpResponse resp = client.execute(httpPost);
            if (resp.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(resp.getEntity(), DEFAULT_CHARSET);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(client);
        }
        return null;
    }

//判断 只有jsonobject的
    public static void compareJsonAssert1(String string1, String string2) {
        boolean flag = false;

        List<Boolean> assertResult = new ArrayList<Boolean>();
        Object object1 = parse(string1.trim());
        Object object2 = parse(string2.trim());
        //遍历第二个Json Key，在第一个Json中取对应Key，替换对应的Value
        if (object1 instanceof JSONObject && object2 instanceof JSONObject) {
//            1是真实的
            JSONObject jsonObject1 = (JSONObject) object1;
//            2是excel
            JSONObject jsonObject2 = (JSONObject) object2;
            for (Map.Entry<String, Object> entry : jsonObject2.entrySet()) {
                // 判断下一级Json格式 取出预期值中的value
                Object o2 = entry.getValue();
                Object keylayer1ex = entry.getKey();
                Object valuelayer1ex = entry.getValue();
                Object valuelayer1ra = jsonObject1.get(keylayer1ex);
                log.info(" 期望字段内容为：Key：" + keylayer1ex + "  ： " + valuelayer1ex);
                log.info(" 实际字段内容为：Key：" + keylayer1ex + "  ： " + valuelayer1ra);
                if (o2 instanceof JSONObject) {
                    log.error(" 获取到的Json类型为：JSONObject ~ ~ ");
//                    4是预期的excel
                    JSONObject jsonObject4 = (JSONObject) o2;
//                    3是实际的
                    JSONObject jsonObject3 = (JSONObject) valuelayer1ra;
                    for (Map.Entry<String, Object> entry2 : jsonObject4.entrySet()) {
                        if (entry2.getValue() instanceof List) {
                            Collections.sort((List<String>) entry2.getValue());
                            Collections.sort((List<String>) jsonObject3.get(entry2.getKey()));
                        }
                    }
//
                    Assert.assertEquals(valuelayer1ex, valuelayer1ra);
                } else {
                    log.info(" 非jsonobject，非jsonarray ~ ~ ");

                    Assert.assertEquals(valuelayer1ex, valuelayer1ra);
                }
            }
        }
    }


    public static void compareJsonAssert1(String string1, String string2,String string3) {
        boolean flag = false;

        List<Boolean> assertResult = new ArrayList<Boolean>();
        Object object1 = parse(string1.trim());
        Object object2 = parse(string2.trim());
        //遍历第二个Json Key，在第一个Json中取对应Key，替换对应的Value
        if (object1 instanceof JSONObject && object2 instanceof JSONObject) {
//            1是真实的
            JSONObject jsonObject1 = (JSONObject) object1;
//            2是excel
            JSONObject jsonObject2 = (JSONObject) object2;
            for (Map.Entry<String, Object> entry : jsonObject2.entrySet()) {
                // 判断下一级Json格式 取出预期值中的value
                Object o2 = entry.getValue();
                Object keylayer1ex = entry.getKey();
                Object valuelayer1ex = entry.getValue();
                Object valuelayer1ra = jsonObject1.get(keylayer1ex);

                log.info(" 期望字段内容为：Key：" + keylayer1ex + "  ： " + valuelayer1ex);
                log.info(" 实际字段内容为：Key：" + keylayer1ex + "  ： " + valuelayer1ra);
                if (o2 instanceof JSONObject) {
                    log.info(" 获取到的Json类型为：JSONObject ~ ~ ");
//                    4是预期的excel
                    JSONObject jsonObject4 = (JSONObject) o2;
//                    3是实际的
                    JSONObject jsonObject3 = (JSONObject) valuelayer1ra;
                    for (Map.Entry<String, Object> entry2 : jsonObject4.entrySet()) {
                        if (entry2.getValue() instanceof List) {
                            log.info(" 获取到的Json类型为：JSONObject中有List ~ ~ ");
                            Collections.sort((List<String>) entry2.getValue());
                            Collections.sort((List<String>) jsonObject3.get(entry2.getKey()));
                        }
                    }
                    Assert.assertEquals(valuelayer1ex, valuelayer1ra);
                } else if (o2 instanceof JSONArray) {
                    log.info(" 获取到的Json类型为：JsonArray ~ ~ ~");
                    JSONArray jsonArray2 = (JSONArray) o2;
//                    转换成JSONArray  取出相同key的 JSONArray值
                    JSONArray jsonArray1 = (JSONArray) jsonObject1.get(entry.getKey());
                    if (jsonArray2.size() == 0 && jsonArray2.size() == 0) {
                        log.error(" JsonArray为空 ~ ~ ");
                    }
                    if (jsonArray2.size() != (jsonArray1.size())) {
                        log.error(" JsonArray的长度不相等 ~ ~ ");
                    }
                    JSONArray jsonArraySort2 = SortJsonArray.jsonArraySort((JSONArray) o2,string3);
                    JSONArray jsonArraySort1 = SortJsonArray.jsonArraySort( jsonArray1,string3);
                    log.info(" 期望字段内容排序后为：Key：" + keylayer1ex + "  ： " + jsonArraySort2);
                    log.info(" 实际字段内容排序后为：Key：" + keylayer1ex + "  ： " + jsonArraySort1);
                    Assert.assertEquals(jsonArraySort2, jsonArraySort1);
                }
                else {
                    log.error(" 非jsonobject，非jsonarray ~ ~ ~ ");

                    Assert.assertEquals(valuelayer1ex, valuelayer1ra);
                }
            }
        }

    }

}