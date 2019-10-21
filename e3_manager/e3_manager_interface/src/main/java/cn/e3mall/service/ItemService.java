package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.pojo.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

public interface ItemService {



    EasyUIDataGridResult getItemList(int page, int rows);
    E3Result addItem(TbItem item,String desc);
    TbItem getItemById(long itemId);
    TbItemDesc getItemDesc(long itemId);
    E3Result updateItem(TbItem item,String desc);
    E3Result delete(String ids);
    E3Result instock(String ids);
    E3Result reshelf(String ids);

    TbItemDesc getItemDescById(Long itemId);
}
