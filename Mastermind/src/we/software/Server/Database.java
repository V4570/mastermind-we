package we.software.Server;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;


public class Database {

	public Connection con = null;
	private String url;
	private String dbusername;
	private String dbpassword;
	private String tablename = "players";
	private String date = "CURRENT_TIMESTAMP";
	
	public Database() {
		url = "jdbc:mysql://127.0.0.1:3306/mastermind";
		dbusername = "";
		dbpassword = "";

		try {
			con = DriverManager.getConnection(url, dbusername, dbpassword);
			//st = con.createStatement();
		}

		catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Den douleuei");

		}
	}

	public boolean usernameExists(String username) throws SQLException {
		
		String upd = "SELECT * FROM "+tablename
				+" WHERE username = ?";
		PreparedStatement ps = con.prepareStatement(upd);
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		return rs.next();

	}
	
	public boolean passwordCheck(String username, String password) throws SQLException{
		String upd = "SELECT password FROM "+tablename
				+" WHERE username = ? AND password = ?";
		PreparedStatement ps = con.prepareStatement(upd);
		ps.setString(1, username);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		return rs.next();
		
	}
	
	
	public void addUser(String username, String password) throws SQLException{
		String upd = "INSERT INTO "+ tablename
				 +" (username, password, date) "+
				  "VALUES (?,?,?)";
		PreparedStatement ps = con.prepareStatement(upd);
		ps.setString(1, username);
		ps.setString(2, password);
		ps.setString(3, date);
		ps.execute();
	}
	
	public void updatePassword(String username, String password) throws SQLException{
		String upd = "UPDATE players SET password = ? WHERE username = ? ";
		PreparedStatement ps = con.prepareStatement(upd);
		ps.setString(1, password);
		ps.setString(2, username);
		ps.executeUpdate();
	}
	
	public void updateHighScore(String username, int highscore) throws SQLException{
		String upd = "UPDATE players SET highscore = ? WHERE username = ? ";
		PreparedStatement ps = con.prepareStatement(upd);
		ps.setInt(1, highscore);
		ps.setString(2, username);
		ps.executeUpdate();
	}
	
	public void updateDate(String username) throws SQLException{
		String upd = "UPDATE players SET date = ? WHERE username = ? ";
		PreparedStatement ps = con.prepareStatement(upd);
		ps.setString(1, date);
		ps.setString(2, username);
		ps.executeUpdate();
	}

}
