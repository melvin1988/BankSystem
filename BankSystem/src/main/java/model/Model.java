package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Model {
	private int id;
	private String name;
	private String username;
	private String password;
	private String email;
	private double balance;
	private String verificationCode;
	private Timestamp verificationTime;
	private String transactionType;
	private Timestamp transactionDate;

	Connection con = null;
	PreparedStatement pStatement = null;
	ResultSet result = null;

	public Model() {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			con = DriverManager.getConnection("jdbc:mysql://localHost:3306/banksystem", "root", "1234");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Timestamp getVerificationTime() {
		return verificationTime;
	}

	public void setVerificationTime(Timestamp verificationTime) {
		this.verificationTime = verificationTime;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}

	//Used in Register.java
	public boolean usernameExists() {
		try {
			pStatement = con.prepareStatement("select * from customer");
			result = pStatement.executeQuery();

			while(result.next()) {
				String databaseUsername = result.getString(3);
				//Check customer username against all the other usernames in the database
				if(username.equals(databaseUsername)) {
					return true;
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	//Used in Register.java
	public int createCustomer() {
		int row = 0;

		try {
			pStatement = con.prepareStatement("insert into customer values(?, ?, ?, ?, ?, ?, ?, ?)");
			pStatement.setInt(1, id);
			pStatement.setString(2, name);
			pStatement.setString(3, username);
			pStatement.setString(4, password);
			pStatement.setString(5, email);
			pStatement.setDouble(6, 1000); //start new customers with a balance of $1000
			pStatement.setString(7, verificationCode);
			pStatement.setTimestamp(8, verificationTime);
			row = pStatement.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	//Used in Verify.java
	public int verifyCode() {
		try {
			pStatement = con.prepareStatement("select * from customer where username=?");
			pStatement.setString(1, username);
			result = pStatement.executeQuery();

			if(result.next()) {
				String databaseVerificationCode = result.getString(7);
				Timestamp databaseVerificationTime = result.getTimestamp(8);
				verificationTime = new Timestamp(new Date().getTime()); //set verification time to the time now
				Timestamp newTime = new Timestamp(verificationTime.getTime() - (2*60*1000)); //set newTime to the time 2 minutes ago

				//Valid code and code entered within 2 minutes
				//If newTime is before the verificationTime stored in the database, it means that less than 2 minutes has passed
				if(verificationCode.equals(databaseVerificationCode) && newTime.before(databaseVerificationTime)) {
					return 1;
				}
				//Code entered after 2 minutes
				else if(newTime.after(databaseVerificationTime)) {
					return -1;
				}
				//Invalid code
				else {
					return 0;
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	//Used in Verify.java
	public int deleteCustomer() {
		int row = 0;

		try {
			pStatement = con.prepareStatement("delete from customer where username=?");
			pStatement.setString(1, username);
			row = pStatement.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	//Used in Forget.java
	public boolean obtainCustomerDataUsingEmail() {
		try {
			pStatement = con.prepareStatement("select * from customer where email=?");
			pStatement.setString(1, email);
			result = pStatement.executeQuery();

			//Set all the required attributes in Model
			if(result.next()) {
				name = result.getString(2);
				username = result.getString(3);
				password = result.getString(4);
				return true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	//Used in Login.java
	public int validateLogin() {
		try {
			pStatement = con.prepareStatement("select * from customer where username=?");
			pStatement.setString(1, username);
			result = pStatement.executeQuery();

			if(result.next()) {
				String databasePassword = result.getString(4);
				Encryption encryption = new Encryption();
				//Valid username and password
				if(encryption.encrypt(password).equals(databasePassword)) {
					return 1;
				}
			}
			//Invalid password
			else {
				return -1;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		//Invalid username
		return 0;
	}

	//Used in ChangePasswordLinkSent.java
	public boolean obtainCustomerDataUsingUsername() {
		try {
			pStatement = con.prepareStatement("select * from customer where username=?");
			pStatement.setString(1, username);
			result = pStatement.executeQuery();

			//Set all the required attributes in Model
			if(result.next()) {
				id = result.getInt(1);
				name = result.getString(2);
				email = result.getString(5);
				balance = result.getDouble(6);
				return true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	//Used in ChangePassword.java
	public int changePassword() {
		int row = 0;

		try {
			pStatement = con.prepareStatement("update customer set password=? where username=?");
			pStatement.setString(1, password);
			pStatement.setString(2, username);
			row = pStatement.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	//Used in ApplyLoan.java
	public int createLoanTransaction() {
		int row = 0;

		try {
			pStatement = con.prepareStatement("insert into transaction values(?, ?, ?, ?)");
			pStatement.setInt(1, id);
			pStatement.setDouble(2, balance);
			pStatement.setString(3, transactionType);
			pStatement.setTimestamp(4, transactionDate);
			row = pStatement.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	//Used in Transfer.java
	public int updateBalance(ArrayList<Model> mList) {
		int row = 0;

		try {
			con.setAutoCommit(false);

			for(int i=0; i<mList.size(); i++) {
				pStatement = con.prepareStatement("update customer set balance=? where username=?");
				pStatement.setDouble(1, mList.get(i).getBalance());
				pStatement.setString(2, mList.get(i).getUsername());
				row += pStatement.executeUpdate();
			}
			con.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	//Used in Transfer.java
	public int createTransferTransaction() {
		int row = 0;

		try {
			pStatement = con.prepareStatement("insert into transaction values(?, ?, ?, ?)");
			pStatement.setInt(1, id);
			pStatement.setDouble(2, balance);
			pStatement.setString(3, transactionType);
			pStatement.setTimestamp(4, transactionDate);
			row = pStatement.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	//Used in TransactionHistory.java
	public ArrayList<Transaction> getTransactionData() {
		ArrayList<Transaction> tList = new ArrayList<Transaction>();

		try {
			pStatement = con.prepareStatement("select * from transaction where id=?");
			pStatement.setInt(1, id);
			result = pStatement.executeQuery();

			//Retrieve data and add to tList
			while(result.next()) {
				Double balance = result.getDouble(2);
				String transactionType = result.getString(3);
				Timestamp transactionDate = result.getTimestamp(4);
				tList.add(new Transaction(balance, transactionType, transactionDate));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return tList;
	}
}