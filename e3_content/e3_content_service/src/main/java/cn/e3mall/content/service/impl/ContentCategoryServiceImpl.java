package cn.e3mall.content.service.impl;


import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.pojo.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 内容管理Service
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    /**
     * 展示查询结果
     * @param parentId
     * @return
     */
    @Override
    public List<EasyUITreeNode> getContentCatList(Long parentId) {
        //根据parentid查询子节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> catList = contentCategoryMapper.selectByExample(example);
        //转换成EasyUITreeNode的列表
        List<EasyUITreeNode> nodeList = new ArrayList<>();
        for (TbContentCategory tbContentCategory:catList) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent()?"closed":"open");
            //添加到列表
            nodeList.add(node);
        }
        return nodeList;
    }

    /**
     * 增加分类节点
     * @param parentId
     * @param name
     * @return
     */
    @Override
    public E3Result addContentCategory(Long parentId, String name) {
        //创建一个tb_content_category表对应的pojo对象
        TbContentCategory contentCategory = new TbContentCategory();
        //设置pojo属性
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        //1（正常），2（删除）
        contentCategory.setStatus(1);
        //默认排序是1
        contentCategory.setSortOrder(1);
        //新添加的节点一定是叶子结点
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        //插入到数据库
        contentCategoryMapper.insert(contentCategory);
        //判断父节点的ispaarent属性。如果不是true，改为true
        //根据ParentId查询父节点
        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parent.getIsParent()){
            parent.setIsParent(true);
            //更新到数据库中
            contentCategoryMapper.updateByPrimaryKey(parent);
        }
        //返回结果，返回E3result，包含pojo
        return E3Result.ok(contentCategory);
    }

    /**
     * 重命名分类节点
     */
    @Override
    public E3Result renameContentCategory(long parentId,String name) {
        //通过id查找
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
        //设置新输入的名称
        contentCategory.setName(name);
        //更新数据库名称
        contentCategoryMapper.updateByPrimaryKey(contentCategory);
        return E3Result.ok();
    }

    /**
     * 删除分类节点
     */
    @Override
    public E3Result deleteContentCategory(long id) {
        //判断是否为父节点
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        Long parentId = contentCategory.getParentId();
        //不是父节点不允许删除
        if (contentCategory.getIsParent()){
            return E3Result.build(1, "删除失败");
        }else {
            //是父节点允许删除
            contentCategoryMapper.deleteByPrimaryKey(id);
            //修改父节点的isParent值
            TbContentCategoryExample example = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(parentId);
            List<TbContentCategory> child = contentCategoryMapper.selectByExample(example);
            if(child.size()==0){
                //判断父节点的isParent是否为true，不是则改为true
                TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
                if (parent.getIsParent()){
                    parent.setIsParent(false);
                }
                contentCategoryMapper.updateByPrimaryKey(parent);
            }
        }
        return E3Result.ok(contentCategory);
    }





}
