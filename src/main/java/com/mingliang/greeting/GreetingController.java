package com.mingliang.greeting;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class GreetingController {

	private static List<News> newsQueue = new ArrayList<News>();

	@RequestMapping("/greeting")
	public List<Greeting> greeting(@RequestParam(value="name", defaultValue="World") String name) {
		List<Greeting> t = new ArrayList<Greeting>();
		t.add(new Greeting(1, "a"));
		t.add(new Greeting(2,"b"));
		t.add(new Greeting(3, "c"));
		t.remove(0);
		t.remove(0);
		return t;
	}


	@RequestMapping(method = RequestMethod.POST, value="/addnews")
	public void addNews(@RequestParam(value="news") String news,
						@RequestParam(value="date") String date,
						@RequestParam(value="title") String title,
						@RequestParam(value="subtitle") String subtitle,
						@RequestParam(value="url") String url,
						@RequestParam(value="src") String src) {
		newsQueue.add(new News(news, date, title, subtitle, url, src));
	}

	@RequestMapping(method = RequestMethod.GET, value="/getnews")
	public List<News> getNews() {
		return reverseReturn(newsQueue);
	}


	public static List<News> reverseReturn(List<News> alist) {
		if ( alist == null || alist.isEmpty()) {
			return null;
		}

		List<News> rlist = new ArrayList<News>(alist);

		Collections.reverse(rlist);
		return rlist;
	}

}