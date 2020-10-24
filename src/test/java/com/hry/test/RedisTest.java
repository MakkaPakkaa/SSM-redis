package com.hry.test;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hry.pojo.Wares;
import com.hry.service.RedisService;
import com.hry.service.WaresService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-*.xml")
public class RedisTest {
	@Autowired
	private RedisService rs;

	@Autowired
	private WaresService ws;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void test() {
		boolean flag = rs.set("name", "小明", 30);
		System.out.println(flag);
	}

	@Test
	public void get() {
		String obj = rs.get("name").toString();
		System.out.println(obj);
	}

	@Test
	public void setList() {
		int pageNum = 3;
		int pageSize = 5;
		List<Wares> list = ws.getListInfoPage(null, null, pageNum, pageSize).getList();
		boolean flag = rs.set("wares_" + pageNum + "_" + pageSize, list, 60 * 10);
		System.out.println("flag = " + flag);
	}

	@Test
	public void setList1() throws Exception {
		int pageNum = 3;
		int pageSize = 5;
		String key = "goods_" + pageNum + "_" + pageSize;
		List<Wares> list = null;
		if (rs.hasKey(key)) {
			// 获取json串
			String jsonStr = rs.get(key).toString();
			// 转换成List
//			list = objectMapper.readValue(jsonStr,
//					objectMapper.getTypeFactory().constructParametricType(List.class, Wares.class));
			list = objectMapper.readValue(jsonStr,
					objectMapper.getTypeFactory().constructParametrizedType(List.class, Wares.class, HashMap.class));
			System.out.println(list);
			System.out.println("从redis中获取");
		} else {
			// 从数据库中读取
			list = ws.getListInfoPage(null, null, pageNum, pageSize).getList();
			// 把list集合转换成json格式的字符串
			String jsonStr = objectMapper.writeValueAsString(list);
			rs.set(key, jsonStr, 60 * 10);
			System.out.println("从数据库中获取");
		}
		for (Wares info : list) {
			System.out.println(info);
		}
	}

}
