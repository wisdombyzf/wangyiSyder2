package util;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import vo.PageVo;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 文件工具类
 */
public class FileUtil
{

    static private Date date=new Date();
    static private long time=date.getTime();
    static private String fileName;
    private static Logger logger=Logger.getLogger(FileUtil.class);


    /**
     * 新建目录
     * @param path
     */
    public static void mkdir(String path)
    {
        File fileDir = new File(path);
        if (!(fileDir.isDirectory()))
        {
            fileDir.mkdirs();
        }
    }

    /**
     * 新建文件
     * @param pathname
     * @return
     */
    public static File newFile(String pathname)
    {
        File file = new File(pathname);
        File fileParent = file.getParentFile();
        if (!file.exists())
        {
            try
            {
                if (!fileParent.exists())
                {
                    fileParent.mkdirs();
                }
                file.createNewFile();
            } catch (IOException e)
            {
                logger.error("新建文件出错");
            }

        }
        return file;
    }



    public static boolean writeByteArrayToFile(File file, byte[] bytes)
    {
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file,true);

            /**
             *  此处代码块需同步，否则可能出现两个json在同一行的情况
             */
            synchronized(FileUtil.class)
            {
                fos.write(bytes);
                //换行写入
                fos.write("\r\n".getBytes());
            }

        } catch (FileNotFoundException e)
        {
            return false;
        } catch (IOException e)
        {
            return false;
        } finally
        {
            try
            {
                if (fos != null)
                {
                    fos.close();
                }
            } catch (IOException e)
            {
            }
        }
        return true;
    }

    public static void readByteArrayFromFile(File file, byte[] bytes)
    {
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);
            fis.read(bytes);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (fis != null)
                {
                    fis.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }


    public void getPath()
    {
        File directory = new File("");// 参数为空
        String courseFile;
        try
        {
            courseFile = directory.getCanonicalPath();
            System.out.println(courseFile);

            String xmlpath = this.getClass().getClassLoader().getResource("").getPath();
            System.out.println(xmlpath);

        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * 将爬取的内容存为json格式字符串文件
     *
     */
    public static void save(PageVo vo,String filePath)
    {
        //每分钟以上的时间间隔新建一个新的储存文件
        date=new Date();
        if (date.getTime()>(time+1000*60))
        {
            time=date.getTime();
        }

        //时间格式化
        SimpleDateFormat ft = new SimpleDateFormat ("yyyyMMddhhmmss");
        fileName=ft.format(time).toString()+".txt";
        FileUtil.newFile(filePath+fileName);


        JSONObject jsonObject=JSONObject.fromObject(vo);
        File file=new File(filePath+fileName);
        FileUtil.writeByteArrayToFile(file,jsonObject.toString().getBytes());
    }


    /**
     * 通过ClassLoader方式加载配置文件....不能用单纯用getClass或是fileinputstream。。。具体原因与编译后文件结构和编译前不同有关
     * 这种加载方式：
     * 1.可在eclipse里运行时得到配置文件路径；
     * 2.亦可在把项目打成jar包运行时，得到配置文件路径；
     */
    public static Properties LoadConfig()
    {
        Properties p = new Properties();
        try
        {
            InputStream in = FileUtil.class.getClassLoader().getResourceAsStream("spider.properties");
            p.load(in);
        } catch (IOException e)
        {
            logger.error("加载配置文件出错");
            e.printStackTrace();
        }
        return p;
    }
}




