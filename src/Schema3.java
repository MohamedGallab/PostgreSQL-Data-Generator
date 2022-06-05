import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Schema3 {

//	CREATE TABLE Sailors(sid INT PRIMARY KEY, sname CHAR(20), rating INT, age REAL);

	public static long insertSailor(int ID, String Name, int rating, double age, Connection conn) {
		String SQL = "INSERT INTO Sailors(sid,sname,rating,age) " + "VALUES(?,?,?,?);";

		long id = -1;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, ID);
			pstmt.setString(2, Name);
			pstmt.setInt(3, rating);
			pstmt.setDouble(4, age);

			int affectedRows = pstmt.executeUpdate();
			System.out.println("Number of affected rows is " + affectedRows);
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
//                	 System.out.println(rs.next());
					if (rs.next()) {
						id = rs.getLong(1);
						pstmt.close();
						conn.commit();
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					System.out.println(ex.getMessage());
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return id;
	}

//	 CREATE TABLE Boat(bid INT PRIMARY KEY, bname CHAR(20), color CHAR(10));
	public static long insertBoat(int ID, String Name, String color, Connection conn) {
		String SQL = "INSERT INTO Boat(bid,bname,color) " + "VALUES(?,?,?);";

		long id = -1;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, ID);
			pstmt.setString(2, Name);
			pstmt.setString(3, color);

			int affectedRows = pstmt.executeUpdate();
			System.out.println("Number of affected rows is " + affectedRows);
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
//                	 System.out.println(rs.next());
					if (rs.next()) {
						id = rs.getLong(1);
						pstmt.close();
						conn.commit();
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					System.out.println(ex.getMessage());
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return id;
	}

//	 CREATE TABLE Reserves(sid INT REFERENCES Sailors, bid INT REFERENCES Boat, day date, PRIMARY KEY(sid,bid));
	public static long insertReserves(int sID, int bID, Date day, Connection conn) {
		String SQL = "INSERT INTO Reserves(sid,bid,day) " + "VALUES(?,?,?);";

		long id = -1;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, sID);
			pstmt.setInt(2, bID);
			pstmt.setDate(3, day);

			int affectedRows = pstmt.executeUpdate();
			System.out.println("Number of affected rows is " + affectedRows);
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
//                	 System.out.println(rs.next());
					if (rs.next()) {
						id = rs.getLong(1);
						pstmt.close();
						conn.commit();
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					System.out.println(ex.getMessage());
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return id;
	}

	///////////////////////////////////////////////////////// Data Population
	///////////////////////////////////////////////////////// Methods
	///////////////////////////////////////////////////////// //////////////////////////////////////////////////////////
	public static void populateSailor(Connection conn) {
		Queue<String> firstName = new LinkedList<>();
		Queue<String> lastName = new LinkedList<>();
		firstName.add("Mohamed");
		firstName.add("Ahmed");
		firstName.add("Youssef");
		firstName.add("Omar");
		firstName.add("Mahmoud");
		firstName.add("Michael");
		firstName.add("Sara");
		firstName.add("Sandy");
		firstName.add("Farah");
		firstName.add("Mostafa");
		firstName.add("Osama");
		firstName.add("Patrick");
		firstName.add("Haley");
		firstName.add("Emma");
		firstName.add("Ziad");
		firstName.add("Mourad");
		lastName.add("Osman");
		lastName.add("Tamer");
		lastName.add("AboElDahab");
		lastName.add("Davidsdottir");
		lastName.add("Thorisson");
		lastName.add("Fraser");
		lastName.add("Velner");
		lastName.add("Toomey");
		lastName.add("Froning");
		lastName.add("Adams");
		double age;
		Random rating = new Random();
		for (int i = 0; i < 19000; i++) {
			String fName = firstName.remove();
			String lName = lastName.remove();
			String name = fName + " " + lName;
			firstName.add(fName);
			lastName.add(lName);
			int rate = (int) rating.nextInt(11);
			age = Math.random() * 55 + 15;
			if (insertSailor(i, name, rate, age, conn) == -1) {
				System.err.println("insertion of record " + i + " failed");
				break;
			} else
				System.out.println("insertion was successful");
		}
	}

	public static void populateBoat(Connection conn) {
		Queue<String> Colors = new LinkedList<>();
		Colors.add("red");
		Colors.add("green");
		Colors.add("blue");
		Colors.add("black");
		Colors.add("yellow");
		Colors.add("white");
		for (int i = 0; i < 3000; i++) {
			String Color = Colors.remove();
			Colors.add(Color);
			if (insertBoat(i, "Boat" + i, Color, conn) == -1) {
				System.err.println("insertion of record " + i + " failed");
				break;
			} else
				System.out.println("insertion was successful");
		}
	}

	@SuppressWarnings("deprecation")
	public static void populateReserves(Connection conn) {
		Random Num = new Random();
		int bid = 0;
		int sid = 0;
		
		for (int i = 0; i < 35000; i++) {
			int day = Num.nextInt(1, 28);
			int Month = Num.nextInt(1, 13);
			int Year = Num.nextInt(1970, 2021);
			boolean flag=false;
			if (i<1000) {
				if (insertReserves(sid++, 103, new Date(day, Month, Year), conn) == -1) {
					System.err.println("insertion of record " + i + " failed");
					break;
				} else
					System.out.println("insertion was successful");
			} else {
				if (bid==103) {
					bid++;
				}
				if (bid%6==0 && flag) {
					if (insertReserves(sid, bid++, new Date(day, Month, Year), conn) == -1) {
						System.err.println("insertion of record " + i + " failed");
						break;
					} else
						System.out.println("insertion was successful");
					if (insertReserves(sid++, bid++, new Date(day, Month, Year), conn) == -1) {
						System.err.println("insertion of record " + i + " failed");
						break;
					} else
						System.out.println("insertion was successful");
				}else {
					if (insertReserves(sid++, bid++, new Date(day, Month, Year), conn) == -1) {
						System.err.println("insertion of record " + i + " failed");
						break;
					} else
						System.out.println("insertion was successful");	
				}
			}
			
			if (bid==3000) {
				bid=0;
			}
			if (sid==19000) {
				sid=0;
			}
			
//			
		}
	}

	public static void insertSchema3(Connection connection) {
		populateSailor(connection);
		populateBoat(connection);
		populateReserves(connection);
	}

	public static void main(String[] argv) {

		System.out.println("-------- PostgreSQL " + "JDBC Connection Testing ------------");

		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
			e.printStackTrace();
			return;

		}

		System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;

		try {

			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/schema3", "postgres", "123");

			insertSchema3(connection);

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;

		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}
}
