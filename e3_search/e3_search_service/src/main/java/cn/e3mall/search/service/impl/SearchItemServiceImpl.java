package cn.e3mall.search.service.impl;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.common.pojo.utils.E3Result;
import cn.e3mall.search.Mapper.ItemMapper;
import cn.e3mall.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 索引库维护Service
 */

@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public E3Result importAllItem() {
        try {
            //查询商品列表
            List<SearchItem> itemList = itemMapper.getItemList();
            //遍历商品列表
            for (SearchItem searchItem : itemList) {
                //创建文档对象
                SolrInputDocument document = new SolrInputDocument();
                //向文档对象中添加域
                document.addField("id", searchItem.getId());
                document.addField("item_title", searchItem.getTitle());
                document.addField("item_sell_point", searchItem.getSell_point());
                document.addField("item_price", searchItem.getPrice());
                document.addField("item_image", searchItem.getImage());
                document.addField("item_category_name", searchItem.getCategory_name());
                //把文档对象写入索引库
                solrServer.add(document);
            }
            //提交
            solrServer.commit();
            //返回导入成功
            return E3Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return E3Result.build(500, "数据导入时发生异常");
        }
    }

    @Override
    public void syncIndex(Long itemId) {

        //根据所给id查询结果
        SearchItem targetItem = itemMapper.getItemById(itemId);
//        System.out.println(targetItem);

        //不为空则说明状态正常
        if (targetItem != null) {
            try {
                //进行同步操作
                SolrInputDocument document = new SolrInputDocument();
                //向文档对象中添加域
                document.addField("id", targetItem.getId());
                document.addField("item_title", targetItem.getTitle());
                document.addField("item_sell_point", targetItem.getSell_point());
                document.addField("item_price", targetItem.getPrice());
                document.addField("item_image", targetItem.getImage());
                document.addField("item_category_name", targetItem.getCategory_name());
                //把文档对象写入索引库
                solrServer.add(document);
                //提交
                solrServer.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                //不正常状态则为下架，删除
                solrServer.deleteById(String.valueOf(itemId));
                //提交
                solrServer.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
