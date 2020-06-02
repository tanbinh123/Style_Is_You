package stu.shop.goods;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import stu.common.common.CommandMap;

@Controller
public class GoodsController {
	
	Logger log = Logger.getLogger(this.getClass()); //로그
	
	   public static final int pagingSet = 5;
	   private int currentPage = 1;
	   private int totalCount;
	   private int blockCount = 16;
	   private int blockPage = 10;
	   private String pagingHtml;

	   //페이징 숫자
	
	@Resource(name="goodsService")
	private GoodsService goodsService;
	
	@RequestMapping(value="/shop/newGoodsList.do") // url 
	public ModelAndView goodsCateList(CommandMap commandMap) throws Exception { // 최신상품 리스트 출력
		
		ModelAndView mv = new ModelAndView("shop/goodsList"); // 보낼 url

		List<Map<String,Object>> list = goodsService.newGoodsList(commandMap.getMap());
		
		mv.addObject("list", list);
		mv.addObject("titleMain", "새상품");
		
		return mv;
		
	}
	
	@RequestMapping(value="/shop/bestGoodsList.do") // url 
	public ModelAndView bestGoodsList(CommandMap commandMap) throws Exception { // 베스트 상품 리스트 출력
		
		ModelAndView mv = new ModelAndView("shop/goodsList"); // 보낼 url
		
		List<Map<String,Object>> list = goodsService.bestGoodsList(commandMap.getMap());
		list.get(0);
		System.out.println("index"+ list.get(0));
		System.out.println("get!!!!!!!!!!!!!"+commandMap.get("GOODS_NAME"));
		mv.addObject("list", list);
		mv.addObject("titleMain", "베스트");
		
		return mv;
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/shop/cateGoodsList/{cate}/{orderBy}.do", method=RequestMethod.GET)
	public ModelAndView cateList(@PathVariable String cate, @PathVariable String orderBy, CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<Map<String,Object>> list = null;

		mv.setViewName("shop/cateGoodsList");

		System.out.println("카테고리 = " + cate);
		System.out.println("카테고리 순서 =" + orderBy);
		
		// category
		commandMap.put("cate", cate);
		
		// order by
		if( "NewItem".equals(orderBy) ) { // 신상품순
			commandMap.put("orderBy", "GOODS_DATE");
			commandMap.put("orderSort", "DESC");
		}else if( "favorite".equals(orderBy) ) { //인기상품
			commandMap.put("orderBy", "GOODS_READCNT");
			commandMap.put("orderSort", "DESC");
		}else if( "low".equals(orderBy) ) { // 낮은가격순
			commandMap.put("orderBy", "GOODS_SELL_PRICE");
			commandMap.put("orderSort", "ASC");
		}else if( "high".equals(orderBy) ) { // 높은가격순
			commandMap.put("orderBy", "GOODS_SELL_PRICE");
			commandMap.put("orderSort", "DESC");
		}
			
		list = goodsService.cateGoodsList(commandMap.getMap());
		
		System.out.println("dd"+list.get(0).get("TOTAL_COUNT"));
		
		list.get(0).get("TOTAL_COUNT"); 
		
		mv.addObject("total", list.get(0).get("TOTAL_COUNT"));
		mv.addObject("list", list);
		mv.addObject("category", cate);

		return mv;
	}
	
	@RequestMapping(value="/shop/goodsDetail.do") // url 
	public ModelAndView goodsDetail(CommandMap commandMap) throws Exception { // 베스트 상품 리스트 출력
		
		ModelAndView mv = new ModelAndView("shop/goodsDetail"); // 보낼 url
		Map<String,Object> map = goodsService.goodsDetail(commandMap.getMap());
		System.out.println("IDX = "+commandMap.getMap());
		System.out.println("map = " + map);
		
		map.get("GOODS_ATT_COLOR");
		String ColorSize = map.get("GOODS_ATT_COLOR").toString();
		System.out.println("ColorSize"+ ColorSize); // 색상 스트링
		
		mv.addObject("map", map.get("map"));  // 상품의 PK값
		mv.addObject("list", map); // 상품 상세 정보
		
		return mv;
		
	}
	
	
	@RequestMapping(value="/shop/openGoodsWrite.do") // url 
	public ModelAndView goodsWriteForm(CommandMap commandMap) throws Exception { // 글쓰기 폼
		
		ModelAndView mv = new ModelAndView("shop/goodsWrite"); // 보낼 url
		
		return mv;
		
	}
	
	
	@RequestMapping(value="/shop/goodsWrite.do") // url 
	public ModelAndView goodsWrite(CommandMap commandMap, HttpServletRequest request) throws Exception { // 글쓰기 작성완료
		
		ModelAndView mv = new ModelAndView("redirect:http://localhost:8080/stu/main.do"); // 보낼 url
		goodsService.goodsInsert(commandMap.getMap(), request);
		
		return mv;
		
	}
	
}