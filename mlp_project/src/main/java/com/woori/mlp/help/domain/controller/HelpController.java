package com.woori.mlp.help.domain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woori.mlp.help.domain.HelpDto;
import com.woori.mlp.help.service.HelpService;

@CrossOrigin("*")
@RequestMapping("/help")
@RestController
public class HelpController {
	
	@Autowired
	HelpService service;
	
	@GetMapping(value="/guideList")
	List<HelpDto> guideList(){
		return service.getGuideList();
	}
	
	@GetMapping(value="/view/{id}")
	HelpDto guideView(@PathVariable("id")int id)
	{
		return service.getGuideView(id);
	}

}
