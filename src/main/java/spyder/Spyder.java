package spyder;

import org.apache.log4j.Logger;
import thread.getListThread;
import thread.getMsgThread;
import util.FileUtil;
import util.URLUtil;
import vo.PageVo;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * 网易新闻app爬虫
 */
public class Spyder
{
    private static String filePath=null;
    private static int queueCapacity=0;
    private static Logger logger = Logger.getLogger(Spyder.class);


    public static void main(String[] args) throws IOException
    {

        List<String> stringList=URLUtil.getGB2312List();

        //初始化
        ini();
        BlockingQueue<PageVo> queue=new LinkedBlockingDeque<PageVo>(queueCapacity);

        getMsgThread getMsgThread =new getMsgThread(queue,filePath);

        //每个目录线程含多少搜索关键词
        int onePageNum=stringList.size()/100;
        for (int i=0;i<stringList.size();i+=onePageNum)
        {
            getListThread getListThread =new getListThread(
                    queue,stringList.subList(i,Math.min(i+onePageNum,stringList.size())));
            new Thread(getListThread,i+"号目录线程").start();
        }

        for (int i=0;i<800;i++)
        {
            new  Thread(getMsgThread,i+"号信息线程").start();
        }
    }


    /**
     * 初次化，读取配置文件
     */
    public static void ini()
    {
        Properties properties = FileUtil.LoadConfig();

        try
        {
            filePath=properties.getProperty("FilePath");
            queueCapacity= Integer.parseInt(properties.getProperty("queueCapacity"));
        }catch (Exception e)
        {
            logger.error("配置文件数据有误");
            System.exit(-1);
        }
    }


}


