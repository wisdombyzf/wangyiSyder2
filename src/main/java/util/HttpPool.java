package util;

import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

/**
 * httpclient连接池。。。解决HttpClient引起的TCP连接数高问题
 *
 */
public class HttpPool
{

    static CloseableHttpClient httpClient = null;

    public static synchronized CloseableHttpClient getHttpClient()
    {

        if (httpClient == null)
        {
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
            // 连接池最大连接数  
            cm.setMaxTotal(200);
            // 单条链路最大连接数（一个ip+一个端口 是一个链路）  
            cm.setDefaultMaxPerRoute(100);
            // 指定某条链路的最大连接数  

            ConnectionKeepAliveStrategy kaStrategy = new DefaultConnectionKeepAliveStrategy()
            {
                @Override
                public long getKeepAliveDuration(HttpResponse response, HttpContext context)
                {
                    long keepAlive = super.getKeepAliveDuration(response, context);
                    if (keepAlive == -1)
                    {
                        keepAlive = 60000;
                    }
                    return keepAlive;
                }

            };

            httpClient = HttpClients.custom().setConnectionManager(cm).setKeepAliveStrategy(kaStrategy).build();
        }

        return httpClient;
    }

}  