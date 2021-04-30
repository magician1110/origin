package com.kh.hmm.board.model.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.kh.hmm.board.model.vo.Board;
import com.kh.hmm.board.model.vo.BoardPoint;

public interface BoardService {
	Board selectBoardOne(int bcode);

	ArrayList<Board> selectBoardList(int dis,int first);
	// 0:All,1:Com,2:QnA,3:Tech,4:Amu,5:PS

	int insertBoard(Board b);

	int updateBoard(Board b);

	int deletBoard(int bcode);

	int checkBoard(BoardPoint point);

	int updateAB(int bcode);

	int writeService(Board b);

	int boardCode();

	void recommendation(String recom, int bcode);

	void crecommendation(String recom, int ccode);

	void bmedal(int bcode);

	void cmedal(int ccode);

	void breport(int bcode, String reporter);

	int isbreport(int bcode, String reporter);

	void viewcount(int bcode);

	String boardName(int dis);

	ArrayList<Board> selectNewTechList(char sm,Date date,int first);

	List<Board> selectSearchBoardList(int dis, String keyword);

	ArrayList<Board> sortList(char sm, int dis, int first);
	
	ArrayList<Board> selectBoardList(String writerId);

	ArrayList<Board> selectCommentsWriterList(String writerId);
}
