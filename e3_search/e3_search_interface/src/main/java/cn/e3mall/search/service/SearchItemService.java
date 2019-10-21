package cn.e3mall.search.service;

import cn.e3mall.common.pojo.utils.E3Result;

public interface SearchItemService {

    E3Result importAllItem();

    void syncIndex(Long itemId);
}
