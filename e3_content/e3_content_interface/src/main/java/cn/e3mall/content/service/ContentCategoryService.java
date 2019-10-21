package cn.e3mall.content.service;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.pojo.utils.E3Result;

import java.util.List;

public interface ContentCategoryService {

    List<EasyUITreeNode> getContentCatList(Long ParentId);
    E3Result addContentCategory(Long parentId,String name);
    E3Result deleteContentCategory(long id);
    E3Result renameContentCategory(long parentId,String name);

}
