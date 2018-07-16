package util;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


/**
 * 根据对应URL下载对应json
 */
public class URLUtil
{

    /**
     * 获取对应url的json数据
     *
     * @param httpUrl
     * @return
     */
    public static String getJson(String httpUrl)
    {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
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
                // 服务端返回数据的长度
                //System.out.println("内容长度：" + content.length());
                result.append(content.toString());
                //System.out.println(content.toString());
            }
        } catch (ClientProtocolException e)
        {
            System.out.println("网页请求错误");
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
            // 相当于关闭浏览器
            try
            {
                //TODO 多线程时待复用
                httpclient.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return result.toString();
    }
}









