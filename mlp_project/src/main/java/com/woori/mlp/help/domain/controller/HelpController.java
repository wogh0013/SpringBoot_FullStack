package com.woori.mlp.help.domain.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.woori.mlp.common.FileUploadUtil;
import com.woori.mlp.help.domain.HelpDto;
import com.woori.mlp.help.service.HelpService;

//http://localhost:9090/help/guideList
@CrossOrigin("*")
@RequestMapping("/help")
@RestController
public class HelpController {
	@Value("${fileUploadPath}") // src/main/resources/application.properties�� ���� �а� �´�.
	String fileUploadPath;
	
	@Value("${domain}")
	String domain;
	
	@Autowired
	HelpService service;
	
	@GetMapping(value="/guideList")
	HashMap<String, Object> guideList(){
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("guideList", service.getGuideList());
		map.put("highLightList", service.getHighLightList());
		
		return map;
	}
	
	@GetMapping(value="/view/{id}")
	HelpDto guideView(@PathVariable("id")int id)
	{
		return service.getGuideView(id);
	}

	//DB�� ������ �����ϱ� - JSON ���·� �޾ƿ´�
	
	//1. ������ �����ؾ� �ϴ� ���
	//form �±׿� enctype="multipart/form-data"
	//FormData��� ��ü�� ���ؼ� ������ ������
	//���������� MultipartFile ��ü�� ����ؼ� ������ �����Ѵ�
	//postman, body �±׿� form-data ���� ���� �� ���
	
	//jsp ��� React�� ó��, React�� ���� ���� DB�� �ֱ⸸ �ϸ� �ȴ�
	@PostMapping(value="/save") // help/save
	HashMap<String, String> save(HelpDto dto, MultipartFile file_image)
	{
		String uploadDir = fileUploadPath+ "/image";
		
		HashMap<String, String> map = new HashMap<String, String>();
		String filename="";
		
		try {
			filename = FileUploadUtil.upload(uploadDir, file_image);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		dto.setCenter_image(domain + "/" + uploadDir + "/" + filename);
		dto.setImage_name(filename);
		
		service.insert(dto);
		
		map.put("result", "success");
		return map;
	}
	
	//2. ������ ������ �ʿ䰡 ���� ���
	//�׳� JSON
	//{title:"����", contents:"����"} , �� �������� �����Ѵ�
	//postman, body �±׿� x-www-form-urlencoded �� ���� �� Ű�� ���� ����
}
