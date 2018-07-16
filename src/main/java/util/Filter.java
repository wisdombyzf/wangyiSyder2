package util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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


}
