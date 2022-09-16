package com.systex.practice;

import com.systex.practice.controller.dto.request.FindStockRequest;
import com.systex.practice.controller.dto.response.Result;
import com.systex.practice.controller.exception.StockWebHttpException;
import com.systex.practice.service.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PracticeApplicationTests {

	@Autowired
	private StockService stockService;

	@Test
	void contextLoads() throws StockWebHttpException {
		FindStockRequest findStockRequest = new FindStockRequest();
		findStockRequest.setBranchNo("F62Z");
		findStockRequest.setCustSeq("01");
		findStockRequest.setStock("1905");
		List<Result> resultList = this.stockService.getStockDetail(findStockRequest);
		System.out.println(resultList);
	}

}
