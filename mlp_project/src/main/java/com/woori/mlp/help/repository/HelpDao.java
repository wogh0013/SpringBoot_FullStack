package com.woori.mlp.help.repository;

import java.util.List;

import com.woori.mlp.help.domain.HelpDto;

public interface HelpDao {

	List<HelpDto> getGuideList();
	HelpDto getGuideView(int id);
	void insert(HelpDto dto); //������ ���
	void updateHit(int id);   //��ȸ�� ����
	
	List<HelpDto> getHighLightList();
}
