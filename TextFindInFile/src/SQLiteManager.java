import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteManager {
	
	 // 상수 설정
    //   - Database 변수
//    private static final String SQLITE_JDBC_DRIVER = "org.sqlite.JDBC";
//    private static final String SQLITE_FILE_DB_URL = "jdbc:sqlite:quartz.db";
//    //"jdbc:sqlite:C://sqlite/db/tests.db";
//    private static final String SQLITE_MEMORY_DB_URL = "jdbc:sqlite::memory";
	private static final String SQLITE_JDBC_DRIVER = "org.sqlite.JDBC";
	private static final String SQLITE_FILE_DB_URL = "jdbc:sqlite:" + "SEARCH_KY.db";
	//현재 사용 X
	//private static final String SQLITE_MEMORY_DB_URL = "jdbc:sqlite::memory";
	
    //  - Database 옵션 변수
    private static final boolean OPT_AUTO_COMMIT = false;
    private static final int OPT_VALID_TIMEOUT = 500;
 
    // 변수 설정
    //   - Database 접속 정보 변수
    private Connection conn = null;
    private String driver = null;
    private String url = null;
    private Statement statement = null;
    
    // 생성자
    public SQLiteManager(){
        this(SQLITE_FILE_DB_URL);
    }
    public SQLiteManager(String url) {
        // JDBC Driver 설정
        this.driver = SQLITE_JDBC_DRIVER;
        this.url = url;
    }
 
    // DB 연결 함수
    public Connection createConnection() {
    	try {
    		// JDBC Driver Class 로드
    		//SQLite JDBC 클래스가 있는지 검사하는 구문
    		Class.forName(this.driver);

    		// DB 연결 객체 생성
    		// 연결을 성공 했을시, Connection으로 부터 Statement 인스턴스를 얻음
    		this.conn = DriverManager.getConnection(this.url);
    		this.statement = conn.createStatement();

    		//searchTextGUI = new SearchTextGUI();
    	}catch (ClassNotFoundException | SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}

    	return this.conn;
    }
    public ResultSet SelectQuery(String query)
    {
    	ResultSet resultSet = null;
    	String str = "SELECT " + query + " FROM SEARCH_TABLE";
    	
    	try 
    	{ 
//    		// 로그 출력 
//    		System.out.println("CONNECTED");
//    		//System.out.println("searchTextGui 검색 값을 갖고 오는지 확인 하는 구문 == : \n" + searchTextGUI.getJtextFieldSearchValue());
//    		System.out.println("func ==  : ");
    		//결과를 갖고 있는 공간
    		resultSet = this.statement.executeQuery(str);
    	

    	} catch ( SQLException e) {
    		e.printStackTrace();
    	}

    	return resultSet;
    }
 
    // DB 연결 종료 함수
    public void closeConnection() {
        try {
            if( this.conn != null ) {
                this.conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.conn = null;
 
            // 로그 출력
            System.out.println("CLOSED");
        }
    }
 
    // DB 재연결 함수
    public Connection ensureConnection() {
        try {
            if( this.conn == null || this.conn.isValid(OPT_VALID_TIMEOUT) ) {
                closeConnection();      // 연결 종료
                createConnection();     // 연결
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
 
        return this.conn;
    }
 
    // DB 연결 객체 가져오기
    public Connection getConnection() {
        return this.conn;
    }
	
}
