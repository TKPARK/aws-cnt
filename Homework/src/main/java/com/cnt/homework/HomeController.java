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
	
	public int T; // ������ �ݾ�
	public int K; // ������ ������
	public int [] Pi; // �����ݾ�
	public int [] Ni; // ��������
	public int [] Ci; // ��밳��
	
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
		
		// ȭ�鿡�� �Ѱܹ��� ������ ������ ���
		String amt = coin.getAmt(); // ������ �ݾ�
		String[] price = coin.getPrice(); // �����ݾ�
		String[] priceCnt = coin.getPriceCnt(); // ��������
		
		resultList = new ArrayList<String>();
		
		T = Integer.parseInt(amt == "" ? "0" : amt.replaceAll(",", ""));
		K = price.length;
		
		// ������ ������ ��ŭ �迭 �ʱ�ȭ
		Pi = new int[K]; // �����ݾ�
		Ni = new int[K]; // ��������
		Ci = new int[K]; // ��밳��
		
		// ������ �����ݾ�, ���� ���
		for(int i=0; i<K; i++) {
			Pi[i] = Integer.parseInt(price[i] == "" ? "0" : price[i].replaceAll(",", ""));
			Ni[i] = Integer.parseInt(priceCnt[i] == "" ? "0" : priceCnt[i].replaceAll(",", ""));
		}
		
		// log
		logger.info("������ �ݾ� T="+T);
		logger.info("������ ������ K="+K);
		for(int i=0; i<K; i++) {
			logger.info("���� Pi="+Pi[i]+", Ni="+Ni[i]);
		}
		
		// ���
		calcCoin(0,0,0);
		
		// ������� ������ �����
		String resultTxt = "";
		resultTxt = "�� " + resultList.size() + "����" + "\n";
		for(String result : resultList) {
			resultTxt += T + " = " + result + "\n";
		}
		
		// ȭ������ ���� ������ ����
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
