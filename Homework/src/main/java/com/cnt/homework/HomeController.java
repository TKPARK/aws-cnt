package com.cnt.homework;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
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
		
		int maxNi = 0; // 가장 큰 동전개수
		int T; // 지폐의 금액
		int K; // 동전의 가지수
		int [] Pi; // 동전금액
		int [] Ni; // 동전개수
		ArrayList<String> resultList = new ArrayList<String>();
		
		String amt = coin.getAmt(); // 지폐의 금액
		String[] price = coin.getPrice(); // 동전금액
		String[] priceCnt = coin.getPriceCnt(); // 동전개수
		
		T = Integer.parseInt(amt == "" ? "0" : amt.replaceAll(",", ""));
		K = price.length;
		
		Pi = new int[K]; // 동전금액
		Ni = new int[K]; // 동전개수
		for(int i=0; i<K; i++) {
			Pi[i] = Integer.parseInt(price[i] == "" ? "0" : price[i].replaceAll(",", ""));
			Ni[i] = Integer.parseInt(priceCnt[i] == "" ? "0" : priceCnt[i].replaceAll(",", ""));
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
		
		
		
		// 1.각각 입력받은 동전개수중 가장 큰 값을 찾는다
		for(int i=0; i<K; i++) {
			if(Ni[i] > maxNi) maxNi = Ni[i];
		}
		logger.info("maxNi = "+maxNi);
		
		int [][] amtPi = new int[K][maxNi+1]; // 각 동전 사용개수

		// 2.각 동전별 '동전금액 * 개수' 값을 구한다
		for(int i=0; i<=maxNi; i++) {
			for(int j=0; j<K; j++) {
				if(i > Ni[j]) continue;
				amtPi[j][i] = Pi[j] * i;
			}
		}
		
//		int cnt = 0;
//		int h = 0;
		
		// init
		int [] coinCnt = new int[K];
		for(int i=0; i<K; i++) {
			coinCnt[i] = 0;
		}
		
//		while(true) {
//			for(int i=0; i<=maxNi; i++) {
//				for(int j=0; j<K; j++) {
//					cnt += 1;
//					
//					if(cnt % K == 0) {
//						coinCnt[j] = (cnt / K - 1) - (h * (maxNi+1));
//						
//						if(cnt % (K * (maxNi+1)) == 0) {
//							coinCnt[j-1] += 1;
//						}
//					}
//					
//					logger.info("cnt = " +cnt+ ", amtPi["+j+"]["+coinCnt[j]+"] = "+amtPi[j][coinCnt[j]]);
//				}
//			}
//			h += 1;
//			logger.info("");
//			
//			if(cnt == 100) break;
//		}
		
//		for(int g=K-1; g>=0; g--) {
//			for(int h=0; h<K; h++) {
//				for(int i=0; i<=Ni[g]; i++) {
//					for(int j=0; j<K; j++) {
//						logger.info("amtPi["+j+"]["+coinCnt[j]+"] = "+amtPi[j][coinCnt[j]]);
//					}
//					coinCnt[g] += 1;
//				}
//				coinCnt[g] = 0;
//			}
//			
//		}
		
		
		
//		for(int g=K; g>=0; g--) {
//			for(int h=0; h<=Ni[g-2]; h++) {
//				for(int i=0; i<=Ni[g-1]; i++) {
//					for(int j=0; j<K; j++) {
//						cnt += 1;
//						//logger.info("cnt = " + cnt + ", h = " + h);
//						if(cnt % K == 0) {
//							coinCnt[j] = (cnt / K - 1) - (h * (Ni[g-1]+1));
//							
//							if(cnt % (K * (Ni[g-1]+1)) == 0) {
//								coinCnt[j-1] += 1;
//							}
//						}
//						logger.info("amtPi["+j+"]["+coinCnt[j]+"] = "+amtPi[j][coinCnt[j]]);
//					}
//				}
//				logger.info("");
//			}
//			cnt = 0;
//		}
		
		
//		int cc = 0;
//		for(int h=0; h<=K; h++) {
//			for(int i=0; i<=maxNi; i++) {
//				for(int j=0; j<K; j++) {
//					int xx = 0;
//					cc += 1;
//					if(cc % K == 0)
//						xx = cc/K-1 > 0 ? cc/K-1 : 0;
//					else
//						xx = 0;
//					logger.info("amtPi["+j+"]["+xx+"] = "+amtPi[j][xx]);
//				}
//				if(cc >= (maxNi+1)*K) cc = 0;
//			}
//			logger.info("");
//		}
		
//		for(int j=0; j<K; j++) {
//			for(int i=Ni[j]; i>0; i--) {
//				//if(i > Ni[j]) continue;
//				//logger.info("amtPi["+j+"]["+i+"] = "+amtPi[j][i]);
//				if(T == amtPi[j][i]) {
//					logger.info(amtPi[j][i]+ " = " +Pi[j]+" x "+i);
//					continue;
//				}
//				
//				for(int x=0; x<K; x++) {
//					if(j >= x) continue;
//					for(int y=Ni[x]; y>=0; y--) {
//						if(T == amtPi[j][i] + amtPi[x][y]) {
//							logger.info(amtPi[j][i] + amtPi[x][y]+ " = " +Pi[j]+" x "+i+" + "+Pi[x]+" x "+y);
//							continue;
//						}
//					}
//				}
//			}
//			//logger.info("");
//		}
		
		
		
		
		//
		int cnt = 1;
		int temp = T;
		String txt = "";
		String txt2 = "";
		for(int i=1; i<=K; i++) {
            int pi = Pi[i-1]; // 동전금액
            int ni = Ni[i-1]; // 동전개수
            //System.out.println("i="+i+", pi="+pi+", ni="+ni+", temp="+temp);
            
            for(int j=ni; j>0; j--) {
            	//System.out.println("j="+j);
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
//            System.out.println("");
        }
		
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
