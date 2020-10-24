package com.hry.mapper;

import java.util.List;
import java.util.Map;

import com.hry.pojo.Wares;

import tk.mybatis.mapper.common.BaseMapper;

public interface WaresMapper extends BaseMapper<Wares>{
	//原始分页
	List<Wares> queryWaresByPage(Map<String, Integer> map);
	//插件分页
	List<Wares> queryPage(Map<String, Object> map);

}
