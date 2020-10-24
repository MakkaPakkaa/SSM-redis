package com.hry.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.hry.pojo.Wares;
import com.hry.service.WaresService;
import com.mysql.cj.util.StringUtils;


@Controller
public class WaresController {
	
	@RequestMapping("{path}")
	public String path(@PathVariable String path){
		return path ; 
	}
	
	@Autowired
	private WaresService ws;
	
	//查询所有
	@RequestMapping("wares")
	public String getwares(Model model) {
		List<Wares> waresList = ws.queryWares();
		model.addAttribute("waresList", waresList);
		return "list";
	}
	/**
	 * 根据Id查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="waresId/{id}")
	public Map<String, Object> queryWaresById(@PathVariable Integer id) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Wares wares = ws.queryWaresById(id);
		System.out.println(wares);
		map.put("wares", wares);
		return map ; 
	}
	
	//分页查询
	@RequestMapping("list")
	public String getWaresList(String name , String outline , 
			@RequestParam(defaultValue="1") Integer pageNum ,
			@RequestParam(defaultValue="5") Integer pageSize,
			Model model) throws Exception{		
		PageInfo<Wares> pageInfo = ws.querywaresByPage(name, outline, pageNum, pageSize);
		model.addAttribute("waresList", pageInfo.getList());
		model.addAttribute("totalPage", pageInfo.getNavigatepageNums());
		model.addAttribute("pageNum", pageNum);
		//存放到作用域中
		model.addAttribute("name", name);
		model.addAttribute("outline", outline);
		model.addAttribute("lastPage", pageInfo.getLastPage());
		return "list";
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="del/{id}")
	public Map<String, Object> delGoodsById(@PathVariable Integer id) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Wares wares = ws.queryWaresById(id);
		if(wares == null){
			map.put("msg", id + "商品编号不存在");
			return map ; 
		}
		ws.delWares(id);
		map.put("msg", "ok");
		return map ; 
	}
	
	@ResponseBody
	@RequestMapping("addwares")
	public Map<String, Object> addInfo(Wares wares){
		Map<String, Object> map = new HashMap<String, Object>();
		//校验
		if(wares == null) {
			map.put("msg","参数异常");
			return map;
		}
		if(StringUtils.isNullOrEmpty(wares.getName())||
		   StringUtils.isNullOrEmpty(wares.getPrice()) ||
		   StringUtils.isNullOrEmpty(wares.getDate()) ||
		   StringUtils.isNullOrEmpty(wares.getExpire_date()) ||
		   StringUtils.isNullOrEmpty(wares.getStock())) {
			
			map.put("msg","参数信息不完整");
			return map;
		}
			
		ws.addWares(wares);
		map.put("msg","ok");
		return map;

		
	}
	
	/**
	 * 修改信息
	 * @param wares
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "upd")
	public Map<String, Object> updateShopInfo(Wares wares){
		Map<String,Object> map = new HashMap<String, Object>();
		Wares id = ws.queryWaresById(wares.getId());
		if(id == null) {
			map.put("msg",wares.getId() + "商品编号不存在");
			return map;
		}
		
		ws.updWares(wares);
		return map;
		
		
	}
	
	//更新
	/*
	 * @RequestMapping(value = "comm" , method = RequestMethod.POST) public String
	 * comm(Wares wares) throws Exception{ //判断id是否存在 Wares queryWares =
	 * ws.queryWaresById(wares.getId()); if (queryWares == null) { throw new
	 * Exception("此用户不存在"); } ws.updWares(wares); return "redirect:wares"; }
	 */
	
	
	//分页查询
		/*
		 * @RequestMapping("warespage") public String
		 * getWaresByPages(@RequestParam(defaultValue = "1")Integer pageNum,
		 * 
		 * @RequestParam(defaultValue = "3") Integer pageSize, Model model) {
		 * List<Wares> waresList = ws.queryWaresByPage( pageNum, pageSize);
		 * model.addAttribute("waresList", waresList); return "list"; }
		 */
	
	
	//删除
	/*
	 * @RequestMapping("del/{id}") public String delWares(@PathVariable Integer id)
	 * throws Exception { System.out.println(id);
	 * 
	 * if (id < 0 || id ==null) { throw new Exception("id参数有误,请检查"); }
	 * 
	 * //判断id是否存在 Wares wares = ws.queryWaresById(id); if (wares == null) { throw
	 * new Exception("此用户不存在"); } ws.delWares(id); return "redirect:../wares"; }
	 */
	
	//修改按钮
		/*
		 * @RequestMapping("edit/{id}") public String editWares(@PathVariable Integer
		 * id,Model model) throws Exception { if (id < 0 || id == null) { throw new
		 * Exception("id参数有误，请检查"); } //判断id是否存在 Wares wares = ws.queryWaresById(id); if
		 * (wares == null) { throw new Exception("此用户不存在"); }
		 * model.addAttribute("wares", wares); return "edit"; }
		 */
	
	/*显示
	 * @RequestMapping("showpage") public String page(
	 * 
	 * @RequestParam(defaultValue="1") Integer pageNum,
	 * 
	 * @RequestParam(defaultValue="3") Integer pageSize, String name , Model model){
	 * PageInfo<Wares> pageInfo = ws.getListInfoPage(name ,pageNum, pageSize);
	 * model.addAttribute("waresList", pageInfo.getList()); //当前页的数据
	 * model.addAttribute("totalPage", pageInfo.getNavigatepageNums());//总页数
	 * model.addAttribute("pageNum", pageNum);//当前页 model.addAttribute("lastPage",
	 * pageInfo.getLastPage()); model.addAttribute("name", name);
	 * System.out.println("name = " + name); return "list"; }
	 */
	
	
}
