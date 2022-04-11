package com.woori.mlp.help.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woori.mlp.help.domain.HelpDto;
import com.woori.mlp.help.repository.HelpDao;

@Service("helpService")
public class HelpServiceImpl implements HelpService{
	
	@Autowired
	HelpDao dao;
	
	@Override
	public List<HelpDto> getGuideList(){
		return dao.getGuideList();
	}
	
	@Override
	public HelpDto getGuideView(int id) {
		dao.updateHit(id); //보기할 때 조회수가 증가해야 한다
//		HelpDto dto = dao.getGuideView(id);
//		String contents = dto.getCenter_contents();
//		contents = contents.replaceAll("\n", "<br/>")
//				dto.setCenter_contents(contents);
		
		return dao.getGuideView(id);
	}

	@Override
	public void insert(HelpDto dto) {
		dao.insert(dto);
	}

	@Override
	public void updateHit(int id) {
		
	}

	@Override
	public List<HelpDto> getHighLightList() {
		return dao.getHighLightList();
	}
	
	
	
}
