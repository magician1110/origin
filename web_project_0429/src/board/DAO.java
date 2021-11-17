package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DAO {
	DBConnect dbconnect = null;
	String sql = "";

	public DAO() {
		dbconnect = new DBConnect();
	}

	public int count() {
		Connection con = dbconnect.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = 0;

		try {
			sql = "SELECT COUNT(*) FROM board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1);
			}

		} catch (Exception e) {

		} finally {
			DBClose.close(con, pstmt, rs);
		}
		return cnt;
	}

	public String pasing(String data) {
		try {
			data = new String(data.getBytes("8859_1"), "UTF-8");
			// data = new String(data.getBytes ("iso-8859-1"), "UTF-8");
		} catch (Exception e) {
		}
		return data;
	}

	public ArrayList<VO> getMemberList() {
		Connection con = dbconnect.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<VO> alist = new ArrayList<VO>();

		try {
			sql = "SELECT NUM, NAME, TITLE, TIME, HIT, INDENT from board order by ref desc, step asc";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				VO vo = new VO();
				boolean dayNew = false;
				vo.setNum(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setTitle(rs.getString(3));

				Date date = new Date();
				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
				String year = (String) simpleDate.format(date);
				String yea = rs.getString(4).substring(0, 10);

				if (year.equals(yea)) {
					dayNew = true;
				}

				vo.setTime(yea);
				vo.setHit(rs.getInt(5));
				vo.setIndent(rs.getInt(6));
				vo.setDayNew(dayNew);
				alist.add(vo);
			}

		} catch (Exception e) {

		} finally {
			DBClose.close(con, pstmt, rs);
		}
		return alist;
	}

	public int getMax() {
		Connection con = dbconnect.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int max = 0;

		try {
			sql = "SELECT MAX(NUM) FROM board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				max = rs.getInt(1);
			}

		} catch (Exception e) {

		} finally {
			DBClose.close(con, pstmt, rs);
		}
		return max;
	}

	public void insertWrite(VO vo, int max) {
		Connection con = dbconnect.getConnection();
		PreparedStatement pstmt = null;

		try {
			sql = "INSERT INTO board(NUM,NAME,PASSWORD,TITLE,MEMO,REF,FILES) VALUES(board_seq.nextval,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, pasing(vo.getName()));
			pstmt.setString(2, pasing(vo.getPassword()));
			pstmt.setString(3, pasing(vo.getTitle()));
			pstmt.setString(4, pasing(vo.getMemo()));
			pstmt.setInt(5, max + 1);
			pstmt.setString(6, pasing(vo.getFiles()));

			System.out.println(pasing(vo.getName()));

			pstmt.execute();
		} catch (Exception e) {

		} finally {
			DBClose.close(con, pstmt);
		}
	}

	public VO getView(int idx) {
		Connection con = dbconnect.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		VO vo = null;

		try {
			sql = "SELECT NAME, TITLE, MEMO, TIME, HIT, PASSWORD, REF, INDENT, STEP, FILES FROM board WHERE NUM=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new VO();
				vo.setName(rs.getString(1));
				vo.setTitle(rs.getString(2));
				vo.setMemo(rs.getString(3));
				vo.setTime(rs.getString(4));
				vo.setHit(rs.getInt(5) + 1);
				vo.setPassword(rs.getString(6));
				vo.setRef(rs.getInt(7));
				vo.setIndent(rs.getInt(8));
				vo.setStep(rs.getInt(9));
				vo.setFiles(rs.getString(10));
			}

		} catch (Exception e) {

		} finally {
			DBClose.close(con, pstmt, rs);
		}
		return vo;
	}

	public void UpdateHit(int idx) {
		Connection con = dbconnect.getConnection();
		PreparedStatement pstmt = null;

		try {
			sql = "UPDATE board SET HIT=HIT+1 where NUM=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();

		} catch (Exception e) {

		} finally {
			DBClose.close(con, pstmt);
		}
	}

	public boolean checkPassword(VO vo, int idx) {
		Connection con = dbconnect.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean ch = false;

		try {
			sql = "SELECT NUM FROM board where NUM=? and PASSWORD=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.setString(2, vo.getPassword());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				ch = true;
			} else {
				ch = false;
			}

		} catch (Exception e) {

		} finally {
			DBClose.close(con, pstmt, rs);
		}
		return ch;
	}

	public void delete(int idx) {
		Connection con = dbconnect.getConnection();
		PreparedStatement pstmt = null;

		try {
			sql = "DELETE FROM board WHERE NUM=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();

		} catch (Exception e) {

		} finally {
			DBClose.close(con, pstmt);
		}
	}

	public void filedel(int idx) {

		Connection con = dbconnect.getConnection();
		PreparedStatement pstmt = null;

		try {
			sql = "update board set files=null where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch (Exception e) {

		} finally {
			DBClose.close(con, pstmt);
		}
	}

	public void modify(VO vo, int idx) {
		Connection con = dbconnect.getConnection();
		PreparedStatement pstmt = null;

		try {
			if (vo.getFiles() == null) { //첨부파일이 없으면
				sql = "UPDATE board SET TITLE=?, MEMO=? where NUM=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, pasing(vo.getTitle()));
				pstmt.setString(2, pasing(vo.getMemo()));
				pstmt.setInt(3, idx);
				pstmt.executeUpdate();
			} else { //첨부파일이 있으면
				sql = "UPDATE board SET TITLE=?, MEMO=?, FILES=? where NUM=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, pasing(vo.getTitle()));
				pstmt.setString(2, pasing(vo.getMemo()));
				pstmt.setString(3, pasing(vo.getFiles()));
				pstmt.setInt(4, idx);
				pstmt.executeUpdate();
			}
		} catch (Exception e) {

		} finally {
			DBClose.close(con, pstmt);
		}
	}

	public void UpdateStep(int ref, int step) {
		Connection con = dbconnect.getConnection();
		PreparedStatement pstmt = null;

		try {
			sql = "UPDATE board SET STEP=STEP+1 where REF=? and STEP>?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ref);
			pstmt.setInt(2, step);
			pstmt.executeUpdate();

		} catch (Exception e) {

		} finally {
			DBClose.close(con, pstmt);
		}
	}

	public void insertReply(VO vo, int ref, int indent, int step) {
		Connection con = dbconnect.getConnection();
		PreparedStatement pstmt = null;

		try {
			sql = "INSERT INTO board(NUM,NAME, PASSWORD, TITLE, MEMO, REF, INDENT, STEP, FILES) "
					+ "VALUES(board_seq.nextval,?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, pasing(vo.getName()));
			pstmt.setString(2, pasing(vo.getPassword()));
			pstmt.setString(3, pasing(vo.getTitle()));
			pstmt.setString(4, pasing(vo.getMemo()));
			pstmt.setInt(5, ref);
			pstmt.setInt(6, indent + 1);
			pstmt.setInt(7, step + 1);
			pstmt.setString(8, pasing(vo.getFiles()));

			pstmt.execute();
		} catch (Exception e) {

		} finally {
			DBClose.close(con, pstmt);
		}
	}

}