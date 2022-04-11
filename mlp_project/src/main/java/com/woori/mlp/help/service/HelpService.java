package com.woori.mlp.help.service;

import java.util.List;

import com.woori.mlp.help.domain.HelpDto;

public interface HelpService {
	List<HelpDto> getGuideList();
	HelpDto getGuideView(int id);
	void insert(HelpDto dto);
	void updateHit(int id);
	List<HelpDto> getHighLightList();
}
