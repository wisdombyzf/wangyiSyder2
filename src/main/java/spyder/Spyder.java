package spyder;

import net.sf.json.JSONObject;
import thread.FirstThread;
import thread.SecondThread;
import vo.PageVo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * 网易新闻app爬虫
 */
public class Spyder
{


    public static void main(String[] args) throws IOException
    {
        //初始化
        BlockingQueue<PageVo> queue=new LinkedBlockingDeque<PageVo>(10000);
        PageVo vo=new PageVo();
        vo.setUrl("http://c.m.163.com/nc/subscribe/abstract/T1436178714849.html");
        PageVo vo2=new PageVo();
        vo2.setUrl("http://c.m.163.com/nc/subscribe/abstract/T1435715166386.html");
//        PageVo vo3=new PageVo();
//        vo3.setUrl();

//        try
//        {
//            queue.put(vo);
//            queue.put(vo2);
//
//        } catch (InterruptedException e)
//        {
//            e.printStackTrace();
//        }
        FirstThread firstThread=new FirstThread(queue);
        SecondThread secondThread=new SecondThread(queue);
        new Thread(secondThread,"目录线程").start();

        for (int i=0;i<10;i++)
        {
            new  Thread(firstThread,i+"号线程").start();
        }



    }




    /**
     * 将json字符串转map
     *
     * @param object
     * @return
     */
    private Map<String, String> toMap(Object object)
    {
        Map<String, String> data = new HashMap<String, String>();
        // 将json字符串转换成jsonObject
        JSONObject jsonObject = JSONObject.fromObject(object);
        Iterator ite = jsonObject.keys();
        // 遍历jsonObject数据,添加到Map对象
        while (ite.hasNext())
        {
            String key = ite.next().toString();
            String value = jsonObject.get(key).toString();
            data.put(key, value);
        }
        // 或者直接将 jsonObject赋值给Map
        // data = jsonObject;
        return data;
    }


}


