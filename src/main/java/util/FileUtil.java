package util;

import net.sf.json.JSONObject;
import vo.PageVo;

import java.io.*;

/**
 * 文件工具类
 */
public class FileUtil
{
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
            }

        }
        return file;
    }



    public static boolean writeByteArrayToFile(File file, byte[] bytes)
    {
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
            fos.write(bytes);
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
        JSONObject jsonObject=JSONObject.fromObject(vo);
        FileUtil.newFile(filePath+vo.getName());
        File file=new File(filePath+vo.getName());
        FileUtil.writeByteArrayToFile(file,jsonObject.toString().getBytes());
    }

}




