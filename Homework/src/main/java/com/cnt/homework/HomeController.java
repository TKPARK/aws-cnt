package com.cnt.homework;

import java.util.ArrayList;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cnt.model.Coin;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	public int T; // 지폐의 금액
	public int K; // 동전의 가지수
	public int [] Pi; // 동전금액
	public int [] Ni; // 동전개수
	public int [] Ci; // 사용개수
	
	public int selcount = 0;
	public int [] sel = new int[10000];
	public ArrayList<String> resultList;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("in home...");
		
		return "home";
	}
	

	@RequestMapping(value = "/homework2", method = RequestMethod.GET)
	public String homework2(Locale locale, Model model) {
		logger.info("in homework2...");
		
		model.addAttribute("amt", "");
		model.addAttribute("price", null);
		model.addAttribute("priceCnt", null);
		model.addAttribute("coinRow", -1 );
		model.addAttribute("resultTxt", "" );
		
		return "homework2";
	}
	
	
	@RequestMapping(value="/coinExchange.do", method=RequestMethod.POST)
	public String coinExchange(Coin coin, Model model) throws Exception {
		logger.info("in coinExchange...");
		
		// 화면에서 넘겨받은 데이터 변수에 담기
		String amt = coin.getAmt(); // 지폐의 금액
		String[] price = coin.getPrice(); // 동전금액
		String[] priceCnt = coin.getPriceCnt(); // 동전개수
		
		resultList = new ArrayList<String>();
		
		T = Integer.parseInt(amt == "" ? "0" : amt.replaceAll(",", ""));
		K = price.length;
		
		// 동전의 가지수 만큼 배열 초기화
		Pi = new int[K]; // 동전금액
		Ni = new int[K]; // 동전개수
		Ci = new int[K]; // 사용개수
		
		// 변수에 동전금액, 개수 담기
		for(int i=0; i<K; i++) {
			Pi[i] = Integer.parseInt(price[i] == "" ? "0" : price[i].replaceAll(",", ""));
			Ni[i] = Integer.parseInt(priceCnt[i] == "" ? "0" : priceCnt[i].replaceAll(",", ""));
		}
		
		// log
		logger.info("지폐의 금액 T="+T);
		logger.info("동전의 가지수 K="+K);
		for(int i=0; i<K; i++) {
			logger.info("동전 Pi="+Pi[i]+", Ni="+Ni[i]);
		}
		
		// 계산
		calcCoin(0,0,0);
		
		// 출력형식 데이터 만들기
		String resultTxt = "";
		resultTxt = "총 " + resultList.size() + "가지" + "\n";
		for(String result : resultList) {
			resultTxt += T + " = " + result + "\n";
		}
		
		// 화면으로 보낼 데이터 세팅
		model.addAttribute("amt", amt);
		model.addAttribute("price", Pi);
		model.addAttribute("priceCnt", Ni);
		model.addAttribute("coinRow", K);
		model.addAttribute("resultTxt", resultTxt );
		
		return "homework2";
	}
	
	
	private int calcCoin(int depth,int starti, int sum) {
		int i;
		
	    if( sum > T )
	    	return 0;
	    
	    if( sum == T ){
    		for( i=0; i<selcount; i++ ) {
    			for( int j=0; j<K; j++ ) {
    				if(sel[i] == Pi[j]) {
        				Ci[j] += 1;
        			}
    	    	}
	        }
    		String txt = "";
    		boolean isSkip = false;
    		for( int j=0; j<K; j++ ) {
    			if(Ci[j] == 0) continue;
    			if(Ci[j] > Ni[j]) isSkip = true;
    			
    			if(txt == "")
    				txt += Pi[j] + " x " + Ci[j];
    			else
    				txt += " + " + Pi[j] + " x " + Ci[j];
    		}
    		
    		if(!isSkip) {
    			System.out.println(txt);
    			resultList.add(txt);
    		}
    		
    		// init
	        Ci = new int[K];
	        isSkip = false;
	        return 0;
	    }

	    for( i=starti; i<K; i++ ) {
	        sel[selcount] = Pi[i];
	        sum = sum + Pi[i];
	        selcount++;
	        calcCoin(depth+1, i, sum);
	        selcount--;
	        sum = sum - Pi[i];
	    }

	    return 0;
	}
}
