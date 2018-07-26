package thread;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Filter;
import util.UrlUtil;
import vo.PageVo;

import java.util.concurrent.BlockingQueue;


/**
 * 通过网易推荐的自媒体列表，来获取的的自媒体列表（其爬取的自媒体号较少，只限定于粉丝数较多的自媒体号）
 */
@Deprecated
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
                    JSONObject jsonObject = JSONObject.fromObject(UrlUtil.doGet(dataURL));

                    //获取其公众号网址列表
                    JSONArray jsonArray = jsonObject.getJSONArray("recommendList");

                    for (int i = 0; i < jsonArray.size(); i++)
                    {

                        PageVo vo = new PageVo();
                        // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        JSONObject job = jsonArray.getJSONObject(i);
                        // 得到 每个对象中的id
                        String ID = job.getString("ename");
                        vo.setId(ID);
                        /**
                         * 对自媒体去重
                         */
                        if (filter.contain(ID))
                        {
                            System.out.println("爬取到一条重复"+ID);

                            continue;
                        }
                        System.out.println("添加一条"+ID);
                        queue.put(vo);
                    }
                }

            }catch (Exception e)
            {

            }
        }
    }
}
