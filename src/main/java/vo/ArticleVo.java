package vo;

/**
 * 网易新闻文章值对象
 * 文章带的图片url暂时不存...暂时未用到
 */

public class ArticleVo {
    //文章id
    private String docid;
    //文章标题
    private String title;
    //文章内容
    private String body;
    //文章类型？
    private String articleType;
    //文章发表时间
    private String ptime;
    //搜索关键词
    private String[] searchKw;
    private String threadVote;//喜欢这篇文章的人数
    private String ename;//文章所属网易号的id
    private String tname;//网易号的名称

}

class searchKw {
    private String weight;//权重
    private String keyword;//关键词名
    private String tag_source; //不知道干啥的

}
