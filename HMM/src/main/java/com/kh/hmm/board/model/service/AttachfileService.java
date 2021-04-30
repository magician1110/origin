package com.kh.hmm.board.model.service;

import java.util.ArrayList;

import com.kh.hmm.board.model.vo.Attachfile;

public interface AttachfileService
{
	ArrayList<Attachfile> selectFileList(int bcode);
	
	int insertAttachfile(Attachfile file);
	
	int deleteAttachfile(int atcode);
	
	int updateAttachfile(Attachfile file);

	Attachfile selectFileOne(int atcode);
}
