package vo;

/**
 * 网易新闻自媒体值对象
 */
public class PageVo
{
    //自媒体id
    private String id;
    //名称
    private String name;
    //简介or描述
    private String description;
    //被关注数,粉丝数
    private String fanNum;

    //头像url
    private String avatarsUrl;

    //该页面url
    private String url;
    //所属频道或类别（例如娱乐、财经、美食）string
    private String channel;
    //自媒体关注其他账号的数量...网易新闻无
    private String follow;
    //点赞数...网易新闻无
    private String likeCount;
    //认证信息 string(例如优质视频原创作者) ...网易新闻无
    private String authInfo;
    //自媒体账号发布的文章数或动态数 int
    private String publicationNum;

    //自媒体的别名或者说一句话简介
    private String alias;

    //标识站点，规定头条为toutiao，网易为netease，腾讯为tencent
    private String site;


    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }



    public String getAvatarsUrl()
    {
        return avatarsUrl;
    }

    public void setAvatarsUrl(String avatarsUrl)
    {
        this.avatarsUrl = avatarsUrl;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getFanNum()
    {
        return fanNum;
    }

    public void setFanNum(String fanNum)
    {
        this.fanNum = fanNum;
    }

    public String getSite()
    {
        return site;
    }

    public void setSite(String site)
    {
        this.site = site;
    }

    public String getAlias()
    {
        return alias;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    public String getPublicationNum()
    {
        return publicationNum;
    }

    public void setPublicationNum(String publicationNum)
    {
        this.publicationNum = publicationNum;
    }

    public String getAuthInfo()
    {
        return authInfo;
    }

    public void setAuthInfo(String authInfo)
    {
        this.authInfo = authInfo;
    }

    public String getLikeCount()
    {
        return likeCount;
    }

    public void setLikeCount(String likeCount)
    {
        this.likeCount = likeCount;
    }

    public String getFollow()
    {
        return follow;
    }

    public void setFollow(String follow)
    {
        this.follow = follow;
    }

    public String getChannel()
    {
        return channel;
    }

    public void setChannel(String channel)
    {
        this.channel = channel;
    }
}
