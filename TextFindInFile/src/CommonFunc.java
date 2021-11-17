import java.sql.ResultSet;
import java.sql.SQLException;

public class CommonFunc {
	private SQLiteManager manager;
	
	public CommonFunc() {
		manager = new SQLiteManager();
		//첫 이닛할때 한번만 하면 되서 인스턴스구문에 적어줌
		manager.createConnection();     // 연결
	}

	
	//szResult를 생성 해서 처리 하는 이유는 한번 검증 하기 위하여(유지보수에 용이)
	public ResultSet getJtextFieldSearchValue(String searchValue) {
		ResultSet szResult = manager.SelectQuery(searchValue);
		try {
			System.out.println("11111" + szResult.getString("title"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(szResult == null) {
			System.out.println("null @@@");
		}
		
		return szResult;
	

	}
  
}
