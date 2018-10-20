import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectivity 
{
	public static void main(String[] args) throws SQLException, ClassNotFoundException 
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connect=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SYSTEM","SYSTEM");
		System.out.println("Connection Established with the server");

		// Basic Operations C=Create U=Update R=Read D=Delete
		
		
		String sql="CREATE TABLE PERSON(Id int,Name varchar2(300),Age int,State varchar2(100),City varchar2(255))";
		
		String sql1="INSERT INTO PERSON VALUES(?,?,?,?,?)";
		
		String sql2="Select * from PERSON";
		
		String sql3="UPDATE PERSON SET ID=? WHERE NAME='Ashley'";
		
		String sql4="DROP TABLE PERSON";
		
		//Deleting a table using Create Statement
		Statement delete=connect.createStatement();
		delete.execute(sql4);
		
		//Create a table using Create statement
		Statement newtable=connect.createStatement();
		newtable.execute(sql);
		
		//Insertion using the Prepared Statement
		
		PreparedStatement insert=connect.prepareStatement(sql1);
		insert.setInt(1,10);
		insert.setString(2,"James");
		insert.setInt(3, 21);
		insert.setString(4, "Alabama");
		insert.setString(5, "Troy");
		insert.execute();	
		
		//Insert using the Callable Statement
		
		CallableStatement callinsert =connect.prepareCall("{CALL INSERT_RECORD(?,?,?,?,?)}");
		callinsert.setInt(1, 11);
		callinsert.setString(2, "Ashley");
		callinsert.setInt(3, 25);
		callinsert.setString(4, "Texas");
		callinsert.setString(5, "Roundrock");
		callinsert.execute();
		
		//Viewing the Result set using the Create Statement
		System.out.println("Extracting All the Records");
		Statement view=connect.createStatement();
		ResultSet rs=view.executeQuery(sql2);
		
		while(rs.next())
		{
			System.out.println("###########################");
			System.out.println("Id :: "+rs.getInt(1));
			System.out.println("Name :: "+rs.getString(2));
			System.out.println("Age :: "+rs.getInt(3));
			System.out.println("State :: "+rs.getString(4));
			System.out.println("City :: " +rs.getString(5));
			System.out.println("###########################");
		}
		
		PreparedStatement update=connect.prepareStatement(sql3);
		update.setInt(1, 20);
		update.executeUpdate();
		ResultSet rs1=view.executeQuery(sql2);
		
		System.out.println("**********After Udating of Record************");
		while(rs1.next())
		{
			System.out.println("Id :: "+rs1.getInt(1));
			System.out.println("Name :: "+rs1.getString(2));
		}
		
	}
}
