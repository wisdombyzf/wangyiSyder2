package thread;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.FileUtil;
import util.Filter;
import util.URLUtil;
import vo.PageVo;

import java.util.concurrent.BlockingQueue;


/**
 * 一号线程，爬取对应自媒体的简介，名称，id等信息
 */
public class FirstThread implements Runnable
{
    BlockingQueue<PageVo> queue;
    Filter filter;
    //TODO 测试时设置上限
    static int MaxNum = 0;
    final String filePath="D:/wangyiApp/";



    public FirstThread(BlockingQueue<PageVo> queue)
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
                PageVo pageVo = queue.take();
                JSONObject jsonObject = JSONObject.fromObject(URLUtil.getJson(pageVo.getUrl()));

                String desc = (String) jsonObject.get("desc");
                System.out.println("已添加一条");
                pageVo.setDescription(desc);
                //储存
                FileUtil.save(pageVo,filePath);

                //获取其他公众号网址
                JSONArray jsonArray = jsonObject.getJSONArray("abstractList");

                for (int i = 0; i < jsonArray.size(); i++)
                {
                    System.out.println("线程"+Thread.currentThread().getName()+"容量"+queue.remainingCapacity());
                    //当队列剩余容量小于2时，丢弃该公众号网页
                    if (queue.remainingCapacity() < 2)
                    {

                    }
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
                    vo.setAvatarsUrl(job.getString("img"));
                    //订阅数
                    vo.setFanNum(job.getString("subnum"));

                    queue.put(vo);

                    System.out.println();
                }

            }catch (Exception e)
            {

            }
        }
    }
}