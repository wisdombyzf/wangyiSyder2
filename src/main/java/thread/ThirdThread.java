package thread;

import util.Filter;
import vo.PageVo;
import java.util.concurrent.BlockingQueue;


/**
 * 爬取自媒体简介
 */
public class ThirdThread implements Runnable
{BlockingQueue<PageVo> queue;
    Filter filter;
    //TODO 测试时设置上限
    static int MaxNum = 0;
    final String filePath="D:/wangyiApp/";



    public ThirdThread(BlockingQueue<PageVo> queue)
    {
        this.queue = queue;
        filter = new Filter();
    }

    @Override
    public void run()
    {


    }
}
