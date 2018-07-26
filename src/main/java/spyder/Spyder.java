package spyder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.GetListThread;
import thread.GetMsgThread;
import util.FileUtil;
import util.UrlUtil;
import vo.PageVo;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * 网易新闻app爬虫
 * @author zf
 */
public class Spyder
{
    private static String filePath=null;
    private static int queueCapacity=0;
    private static final Logger LOGGER = LoggerFactory.getLogger(Spyder.class);


    public static void main(String[] args)
    {

        List<String> stringList=UrlUtil.getGB2312List();

        //初始化
        ini();
        BlockingQueue<PageVo> queue=new LinkedBlockingDeque<PageVo>(queueCapacity);

        GetMsgThread GetMsgThread =new GetMsgThread(queue,filePath);

        //每个目录线程含多少搜索关键词
        int onePageNum=stringList.size()/100;
        for (int i=0;i<stringList.size();i+=onePageNum)
        {
            GetListThread GetListThread =new GetListThread(
                    queue,stringList.subList(i,Math.min(i+onePageNum,stringList.size())));
            new Thread(GetListThread,i+"号目录线程").start();
        }

        for (int i=0;i<800;i++)
        {
            new  Thread(GetMsgThread,i+"号信息线程").start();
        }
    }


    /**
     * 初次化，读取配置文件
     */
    private static void ini()
    {
        Properties properties = FileUtil.LoadConfig();

        try
        {
            filePath=properties.getProperty("FilePath");
            queueCapacity= Integer.parseInt(properties.getProperty("queueCapacity"));
        }catch (Exception e)
        {
            LOGGER.error("配置文件数据有误");
            System.exit(-1);
        }
    }


}


