package cn.e3mall.controller;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.pojo.utils.E3Result;
import cn.e3mall.pojo.TbItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;

/**
 * 商品管理Controller
 */
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem tbitem = itemService.getItemById(itemId);
		return tbitem;
	}

	@RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page,Integer rows){
	    //调用服务，查询商品列表
        EasyUIDataGridResult result = itemService.getItemList(page,rows);
        return result;

    }

    /**
     * 商品添加功能
     */
    @RequestMapping(value = "/item/save",method = RequestMethod.POST)
    @ResponseBody
    public E3Result addItem(TbItem item, String desc){
        E3Result result = itemService.addItem(item,desc);
        return result;
    }

    /**
     * 根据id获取商品描述
     */
    @RequestMapping("/item/desc/{id}")
    @ResponseBody
    public TbItemDesc getItemDesc(@PathVariable("id") long id){
        TbItemDesc result = itemService.getItemDesc(id);
        return result;
    }

    /**
     *编辑商品
     */
    @RequestMapping("/item/update")
    @ResponseBody
    public E3Result updateItem(TbItem item,String desc){
        E3Result result = itemService.updateItem(item,desc);
        return result;
    }
    /**
     * 删除商品
     */
    @RequestMapping("/item/delete")
    @ResponseBody
    public E3Result delete(String ids){
//        System.out.println(ids);
//        System.out.println(StringUtils.isEmpty(ids));
        E3Result result = itemService.delete(ids);
        return result;
    }
    /**
     * 下架商品
     */
    @RequestMapping("/item/instock")
    @ResponseBody
    public E3Result instock(String ids){
        E3Result result = itemService.instock(ids);
        return result;
    }


    /**
     * 上架商品
     */
    @RequestMapping("/item/reshelf")
    @ResponseBody
    public E3Result reshelf(String ids){
        E3Result result = itemService.reshelf(ids);
        return result;
    }
}

