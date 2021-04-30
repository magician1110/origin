package com.kh.hmm.board.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.hmm.board.model.vo.Comments;
import com.kh.hmm.board.model.vo.CommentsPoint;

@Repository("commentsDao")
public class CommentsDao
{
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public ArrayList<Comments> selectComments(int bcode) 
	{
		List<Comments> list=sqlSession.selectList("selectComments",bcode);
		
		return (ArrayList<Comments>)list;
	}

	public int insertComments(Comments c) 
	{
		return sqlSession.insert("insertComments",c);
	}
	
	public int updateComments(Comments c) 
	{
		return sqlSession.update("updateComments",c);
	}
	
	public int deleteComments(int ccode) 
	{
		return sqlSession.update("deleteComments",ccode);
	}
	
	public int checkComments(CommentsPoint point) 
	{
		return sqlSession.update("checkComments",point);
	}

	public Comments selectCommentsOne(int ccode)
	{
		return sqlSession.selectOne("selectCommentsOne",ccode);
	}

	public Integer iscReport(int ccode, String reporter)
	{
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("ccode", ccode);
		map.put("reporter", reporter);
		
		return sqlSession.selectOne("iscreport",map);
	}
	
	public void creport(int ccode, String reporter)
	{
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("ccode", ccode);
		map.put("reporter", reporter);
		
		sqlSession.insert("creport",map);
	}

	public int getCcode()
	{
		return sqlSession.selectOne("getCcode");
	}

	public int insertUComments(Comments c)
	{
		return sqlSession.insert("insertUComments",c);
	}
}
