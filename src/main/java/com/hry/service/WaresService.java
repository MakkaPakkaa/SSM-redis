package com.hry.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hry.mapper.WaresMapper;
import com.hry.pojo.Wares;
import com.mysql.cj.util.StringUtils;

@Service
public class WaresService {
	//自动注入
	@Autowired
	private WaresMapper wm;
	
	@Autowired
	private RedisService rs ; 
	
	private ObjectMapper objectMapper = new ObjectMapper();
	/**
	 * 查询所有
	 * @return
	 */
	public List<Wares> queryWares(){
		List<Wares> list =wm.selectAll();
		return list;
		
	}
	/**
	 * 删除用户
	 * @param id
	 */
	public void delWares(Integer id) {
		wm.deleteByPrimaryKey(id);
	}
	/**
	 * 根据ID查询响应的对象
	 * @param id
	 * @return
	 */
	public Wares queryWaresById(Integer id) {
		Wares wares = wm.selectByPrimaryKey(id);
		return wares;
	}
	/**
	 * 修改商品
	 * @param wares
	 */
	public void updWares(Wares wares) {
		wm.updateByPrimaryKey(wares);
	}
	
	/**
	 * 添加商品信息
	 * @param wares
	 */
	public void addWares(Wares wares) {
		wm.insert(wares);
	}
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<Wares> queryWaresByPage(Integer pageNum, Integer pageSize){
		//定义Hashmap
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		Integer startIndex = (pageNum-1)*pageSize;
		
		map.put("startIndex", startIndex);
		map.put("pageSize", pageSize);
		
		List<Wares> list = wm.queryWaresByPage(map);
		
		return list;
	}
	/**
	 * 
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Wares> getListInfoPage(String name , String outline
			,Integer pageNum , Integer pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isNullOrEmpty(name)) {
			map.put("name", name);
		}
		if (!StringUtils.isNullOrEmpty(outline)) {
			map.put("outline", outline);
		}
		
		
		PageHelper.startPage(pageNum,pageSize);

		//获取分页数据
		List<Wares> list = wm.queryPage(map);
		//PageInfo 设置分页
		PageInfo<Wares> pageInfo = new PageInfo<Wares>(list);
		return pageInfo;
	}
	
	
	public PageInfo<Wares> querywaresByPage(String name ,String outline, Integer pageNum , Integer pageSize) throws Exception{
		String key = "wares";
		Map<String, Object> map = new HashMap<String, Object>();
		if(!StringUtils.isNullOrEmpty(name)){
			map.put("name", name);
			key += "_" + name ; 
		}
		if(!StringUtils.isNullOrEmpty(outline)){
			map.put("outline", outline);
			key += "_" + outline ; 
		}
		key += "_"+pageNum+"_" + pageSize ; //wares_1_3  第一页的三条数据，放入到缓存中
		List<Wares> list = null ; 
		//开始分页
		PageHelper.startPage(pageNum , pageSize);
		if(rs.hasKey(key)){
			String jsonStr = rs.get(key).toString();
			list = objectMapper.readValue(jsonStr,
					objectMapper.getTypeFactory().constructParametrizedType(List.class, Wares.class, HashMap.class));
			System.out.println(list);
			System.out.println("从redis中获取");

		}else{
			list = wm.queryPage(map);
			String jsonStr = objectMapper.writeValueAsString(list);
			rs.set(key, jsonStr, 60*10) ;
		}
		//PageInfo 设置分页
		PageInfo<Wares> pageInfo = new PageInfo<Wares>(list);
		return pageInfo ; 
	}
}
