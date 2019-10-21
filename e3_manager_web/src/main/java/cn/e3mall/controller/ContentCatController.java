package cn.e3mall.controller;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.pojo.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 内容分类管理Controller
 */

@Controller
public class ContentCatController {


    @Autowired
    private ContentCategoryService contentCategoryService;


    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(
            @RequestParam(name = "id",defaultValue = "0")Long parentId){
        List<EasyUITreeNode> list = contentCategoryService.getContentCatList(parentId);
        return list;
    }
    /**
     * 添加分类节点
     */
    @RequestMapping(value = "/content/category/create",method = RequestMethod.POST)
    @ResponseBody
    public E3Result creatContentCCategory(Long parentId, String name){
        //调用服务添加节点
        E3Result result = contentCategoryService.addContentCategory(parentId, name);
        return result;
    }
    /**
     * 重命名节点
     */
    @RequestMapping("/content/category/update")
    @ResponseBody
    public E3Result renameContentCategory(long id, String name){
        E3Result result = contentCategoryService.renameContentCategory(id,name);
        return result;
    }


    /**
     * 删除节点
     */
    @RequestMapping("/content/category/delete/")
    @ResponseBody
    public E3Result deleteContentCategory(long id){
        E3Result result = contentCategoryService.deleteContentCategory(id);
        return result;
    }


}
