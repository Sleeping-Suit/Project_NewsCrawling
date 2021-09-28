package com.cos.newscraw.util;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cos.newscraw.domain.News;


@Component
public class NaverCraw {

	int aidNum = 1; 
	
	public List<News> collect5() {
		RestTemplate rt = new RestTemplate();
		List<News> newsList = new ArrayList<>();
		
		for (int i = 1; i < 6; i++) {
			String aid = String.format("%010d", aidNum);
			String url = "https://news.naver.com/main/read.naver?mode=LSD&mid=shm&sid1=102&oid=022&aid="+aid;
			String html = rt.getForObject(url, String.class);
			
			Document doc = Jsoup.parse(html);
					
			Elements companyElement = doc.select(".section").select("[em]");
			Element titleElement = doc.selectFirst("#articleTitle");
			Element timeElement = doc.selectFirst(".t11");
			
			String company = companyElement.text();
			String title = titleElement.text();
			String createdAt = timeElement.text();
			
			News news = News.builder().company(company).title(title).createdAt(createdAt).build();
			
			newsList.add(news);
			
			aidNum ++;
		}
		return newsList;
	}
}