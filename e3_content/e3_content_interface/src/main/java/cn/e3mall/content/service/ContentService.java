package cn.e3mall.content.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.pojo.utils.E3Result;
import cn.e3mall.pojo.TbContent;

import java.util.List;

public interface ContentService {

    E3Result addContent(TbContent content);
    EasyUIDataGridResult getItemList(Long categoryId, Integer page, Integer rows);
    public E3Result deleteBatchContent(String[] ids);
    List<TbContent> getContentListByCid(Long cid);
}
