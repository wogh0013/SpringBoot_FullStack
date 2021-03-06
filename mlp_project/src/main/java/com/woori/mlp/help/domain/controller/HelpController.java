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
	@Value("${fileUploadPath}") // src/main/resources/application.properties의 값을 읽고 온다.
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

	//DB에 데이터 저장하기 - JSON 형태로 받아온다
	
	//1. 파일을 전송해야 하는 경우
	//form 태그에 enctype="multipart/form-data"
	//FormData라는 객체를 통해서 정보를 보내고
	//서버에서는 MultipartFile 객체를 사용해서 파일을 수신한다
	//postman, body 태그에 form-data 파일 전송 시 사용
	
	//jsp 대신 React가 처리, React가 보낸 값을 DB에 넣기만 하면 된다
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
	
	//2. 파일을 전송할 필요가 없을 경우
	//그냥 JSON
	//{title:"제목", contents:"내용"} , 이 형식으로 전송한다
	//postman, body 태그에 x-www-form-urlencoded 를 선택 후 키와 값을 전송
}
