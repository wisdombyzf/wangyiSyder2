package util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;


/**
 * 根据对应URL下载对应json
 * @author zf
 */
public class UrlUtil
{

    private static final Logger logger=LoggerFactory.getLogger(UrlUtil.class);
    /**
     * 通过get方法获取对应url的json数据
     *
     * @param httpUrl
     * @return 返回的json字符串
     */
    public static String doGet(String httpUrl)
    {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpPool.getHttpClient();
        StringBuffer result = new StringBuffer();
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(httpUrl);
        CloseableHttpResponse response = null;
        try
        {
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200)
            {
                // 获取服务端返回的数据
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                result.append(content.toString());
            }
        } catch (ClientProtocolException e)
        {
            logger.warn("网页请求错误");
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (response != null)
            {
                try
                {
                    response.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }


    /**
     * post请求（用于请求json格式的参数）
     *
     * @param url    请求网址
     * @param params requestBody
     * @return
     */
    public static String doPost(String url, String params) throws Exception
    {
        CloseableHttpClient httpclient=HttpPool.getHttpClient();
        // 创建httpPost
        HttpPost httpPost = new HttpPost(url);
        //设置请求头
        httpPost.setHeader("User-C", "6K6i6ZiF");
        httpPost.setHeader("Add-To-Queue-Millis", "1531722537");
        httpPost.setHeader("User-D", "JyH4qmC8TJ8zr9a3a/");
        httpPost.setHeader("httpDNSIP", "59.111.160.220");
        httpPost.setHeader("User-L", "GmM8cbVMDunx");
        httpPost.setHeader("httpDNSIP", "GmM8cbVMDunx");
        httpPost.setHeader("User-LC", "yCldziPlrx");
        httpPost.setHeader("User-N", "ELwR6f4rhTcUqICF");
        httpPost.setHeader("data4-Sent-Millis", "1531722537490");
        httpPost.setHeader("User-Agent", " NewsApp/39.1 Android/8.1.0 (xiaomi/Redmi Note 5)");
        httpPost.setHeader("X-NR-Trace-Id", "1531722537491_76902487");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.setHeader("Connection", "Keep-Alive");
        httpPost.setHeader("Accept-Encoding", "gzip");
        httpPost.setHeader("Host", "c.m.163.com");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(params, charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;
        try
        {

            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK)
            {
                HttpEntity responseEntity = response.getEntity();
                return EntityUtils.toString(responseEntity);
            } else
            {
                logger.warn("");
            }
        } finally
        {
            if (response != null)
            {
                try
                {
                    response.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 获取gb2312字符集的汉字列表
     * @return
     */
    public static  List<String>  getGB2312List()
    {
        List<String> result=new LinkedList<String>();

        //遍历数字集
        for (int i=0;i<10;i++)
        {
            result.add(""+i);
        }

        byte[] tmp=new byte[1];
        //遍历字母集
        for (int i=(int)'A';i<=(int)'z';i++)
        {
            tmp[0]=(byte) i;
            String b= null;
            try
            {
                b = new String(tmp,"ASCII");
            } catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            result.add(b);
        }

        StringBuffer buffer=new StringBuffer();
        buffer.append("0123456789");
        buffer.append("qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM");
        //对gb2312字符集遍历
        try
        {
            //遍历gb2312汉字编码分区
            for (int i=0xB0;i<0xF7;i++)
            {
                //遍历每个分区中的汉字
                for (int j=0xA1;j<0xFF;j++)
                {
                    byte[] bytes=new byte[2];
                    bytes[0]= (byte) i;
                    bytes[1]= (byte) j;

                    String s = new String(bytes, "gb2312");
                    result.add(s);
                }

            }
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return result;
    }


    /**
     *  通过jdk8的util类中的Base64类实现 base64编码
     * @param plainText
     * @return
     */
    public static String getEncodedBase64(String plainText)
    {
        String encoded = null;
        try
        {
            byte[] bytes = plainText.getBytes("UTF-8");
            encoded = Base64.getEncoder().encodeToString(bytes);
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return encoded;
    }
}









