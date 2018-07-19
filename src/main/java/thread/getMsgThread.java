package thread;

import net.sf.json.JSONObject;
import util.FileUtil;
import util.Filter;
import util.URLUtil;
import vo.PageVo;

import java.util.concurrent.BlockingQueue;




/**
 * 一号线程，爬取对应自媒体的简介，名称等信息
 *
 * 简介获取网址：  http://c.m.163.com/nc/subscribe/abstract/自媒体id.html
 * 名称，别名等信息获取网址:  http://c.m.163.com/nc/subscribe/v2/topic/自媒体id.html
 */
public class getMsgThread implements Runnable
{
    BlockingQueue<PageVo> queue;
    Filter filter;
    //文件储存的路径
    private String filePath="";



    public getMsgThread(BlockingQueue<PageVo> queue,String filePath)
    {
        this.queue = queue;
        filter = new Filter();
        this.filePath=filePath;
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                PageVo pageVo = queue.take();
                //构建简介获取网址
                String  descUrl="http://c.m.163.com/nc/subscribe/abstract/"+pageVo.getId()+".html";
                JSONObject jsonObject = JSONObject.fromObject(URLUtil.doGet(descUrl));
                String desc = jsonObject.getString("desc");
                pageVo.setDescription(desc);

                //构建名称等信息获取网址
                String msgUrl="http://c.m.163.com/nc/subscribe/v2/topic/"+pageVo.getId()+".html";
                JSONObject jsonObject2 = JSONObject.fromObject(URLUtil.doGet(msgUrl));
                JSONObject msg=jsonObject2.getJSONObject("subscribe_info");
                pageVo.setAlias(msg.getString("alias"));
                pageVo.setAvatarUrl(msg.getString("topic_icons"));
                pageVo.setName(msg.getString("tname"));

                //将subnum中的xx万转为数字
                String tmp=msg.getString("subnum");
                if (tmp.length()>1&&tmp.substring(tmp.length()-1).equals("万"))
                {
                    float tmp2= Float.parseFloat(tmp.substring(0,tmp.length()-1));
                    pageVo.setFanNum(String.valueOf((int) (tmp2*10000)));
                }else
                {
                    pageVo.setFanNum(tmp);
                }
                pageVo.setUrl("https://c.m.163.com/news/sub/"+pageVo.getId()+".html?spss=newsapp");
                //标注为网易
                pageVo.setSite("netease");



                //储存
                FileUtil.save(pageVo,filePath);

//                //获取其他公众号网址
//                JSONArray jsonArray = jsonObject.getJSONArray("abstractList");
//
//                for (int i = 0; i < jsonArray.size(); i++)
//                {
//                    System.out.println("线程"+Thread.currentThread().getName()+"容量"+queue.remainingCapacity());
//                    //当队列剩余容量小于2时，丢弃该公众号网页
//                    if (queue.remainingCapacity() < 2)
//                    {
//
//                    }
//                    PageVo vo = new PageVo();
//
//                    // 遍历 jsonarray 数组，把每一个对象转成 json 对象
//                    JSONObject job = jsonArray.getJSONObject(i);
//                    // 得到 每个对象中的属性值
//                    String ID = job.getString("ename");
//                    vo.setId(ID);
//
//
//                    /**
//                     * 对网址去重
//                     */
//                    if (filter.Contain(ID))
//                    {
//                        System.out.println("爬取到一条重复");
//                        continue;
//                    }
//                    vo.setUrl(ID);
//                    queue.put(vo);
//
//                    System.out.println();
//                }

            }catch (Exception e)
            {

            }
        }
    }
}