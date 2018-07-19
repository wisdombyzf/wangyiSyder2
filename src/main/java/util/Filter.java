package util;

import net.sf.json.JSONObject;

import java.util.*;

/**
 * url去重，暂时利用set
 */
public class Filter
{
    //利用synchronizedSet使set线程安全
    private static Set<String> set= Collections.synchronizedSet(new HashSet<String>());

    /**
     * url去重....暂时用set
     *
     * @param url
     * @return
     */
    public boolean Contain(String url)
    {
        if (set.contains(url))
        {
            return true;
        } else
        {
            set.add(url);
            return false;
        }
    }

    public int getUrlNum()
    {
        return set.size();
    }



    /**
     * 将json字符串转map
     *
     * @param object
     * @return
     */
    private Map<String, String> toMap(Object object)
    {
        Map<String, String> data = new HashMap<String, String>();
        // 将json字符串转换成jsonObject
        JSONObject jsonObject = JSONObject.fromObject(object);
        Iterator ite = jsonObject.keys();
        // 遍历jsonObject数据,添加到Map对象
        while (ite.hasNext())
        {
            String key = ite.next().toString();
            String value = jsonObject.get(key).toString();
            data.put(key, value);
        }

        return data;
    }



}
