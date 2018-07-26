import net.sf.json.JSONObject;

public class Test
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        JSONObject download_param =new JSONObject();
        JSONObject headers=new JSONObject();
        headers.put("headers",setHeaders("*/*",
                      "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3",
                      "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0"));
        download_param.put("download_param",headers);
        download_param.put("http_proxy", "");
        download_param.put("http_proxy_password", "");
        download_param.put("http_proxy_port", 0);
        download_param.put("http_proxy_user", "");
        download_param.put("max_redirect", 5);
        //还有一些。。。都是重复工作


        System.out.println(download_param.toString());





    }


    private static JSONObject setHeaders(String Accept, String AcceptLanguage, String UserAgent)
    {
        JSONObject head=new JSONObject();
        head.put("Accept",Accept);
        head.put("Accept-Language",AcceptLanguage);
        head.put("User-Agent",UserAgent);
        return head;
    }


}
