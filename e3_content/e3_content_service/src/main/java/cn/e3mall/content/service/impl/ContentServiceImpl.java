package cn.e3mall.content.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.pojo.utils.E3Result;
import cn.e3mall.common.pojo.utils.JsonUtils;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 内容管理Service
 */
@Service
public class ContentServiceImpl implements ContentService{

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${CONTENT_LIST}")
    private String CONTENT_LIST;
    /**
     * 新增商品列表
     * @param content
     * @return
     */
    @Override
    public E3Result addContent(TbContent content) {
        //将内容数据插入到内容表
        content.setCreated(new Date());
        content.setUpdated(new Date());
        //插入到数据库
        contentMapper.insert(content);
        //添加操作之后进行缓存同步
        jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
        return E3Result.ok();
    }
    /**
     * 编辑商品列表
     */
    @Override
    public EasyUIDataGridResult getItemList(Long categoryId, Integer page, Integer rows) {
        //设置分页信息
        PageHelper.startPage(page,rows);
        //创建查询条件
        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andCategoryIdEqualTo(categoryId);
        //执行查询
        List<TbContent> contents = contentMapper.selectByExample(example);
        //取分页信息
        PageInfo<TbContent> pageInfo = new PageInfo(contents);
        //创建返回结果对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(contents);
        //修改操作之后进行缓存同步
        jedisClient.hdel(CONTENT_LIST, categoryId.toString());
        return result;
    }
    /**
     * 删除商品列表
     */
    @Override
    public E3Result deleteBatchContent(String[] ids) {
        //遍历集合
        for (String id  :ids) {
            //执行操作数据库
            contentMapper.deleteByPrimaryKey(Long.valueOf(id));
        }
        //更新缓存
        return E3Result.ok();
    }


    /**
     * 根据内容分类id查询内容列表
     */
    @Override
    public List<TbContent> getContentListByCid(Long cid) {
        //查询缓存
        try{//如果缓存中有直接相应结果
            String json = jedisClient.hget(CONTENT_LIST, cid + "");
            if (!StringUtils.isEmpty(json)){
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //如果没有查询数据库
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andCategoryIdEqualTo(cid);
        //执行查询
        List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
        try{//把结果添加到缓存
            jedisClient.hset(CONTENT_LIST,cid +"", JsonUtils.objectToJson(list));
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
