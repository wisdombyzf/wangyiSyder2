package thread;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Filter;
import util.URLUtil;
import vo.PageVo;

import java.util.concurrent.BlockingQueue;


/**
 * 二号线程，爬取自媒体列表
 */
public class SecondThread implements Runnable
{
    BlockingQueue<PageVo> queue;
    Filter filter;
    //TODO 测试时设置上限
    static int MaxNum = 0;
    final String filePath="D:/wangyiApp/";



    public SecondThread(BlockingQueue<PageVo> queue)
    {
        this.queue = queue;
        filter = new Filter();
    }

    @Override
    public void run()
    {
        while (true)
        {
            //TODO 测试用
            synchronized (this)
            {
                MaxNum++;
            }


            try
            {
                for (int num=0;num<250;num+=20)
                {
                    String dataURL="http://c.m.163.com/nc/topicset/v6/recommend/android/"+num+"-20.html";
                    JSONObject jsonObject = JSONObject.fromObject(URLUtil.getJson(dataURL));

                    //获取其公众号网址列表
                    JSONArray jsonArray = jsonObject.getJSONArray("recommendList");

                    for (int i = 0; i < jsonArray.size(); i++)
                    {

                        PageVo vo = new PageVo();

                        // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        JSONObject job = jsonArray.getJSONObject(i);
                        // 得到 每个对象中的属性值
                        String ID = job.getString("ename");
                        vo.setId(ID);
                        vo.setName(job.getString("tname"));
                        //构造指向该公众号简介的网址
                        String url = "http://c.m.163.com/nc/subscribe/abstract/" + ID + ".html";

                        /**
                         * 对网址去重
                         */
                        if (filter.Contain(url))
                        {
                            System.out.println("爬取到一条重复");
                            continue;
                        }
                        vo.setUrl(url);

                        //图片url
                        vo.setAvatarsUrl(job.getString("topic_icons"));
                        //订阅数
                        vo.setFanNum(job.getString("subnum"));

                        queue.put(vo);
                    }
                }

            }catch (Exception e)
            {

            }
        }
    }
}
