package com.kh.hmm.board.model.service;

import java.util.ArrayList;

import com.kh.hmm.board.model.vo.Comments;
import com.kh.hmm.board.model.vo.CommentsPoint;

public interface CommentsService
{	
	ArrayList<Comments> selectCommentsList(int bcode);
		
	Comments selectCommentsOne(int ccode);
	
	int insertComments(Comments c);
	
	int updateComments(Comments c);
	
	int deletComments(int ccode);
	
	int checkComments(CommentsPoint point);

	Integer iscreport(int ccode, String reporter);
	
	void creport(int ccode, String reporter);

	int getCcode();

	int insertUComments(Comments c);
}
