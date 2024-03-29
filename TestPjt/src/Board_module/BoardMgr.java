package Board_module;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;


public class BoardMgr {

	private DBConnectionMgr pool;
	

	public BoardMgr() {
		try {
			pool = DBConnectionMgr.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//게시판 목록 불러오기
	public Vector<BoardBean> getBoardList(String keyField, String keyWord,int start, int end) {
		Connection con =null; //자바에서 DB로 sql 전송
		PreparedStatement pstmt =null;//DB에서 자바로 sql 결과 전송
		ResultSet rs =null;//sql문 실행 결과
		String sql =null;//sql 실행 문장
		Vector<BoardBean> vlist =new Vector<BoardBean>();
		try {
			con = pool.getConnection();
			System.out.println("게시판 불러오기\n");
			if(keyWord.equals("null") || keyWord.equals("")) {
				sql = "select * from tableboard order by num desc limit ?,?";
				//번호를 기준으로 내림 차순으로 출력
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1,start);
				pstmt.setInt(2,end);
			} else {//검색하면 동작
				System.out.printf("in java keyField=%s keyWord=%s\n",keyField,keyWord);
				sql = "select * from  tableboard where " + keyField + " like ? ";
				sql += "order by num desc, pos limit ? , ?";
				pstmt =con.prepareStatement(sql);
				pstmt.setString(1, "%" + keyWord + "%");//찾고 싶은 내용
				pstmt.setInt(2, start);//1부터 10까지만 한 화면에 표시
				pstmt.setInt(3, end);
			}
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardBean bean = new BoardBean();
				bean.setNum(rs.getInt("num"));
				bean.setTitle(rs.getString("title"));
				bean.setPos(rs.getInt("pos"));
				bean.setSort(rs.getString("sort"));
				bean.setId(rs.getString("id"));
				bean.setLogdate(rs.getString("Logdate"));
				bean.setCount(rs.getInt("count"));
				vlist.add(bean);
			}
		}catch (Exception e){
			e.printStackTrace();
		} finally {
			pool.freeConnection(con,pstmt,rs);
		}
		return vlist;
	}
	
	//게시글 갯수 확인하기
	public int getTotalCount(String keyField, String keyWord) {
		Connection conn = null; //자바에서 DB로 sql문 전송
		PreparedStatement pstmt = null;//DB에서 자바로 결과 전송
		ResultSet rs = null;//sql문 실행 결과
		String sql =null;//sql실행 문장
		
		int totalCount = 0;
		try {
			conn = pool.getConnection();
			if (keyWord.equals("null")||keyWord.equals("")) {
				sql="select count(num) from tableboard" ;
				pstmt = conn.prepareStatement(sql);
				//키워드가 비워있을 때 키필드에서 count를 찾는다
			} else {
				sql = "select count(num) from  tableboard where " + keyField + " like ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + keyWord + "%");//찾고 싶은 내용
				//키워드가 있을 때
			}
			rs =pstmt.executeQuery();//sql문을 실행
			if(rs.next()) {
				totalCount = rs.getInt(1);//컬럼의 첫번 째 숫자를 가지고 온다
			}
		}catch (Exception e) {
			e.printStackTrace(); // 예외 일때 잡아주기
		}finally {
			pool.freeConnection(conn, pstmt, rs); //사용했던 데이터 반환
		}
		return totalCount;
	}
	//게시판 글쓰기 기능 구현
	public void insertBoard(HttpServletRequest req) {

		 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String id=req.getParameter("id"),sort=req.getParameter("sort"),title=req.getParameter("title")
				,content=req.getParameter("content"),ip=req.getParameter("ip");
		Integer pos=0,count=0;
		System.out.println(" id: "+id+" sort: "+sort+" title: "+title+" content: "+content+" ip: "+ip+"\n");
		
		
		try {
			
			conn=pool.getConnection();
			//sql문으로 파라메터 가지고 오기
			/* sql="insert tableboard(id,sort,title,content,ip,pos,count)"; */
			sql="insert into tableboard(id,sort,title,content,ip,pos,count)";
			sql+="values(?, ?, ?, ?, ?, ?, ?)";
			pstmt =conn.prepareStatement(sql);
			
			pstmt.setString(1, id.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(2, sort.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(3, title.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(4, content.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(5, ip);
			pstmt.setInt(6, pos);
			pstmt.setInt(7, count);
			pstmt.executeUpdate();
			
				
			
		}catch(Exception e) {
			e.printStackTrace(); //예외 처리
		}finally {
			pool.freeConnection(conn,pstmt,rs);//데이터 반환
		}
	}
	
	//게시판 데이터 읽기
	public BoardBean getBoard(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		BoardBean bean = new BoardBean();
		try {
			con = pool.getConnection();
			sql = "select * from tableboard where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				bean.setNum(rs.getInt("num"));
				bean.setId(rs.getString("id"));
				bean.setTitle(rs.getString("title"));
				bean.setContent(rs.getString("content"));
				bean.setPos(rs.getInt("pos"));
				bean.setLogdate(rs.getString("logdate"));
				bean.setCount(rs.getInt("count"));
				bean.setIp(rs.getString("ip"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return bean;
	}

	//조회수 증가
	public void upCount(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "update tableboard set count=count+1 where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
	}

	//게시물 삭제
	public void deleteBoard(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = pool.getConnection();
			
			sql = "delete from tableboard where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
	}

	//게시물 수정
	public void updateBoard(BoardBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "update tableboard set  title=?, content = ?,sort= ?,logdate=now() where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getTitle());
			pstmt.setString(2, bean.getContent());
			pstmt.setString(3, bean.getSort());
			pstmt.setInt(4, bean.getNum());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
	}

	
}