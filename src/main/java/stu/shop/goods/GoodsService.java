package stu.shop.goods;

import java.util.List;
import java.util.Map;

public interface GoodsService {
	
	List<Map<String, Object>> newGoodsList(Map<String, Object> map) throws Exception; // 카테고리 상품 리스트
	
	List<Map<String, Object>> bestGoodsList(Map<String, Object> map) throws Exception; // 베스트 상품 리스트

}
