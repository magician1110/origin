package com.kh.hmm.board.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.hmm.board.model.dao.CommentsDao;
import com.kh.hmm.board.model.vo.Comments;
import com.kh.hmm.board.model.vo.CommentsPoint;


@Service("commentsService")
public class CommentsServiceImpl implements CommentsService
{

	@Autowired
	private CommentsDao cDao;

	@Override
	public ArrayList<Comments> selectCommentsList(int bcode)
	{
		return cDao.selectComments(bcode);
	}

	@Override
	public int insertComments(Comments c)
	{
		return cDao.insertComments(c);
	}
	
	@Override
	public int insertUComments(Comments c)
	{
		return cDao.insertUComments(c);
	}	
	
	@Override
	public int updateComments(Comments c)
	{
		return cDao.updateComments(c);
	}

	@Override
	public int checkComments(CommentsPoint point)
	{
		return cDao.checkComments(point);
	}

	@Override
	public Comments selectCommentsOne(int ccode)
	{
		return cDao.selectCommentsOne(ccode);
	}

	@Override
	public Integer iscreport(int ccode, String reporter)
	{
		return cDao.iscReport(ccode,reporter);
	}

	@Override
	public void creport(int ccode, String reporter)
	{
		cDao.creport(ccode, reporter);
	}

	@Override
	public int getCcode()
	{
		return cDao.getCcode();
	}

	@Override
	public int deletComments(int ccode)
	{		
		return cDao.deleteComments(ccode);
	}

}
