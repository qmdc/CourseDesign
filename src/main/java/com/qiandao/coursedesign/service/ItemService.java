package com.qiandao.coursedesign.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiandao.coursedesign.pojo.Item;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author konan
 * @since 2022-06-05
 */
public interface ItemService extends IService<Item> {

    List<Map<String,Object>> search(String keyword, Integer pageIndex, Integer pageSize);

}
