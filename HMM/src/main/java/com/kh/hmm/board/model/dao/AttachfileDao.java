package com.kh.hmm.board.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.hmm.board.model.vo.Attachfile;

@Repository("attachfileDao")
public class AttachfileDao
{
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public ArrayList<Attachfile> selectAttachfiles(int bcode)
	{
		List<Attachfile> list=sqlSession.selectList("attachfileList",bcode);

		return (ArrayList<Attachfile>)list;
	}

	public int insertAttachfile(Attachfile file)
	{
		return sqlSession.insert("attachInsert",file);
	}

	public int deleteAttachfile(int atcode)
	{
		return sqlSession.delete("attachDelete",atcode);
	}

	public int updateAttachfile(Attachfile file)
	{
		return sqlSession.update("attachUpdate",file);
	}

	public Attachfile selectFileOne(int atcode)
	{
		return sqlSession.selectOne("selectFileOne",atcode);
	}	
}
