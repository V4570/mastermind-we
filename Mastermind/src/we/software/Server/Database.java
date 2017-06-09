package we.software.Server;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

public class Database {

	private Connection con = null;
	private String url;
	private String dbusername;
	private String dbpassword;
	private String tablename = "players";

	public Database() {
		url = "jdbc:mysql://127.0.0.1:3306/mastermind";
		dbusername = "";
		dbpassword = "";

		try {
			con = DriverManager.getConnection(url, dbusername, dbpassword);
			// st = con.createStatement();
		}

		catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Den douleuei");

		}
	}

	public boolean usernameExists(String username) throws SQLException {

		String upd = "SELECT * FROM " + tablename + " WHERE username = ?";
		PreparedStatement ps = con.prepareStatement(upd);
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		return rs.next();

	}

	public boolean passwordCheck(String username, String password) throws SQLException, UnsupportedEncodingException {
		String upd = "SELECT * FROM " + tablename + " WHERE username = ?";
		String salt;
		PreparedStatement ps = con.prepareStatement(upd);
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
		salt = rs.getString("salt");
		String upd1 = "SELECT password FROM " + tablename + " WHERE username = ? AND password = ?";
		ps = con.prepareStatement(upd1);
		ps.setString(1, username);
		ps.setString(2, this.get_SHA_512_SecurePassword(password, salt));
		ResultSet rs1 = ps.executeQuery();
		return rs1.next();
		}else{
			return false;
		}
		

	}

	public void addUser(String username, String password) throws SQLException, UnsupportedEncodingException {
		String upd = "INSERT INTO " + tablename + " (username, password, salt) " + "VALUES (?,?,?)";
		PreparedStatement ps = con.prepareStatement(upd);
		String salt = this.getSaltString();
		String pass = this.get_SHA_512_SecurePassword(password, salt);
		ps.setString(1, username);
		ps.setString(2, pass);
		ps.setString(3, salt);
		ps.execute();
	}

	public void updatePassword(String username, String password) throws SQLException, UnsupportedEncodingException {
		String upd = "UPDATE players SET password, salt = ? WHERE username = ? ";
		PreparedStatement ps = con.prepareStatement(upd);
		String salt = this.getSaltString();
		ps.setString(1, this.get_SHA_512_SecurePassword(password, salt));
		ps.setString(2, salt);
		ps.setString(3, username);
		ps.executeUpdate();
	}

	public void updateHighScore(String username, int highscore) throws SQLException {
		String upd = "UPDATE players SET highscore = ? WHERE username = ? ";
		PreparedStatement ps = con.prepareStatement(upd);
		ps.setInt(1, highscore);
		ps.setString(2, username);
		ps.executeUpdate();
	}

	public void updateDate(String username) throws SQLException {
		String upd = "UPDATE players SET date = ? WHERE username = ? ";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		PreparedStatement ps = con.prepareStatement(upd);
		ps.setString(1, sdf.format(timestamp));
		ps.setString(2, username);
		ps.executeUpdate();
	}

	public String getHighScores() throws SQLException {
		ArrayList<String> hs = new ArrayList<String>();
		int h;
		String name;
		String get = "SELECT username, highscore FROM players ORDER BY highscore DESC";
		Statement s = con.createStatement();
		s.executeQuery(get);
		ResultSet rs = s.getResultSet();
		while (rs.next()) {
			name = rs.getString("username");
			h = rs.getInt("highscore");
			hs.add(name + " " + h);
		}

		String b = "";

		for (int i = 0; i < 4; i++) {
			if (i != 0) {
				b = b + ",";
			}
			b = b + hs.get(i);
		}
		return b;
	}

	public String get_SHA_512_SecurePassword(String passwordToHash, String salt) throws UnsupportedEncodingException {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt.getBytes("UTF-8"));
			byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	protected String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 18) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

}
