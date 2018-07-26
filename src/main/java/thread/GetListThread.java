package thread;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Filter;
import util.UrlUtil;
import vo.PageVo;

import java.util.List;
import java.util.concurrent.BlockingQueue;


/**
 * 通过网易app搜索引擎获取自媒体名单
 * @author zf
 */
public class GetListThread implements Runnable
{
    BlockingQueue<PageVo> queue;
    private Filter filter;
    private List<String> stringList;
    private static final Logger LOGGER =LoggerFactory.getLogger(GetListThread.class);


    /**
     * 不传入将遍历的字符集合时，默认采用遍历gb2313字符集的做法
     *
     * @param queue
     */
    public GetListThread(BlockingQueue<PageVo> queue)
    {
        this.queue = queue;
        filter = new Filter();
        stringList=UrlUtil.getGB2312List();
    }

    /**
     * 将遍历传入的关键词字符集合
     *
     * @param queue
     * @param stringList 关键词字符集合
     */
    public GetListThread(BlockingQueue<PageVo> queue, List<String> stringList)
    {
        this.queue = queue;
        filter = new Filter();
        this.stringList=stringList;
    }

    @Override
    public void run()
    {

        //对搜索关键词进行遍历
        for (String s : stringList)
        {

            int num = 0;
            String data = null;
            //对单个关键词的结果进行遍历
            while (true)
            {
                String httpUrl = "http://c.m.163.com/nc/topicset/v5/searchByPage/android/" + num + "-20.html";
                num += 20;
                try
                {
                    data = UrlUtil.doPost(httpUrl, "keyWord=" + UrlUtil.getEncodedBase64(s));
                    JSONObject jsonObject = JSONObject.fromObject(data);
                    //获取其他公众号网址
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    //当结果页结束时，换下一个搜索词
                    if (jsonArray.size() == 0)
                    {
                        break;
                    }
                    for (int i = 0; i < jsonArray.size(); i++)
                    {

                        PageVo vo = new PageVo();

                        // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        JSONObject job = jsonArray.getJSONObject(i);
                        // 得到自媒体id
                        String ID = job.getString("tid");
                        vo.setId(ID);

                        /**
                         * 对网址去重
                         */
                        if (filter.contain(ID))
                        {
                            //LOGGER.debug("爬取到一条重复记录"+ID);
                            continue;
                        }
                        vo.setUrl(ID);
                        queue.put(vo);
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            LOGGER.debug("已爬取"+filter.getUrlNum()+"条数据，队列容量="+queue.remainingCapacity());
        }
        LOGGER.debug("线程" + Thread.currentThread().getName()+"终止");
    }


}

