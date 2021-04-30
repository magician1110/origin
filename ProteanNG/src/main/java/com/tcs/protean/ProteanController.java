package com.tcs.protean;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tcs.protean.bean.TestBean;
import com.tcs.protean.service.ProteanService;

@Controller
public class ProteanController {
	@Inject
	ProteanService service;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String protean(Model model) throws Exception {
		List<TestBean> list;
		list = service.protean();
		for(int i = 0; i < list.size(); i++) {
			System.out.println("list getCreate_date :" + list.get(i).getCreate_date());	
			System.out.println("list2 getCountry :" + list.get(i).getCountry());
			System.out.println("list3 getUser_name :" + list.get(i).getUser_name());
			System.out.println("list4 getContent :" + list.get(i).getContent());
		}
		
		model.addAttribute("list", list);
		
		return "proteanMain";
	}
	
}
