package util;

import java.util.BitSet;

/**
 * 最最原始的布隆过滤器类
 */
public class SimpleBloomFilter
{
    // 设置布隆过滤器的大小
    private static final int DEFAULT_SIZE = 2 << 24;
    // 产生随机数的种子，可产生6个不同的随机数产生器。。。而且最好取素数
    private static final int[] seeds = new int[]{7, 11, 13, 31, 37, 61};
    // Java中的按位存储的思想，其算法的具体实现（布隆过滤器）
    private BitSet bits = new BitSet(DEFAULT_SIZE);
    // 根据随机数的种子，创建6个哈希函数
    private SimpleHash[] func = new SimpleHash[seeds.length];

    // 设置布隆过滤器所对应k（6）个哈希函数
    public SimpleBloomFilter()
    {
        for (int i = 0; i < seeds.length; i++)
        {
            func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
        }
    }

    public static void main(String[] args)
    {
        SimpleBloomFilter filter = new SimpleBloomFilter();
        filter.add("123");
        System.out.println(filter.contains("123"));
        System.out.println(filter.contains("124"));
        filter.add("124");
        System.out.println(filter.contains("124"));
    }


    /**
     * 添加
     * @param str 值
     */
    public void add(String str)
    {
        // 集齐6个hash值，准备（召唤神龙）添加
        for (SimpleHash f:func)
        {
            bits.set(f.hash(str));
        }
    }


    /**
     * 判断是否存在
     * @param str
     * @return
     */
    public boolean contains(String str)
    {
        // 根据此URL得到在布隆过滤器中的对应位，并判断其标志位（6个不同的哈希函数产生6种不同的映射）
        for (SimpleHash f : func)
        {
            //当存在六位不都为0时，返回false
            if (!bits.get(f.hash(str)))
            {
                return false;
            }
        }
        return true;
    }


    /**
     * 哈希类
     */
    public static class SimpleHash
    {
        private int cap;
        private int seed;

        // 默认构造器，哈希表长默认为DEFAULT_SIZE大小，此哈希函数的种子为seed
        public SimpleHash(int cap, int seed)
        {
            this.cap = cap;
            this.seed = seed;
        }

        public int hash(String value)
        {
            int result = 0;
            int len = value.length();

            for (int i = 0; i < len; i++)
            {
                // 散列函数...重点，将此URL（使用到了集合中的每一个元素）散列到一个值
                result = seed * result + value.charAt(i);
            }

            // 产生单个信息指纹。。。不能直接返回result，可能会越界
            return (cap - 1) & result;
        }
    }
}
