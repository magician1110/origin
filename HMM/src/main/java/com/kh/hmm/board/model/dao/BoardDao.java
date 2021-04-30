package com.kh.hmm.board.model.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.hmm.board.model.vo.Board;
import com.kh.hmm.board.model.vo.BoardPoint;

@Repository("boardDao")
public class BoardDao {
	@Autowired
	private SqlSessionTemplate sqlSession;

	private String pre = null;
	private String post = null;
	private int boardNumber=9; 

	public BoardDao() {
	}

	private String distributor(int dis, String pre, String post) {
		String result = null;
		/*
		 * 1 기업 2 QnA 3 신기술 4 아무말대잔치 5 프로젝트/소스
		 */

		if (dis == 0)
			result = "All";
		else
			result = "";

		return pre + result + post;
	}

	public Board selectBoardOne(int bcode) {
		return (Board) sqlSession.selectOne("selectBoardOne", bcode);
	}

	public ArrayList<Board> selectBoardList(int dis,int first) {
		pre = "select";
		post = "BoardList";
		String val="A.POSTDATE DESC";
		
		HashMap map=new HashMap();
		map.put("dis", dis);
		map.put("first", first);
		map.put("second", first+boardNumber);
		map.put("sort",val );
		
		List<Board> list = sqlSession.selectList("selectAllBoardList", map);

		return (ArrayList<Board>) list;
	}

	public int insertBoard(Board b) {
		return sqlSession.insert("insertBoard", b);
	}

	public int updateBoard(Board b) {
		return sqlSession.update("updateBoard", b);
	}

	public int deleteBoard(int bcode) {
		return sqlSession.update("deleteBoard", bcode);
	}

	public int checkBoard(BoardPoint point) {
		return sqlSession.update("checkBoard", point);
	}

	public int boardCode() {
		return sqlSession.selectOne("boardCode");
	}

	public int updateAB(int bcode) {
		return sqlSession.update("updateAB", bcode);
	}

	public int write(Board b) {

		System.out.println(b + "in wirte in Dao");
		return sqlSession.insert("writeBoard", b);
	}

	public void recommendation(String recom, int bcode) {
		switch (recom) {
		case "best":
			sqlSession.update("bestrecommendation", bcode);
			break;
		case "good":
			sqlSession.update("goodrecommendation", bcode);
			break;
		case "bad":
			sqlSession.update("badrecommendation", bcode);
			break;
		case "worst":
			sqlSession.update("worstrecommendation", bcode);
			break;
		}

	}

	public void crecommendation(String recom, int ccode) {
		if (recom.compareTo("good") == 0)
			sqlSession.update("cgoodrecommendation", ccode);
		else
			sqlSession.update("cbadrecommendation", ccode);
	}

	public void bmedal(int bcode) {
		sqlSession.update("bmedal", bcode);
	}

	public void cmedal(int ccode) {
		sqlSession.update("cmedal", ccode);
	}

	public void breport(int bcode, String reporter) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("bcode", bcode);
		map.put("reporter", reporter);

		sqlSession.insert("breport", map);
	}

	public int isbreport(int bcode, String reporter) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("bcode", bcode);
		map.put("reporter", reporter);

		return sqlSession.selectOne("isbreport", map);
	}

	public void viewcount(int bcode) {
		sqlSession.update("viewcount", bcode);
	}

	public String boardName(int dis) {
		return sqlSession.selectOne("boardName", dis);
	}

	public List<Board> selectSearchBoardList(int dis, String keyword) {
		keyword = keyword.toUpperCase();
		System.out.println("DAO 키워드 : " + keyword);
		List<Board> list = null;
		HashMap map = new HashMap();
		map.put("keyword1", keyword);
		map.put("keyword2", keyword);
		if (dis == 0) {
			list = sqlSession.selectList("selectSearchBoardList", map);
		} else {
			map.put("dis", dis);
			list = sqlSession.selectList("selectSearchDisBoard", map);
		}
		return list;
	}

	public ArrayList<Board> selectNewTechList(char sm,Date date,int first)
	{
		HashMap map=new HashMap();
		String val=null;
		
		switch(sm) 
		{
		case 'r': val="A.POSTDATE DESC";
			break;
		case 'f': val="C.VIEWNUM DESC";
			break;
		case 'g': val="C.CAL DESC";
			break;
		}

		map.put("prev", date);
		map.put("next", date);
		map.put("dis", 3);
		map.put("first", first);
		map.put("second", first+boardNumber);
		map.put("sort",val );
		
		List<Board> list=sqlSession.selectList("selectAllBoardList",map);
		
		return (ArrayList<Board>)list;
	}

	public ArrayList<Board> sortList(char sm, int dis, int first)
	{
		HashMap map=new HashMap();
		String val=null;
		
		switch(sm) 
		{
		case 'r': val="A.POSTDATE DESC";
			break;
		case 'f': val="C.VIEWNUM DESC";
			break;
		case 'g': val="C.CAL DESC";
			break;
		}
		map.put("first", first);
		map.put("second", first+boardNumber);
		map.put("sort",val );
		map.put("dis", dis);

		List<Board> list = sqlSession.selectList("selectAllBoardList", map);
		
		return (ArrayList<Board>)list;
	}
	
	public ArrayList<Board> selectBoardList(String writerId) {
		List<Board> list = sqlSession.selectList("selectWriterList",writerId);
		return (ArrayList<Board>) list;
	}
	
	public ArrayList<Board> selectCommnetsWriterList(String writerId) {
		List<Board> list = sqlSession.selectList("selectCommentsWriterList",writerId);
		return (ArrayList<Board>) list;
	}
}
