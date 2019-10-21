package cn.e3mall.search.Message;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.search.Mapper.ItemMapper;
import cn.e3mall.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.awt.font.TextMeasurer;

/**
 * 监听商品添加消息，接收消息后，将对应的商品信息同步到索引库
 */
public class ItemUpdateMessageListener implements MessageListener {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrServer solrServer;

    @Autowired
    private SearchItemService searchItemService;

    @Override
    public void onMessage(Message message) {
        try {
            //从消息中取商品id
            TextMessage textMessage = (TextMessage) message;
            Long itemId = Long.valueOf(textMessage.getText());
            searchItemService.syncIndex(itemId);
            //等待事务提交
            Thread.sleep(1000);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
