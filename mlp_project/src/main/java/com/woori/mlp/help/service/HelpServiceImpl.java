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
		return dao.getGuideView(id);
	}

	@Override
	public void insert(HelpDto dto) {
		dao.insert(dto);
	}

	@Override
	public void updateHit(int id) {
		dao.updateHit(id);
	}
	
}
