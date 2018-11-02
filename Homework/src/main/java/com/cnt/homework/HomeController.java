package com.cnt.homework;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	

	@RequestMapping(value = "/homework2", method = RequestMethod.GET)
	public String homework2(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
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
		
		int T; // 지폐의 금액
		int K; // 동전의 가지수
		int [] Pi; // 동전금액
		int [] Ni; // 동전개수
		int [] D; // 각 동전 사용개수
		ArrayList<String> resultList = new ArrayList<String>();
		
		String amt = coin.getAmt(); // 지폐의 금액
		String[] price = coin.getPrice(); // 동전금액
		String[] priceCnt = coin.getPriceCnt(); // 동전개수
		
		T = Integer.parseInt(amt);
		K = price.length;
		
		Pi = new int[K]; // 동전금액
		Ni = new int[K]; // 동전개수
		D = new int[10001];
		for(int i=0; i<K; i++) {
			Pi[i] = Integer.parseInt(price[i]);
			Ni[i] = Integer.parseInt(priceCnt[i]);
		}
		logger.info("지폐의 금액 T="+T);
		logger.info("동전의 가지수 K="+K);
		for(int i=0; i<K; i++) {
			logger.info("동전 Pi="+Pi[i]+", Ni="+Ni[i]);
		}
		
//		int l1, l2, l3, last;
//		last = 0;
//		D[0] = 1;
//		for(l1=0;l1<K;l1++)
//		{
//			for(l2=last;l2>=0;l2--)
//			{
//				for(l3=1;l3<=Ni[l1];l3++)	
//				{
//					if(l2 + l3 * Pi[l1] > T) break;
//					D[l2 + l3*Pi[l1]] += D[l2];
//					int tt = l2 + (l3*Pi[l1]);
//					logger.info("D["+tt+"] ="+D[l2 + l3*Pi[l1]]);
//				}
//			}
//			last += Pi[l1]*Ni[l1];
//			if(last > T) last = T;
//		}
//		logger.info("동전 D[T]="+D[T]);
		
		
//		List<Integer> list = new ArrayList<>();
//		
//		int temp = T;
//		String txt = " " + T + " = ";
//		for(int i=0; i<K; i++) {
//			int pi = Pi[i]; // 동전금액
//			int ni = Ni[i]; // 동전개수
//			System.out.println("pi = " + pi+ ", Ni = " + ni);
//			
//			for(int j=1; j<=ni; j++) {
//				//System.out.println("temp = " + temp+ ", pi * j = " + pi * j);
//				list.add(pi * j);
//				
//				if(temp < (pi * j)) {
//					
//				} else if(temp == (pi * j)) {
//					System.out.println(txt + pi + " x " + j);
//				} else if(temp > (pi * j)) {
//					txt += pi + " x " + j + " + ";
//					temp = temp - (pi * j);
//					Ci[i-1] = temp;
//					break;
//				}
//			}
//		}
//		System.out.println("list.size() = " + list.size());
//		for(int i=0; i<list.size(); i++) {
//			System.out.println("list("+i+") = " + list.get(i));
//		}
		
		
		//
		int cnt = 1;
		int temp = T;
		String txt = "";
		String txt2 = "";
		for(int i=1; i<=K; i++) {
            int pi = Pi[i-1]; // 동전금액
            int ni = Ni[i-1]; // 동전개수
            
            for(int j=ni; j>0; j--) {
                if(temp == (pi * j)) {
                	txt = txt2 + pi +" x "+j;
                    System.out.println(txt);
                    resultList.add(txt);
                }
                else {
                    if(temp > pi * j) {
                        txt2 = txt2 + pi +" x "+j+" + ";
                        temp = temp - (pi * j);
                        break;
                    }
                }
            }
            if(i == K) {
                //System.out.println("i="+i+", cnt="+cnt);
                cnt = cnt + 1;
                if(cnt == K+1)
                    break;
                else {
                    i = cnt-1;
                    txt2 = "";
                    temp = T;
                }
            }
        }
		//https://www.digitalculture.or.kr/koi/showOlymPiadDissentDetail.do
		String resultTxt = "";
		resultTxt = "총 " + resultList.size() + "가지" + "\n";
		for(String result : resultList) {
			resultTxt += T + " = " + result + "\n";
		}
		
		model.addAttribute("amt", amt);
		model.addAttribute("price", Pi);
		model.addAttribute("priceCnt", Ni);
		model.addAttribute("coinRow", K);
		model.addAttribute("resultTxt", resultTxt );
		
		return "homework2";
	}
	
}
