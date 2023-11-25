	package application.model;
	
	import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import application.entities.Account;
import application.entities.Account.AccountStatus;
	
	public class AccountModel {
		public static boolean create(Account account) {
			boolean result = true;
			try {
				PreparedStatement preparedStatement = ConnectDB.connection()
						.prepareStatement("insert into account(username,password,fullname,email,role,term,isFirstLogin) values(?,?,?,?,?,?,TRUE)");
				preparedStatement.setString(1, account.getUsername());
				preparedStatement.setString(2, account.getPassword());
				preparedStatement.setString(3, account.getFullname());
				preparedStatement.setString(4, account.getEmail());
				preparedStatement.setString(5, account.getRole());
				preparedStatement.setBoolean(6, account.getTerm());
				
				result = preparedStatement.executeUpdate() > 0;
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			} finally {
				
				ConnectDB.disconnect();
			}
			return result;
		}
//		public static boolean createNewInfo(Account account) {
//		    boolean result = true;
//		    try {
//		        PreparedStatement preparedStatement = ConnectDB.connection()
//		                .prepareStatement("insert into account(IdentityNumber,phoneNumber,country,city,address,DateOfBirth,photo_before,photo_after) values(?,?,?,?,?,?,?,?)");
//		        preparedStatement.setInt(1, account.getIdentityNumber());
//		        preparedStatement.setInt(2, account.getPhoneNumber());
//		        preparedStatement.setString(3, account.getCountry());
//		        preparedStatement.setString(4, account.getCity());
//		        preparedStatement.setString(5, account.getAddress());
//		        preparedStatement.setDate(6, Date.valueOf(account.getDateOfBirth())); 
//		        
//		        
//
//		        preparedStatement.setBytes(7, account.getPhoto_before()); 
//		        preparedStatement.setBytes(8, account.getPhoto_after()); 
//		        
//		        result = preparedStatement.executeUpdate() > 0;
//		    } catch (Exception e) {
//		        e.printStackTrace();
//		        result = false;
//		    } finally {
//		        ConnectDB.disconnect();
//		    }
//		    return result;
//		}
		public boolean topUpAccount(int accountId, BigDecimal topUpAmount) {
	        Connection conn = null;
	        PreparedStatement updateBalanceStmt = null;
	        PreparedStatement insertHistoryStmt = null;
	        PreparedStatement selectBalanceStmt = null;
	        ResultSet rs = null;
	        boolean result = false;

	        try {
	            conn = ConnectDB.connection();
	            conn.setAutoCommit(false); 

	            BigDecimal oldBalance = BigDecimal.ZERO;
	            String selectBalanceSql = "SELECT balance FROM account WHERE id = ?";
	            selectBalanceStmt = conn.prepareStatement(selectBalanceSql);
	            selectBalanceStmt.setInt(1, accountId);
	            rs = selectBalanceStmt.executeQuery();
	            if (rs.next()) {
	                oldBalance = rs.getBigDecimal("balance");
	            }

	            BigDecimal newBalance = oldBalance.add(topUpAmount);

	            String updateBalanceSql = "UPDATE account SET balance = ? WHERE id = ?";
	            updateBalanceStmt = conn.prepareStatement(updateBalanceSql);
	            updateBalanceStmt.setBigDecimal(1, newBalance);
	            updateBalanceStmt.setInt(2, accountId);
	            int balanceRowsUpdated = updateBalanceStmt.executeUpdate();

	            if (balanceRowsUpdated == 1) {
	                String insertHistorySql = "INSERT INTO topup_history (id, amount, created, oldBalance, newBalance) VALUES (?, ?, CURRENT_TIMESTAMP, ?, ?)";
	                insertHistoryStmt = conn.prepareStatement(insertHistorySql);
	                insertHistoryStmt.setInt(1, accountId);
	                insertHistoryStmt.setBigDecimal(2, topUpAmount);
	                insertHistoryStmt.setBigDecimal(3, oldBalance);
	                insertHistoryStmt.setBigDecimal(4, newBalance);
	                int historyRowsInserted = insertHistoryStmt.executeUpdate();

	                
	                if (historyRowsInserted == 1) {
	                    conn.commit();
	                    result = true;
	                } else {
	                    conn.rollback();
	                }
	            } else {
	                conn.rollback();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            if (conn != null) {
	                try {
	                    conn.rollback(); // Nếu có lỗi xảy ra, hủy bỏ giao dịch
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	            result = false;
	        } finally {
	            // Đóng các tài nguyên
	            try {
	                if (rs != null) rs.close();
	                if (selectBalanceStmt != null) selectBalanceStmt.close();
	                if (updateBalanceStmt != null) updateBalanceStmt.close();
	                if (insertHistoryStmt != null) insertHistoryStmt.close();
	                if (conn != null) {
	                    conn.setAutoCommit(true); // Khôi phục lại chế độ tự động commit
	                    conn.close();
	                }
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }

	        return result;
	    }

		
		public boolean updateAccountInfo(Account account) {
		    boolean result = false;
		    try {
		        PreparedStatement preparedStatement = ConnectDB.connection()
		            .prepareStatement("UPDATE account SET IdentityNumber=?, phoneNumber=?, country=?, city=?, address=?, DateOfBirth=?, photo_before=?, photo_after=?,status = ?,balance = ? WHERE id = ?");
		        preparedStatement.setInt(1, account.getIdentityNumber());
		        preparedStatement.setInt(2, account.getPhoneNumber());
		        preparedStatement.setString(3, account.getCountry());
		        preparedStatement.setString(4, account.getCity());
		        preparedStatement.setString(5, account.getAddress());
		        preparedStatement.setDate(6, java.sql.Date.valueOf(account.getDateOfBirth()));
		        preparedStatement.setBytes(7, account.getPhoto_before()); 
		        preparedStatement.setBytes(8, account.getPhoto_after());
		        preparedStatement.setInt(9, AccountStatus.PENDING.getValue());
		        preparedStatement.setBigDecimal(10, BigDecimal.ZERO);
		        preparedStatement.setInt(11, account.getId());

		        result = preparedStatement.executeUpdate() > 0;
		    } catch (Exception e) {
		        e.printStackTrace();
		        result = false;
		    } finally {
		        ConnectDB.disconnect();
		    }
		    return result;
		}
		



		public Account findByUsername(String username) {
		    Account account = null;
		    try {
		        PreparedStatement preparedStatement = ConnectDB.connection()
		            .prepareStatement("SELECT * FROM account WHERE username = ?");
		        preparedStatement.setString(1, username);
		        ResultSet resultSet = preparedStatement.executeQuery();
		        if (resultSet.next()) {
		            account = new Account();
		            account.setId(resultSet.getInt("id"));
		            account.setUsername(resultSet.getString("username"));
		            account.setPassword(resultSet.getString("password"));
		            account.setPhoto_after(resultSet.getBytes("photo_after"));
		            account.setPhoto_before(resultSet.getBytes("photo_before"));
		            account.setFullname(resultSet.getString("fullname"));
		            account.setBalance(resultSet.getBigDecimal("balance"));
		            account.setTerm(resultSet.getBoolean("term"));
		            account.setRole(resultSet.getString("role"));
		            account.setIsFirstLogin(resultSet.getBoolean("isFirstLogin"));
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        ConnectDB.disconnect();
		    }
		    return account;
		}
		
		public static Account findById(int id) {
			Account account = null;
			try {
				PreparedStatement preparedStatement = ConnectDB.connection()
						.prepareStatement("SELECT * FROM account WHERE id = ?");
				preparedStatement.setInt(1, id);
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					account = new Account();
					account.setId(resultSet.getInt("id"));
					account.setUsername(resultSet.getString("username"));
					account.setPassword(resultSet.getString("password"));
					account.setPhoto_after(resultSet.getBytes("photo_after"));
					account.setPhoto_before(resultSet.getBytes("photo_before"));
					account.setFullname(resultSet.getString("fullname"));
					 account.setBalance(resultSet.getBigDecimal("balance"));
					account.setTerm(resultSet.getBoolean("term"));
					account.setRole(resultSet.getString("role"));
					account.setIsFirstLogin(resultSet.getBoolean("isFirstLogin"));
					account.setFirstLoanType(resultSet.getString("firstLoanType"));
					 int statusValue = resultSet.getInt("status");
			            AccountStatus status = AccountStatus.fromValue(statusValue);
			            account.setStatus(status);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				ConnectDB.disconnect();
			}
			return account;
		}
		public Account findByEmail(String email) {
		    Account account = null;
		    try {
		        PreparedStatement preparedStatement = ConnectDB.connection()
		            .prepareStatement("SELECT * FROM account WHERE email = ?");
		        preparedStatement.setString(1, email);
		        ResultSet resultSet = preparedStatement.executeQuery();
		        if (resultSet.next()) {
		            account = new Account();
		            account.setId(resultSet.getInt("id"));
		            account.setUsername(resultSet.getString("username"));
		            account.setPassword(resultSet.getString("password"));
		            account.setPhoto_after(resultSet.getBytes("photo_after"));
		            account.setPhoto_before(resultSet.getBytes("photo_before"));
		            account.setFullname(resultSet.getString("fullname"));
		            account.setBalance(resultSet.getBigDecimal("balance"));
		            account.setTerm(resultSet.getBoolean("term"));
		            account.setRole(resultSet.getString("role"));
		            account.setIsFirstLogin(resultSet.getBoolean("isFirstLogin"));
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        ConnectDB.disconnect();
		    }
		    return account;
		}
		
		
		public boolean updatePassword(String username, String newPassword) {
		    boolean result = false;
		    try {
		        PreparedStatement preparedStatement = ConnectDB.connection()
		            .prepareStatement("UPDATE account SET password = ? WHERE username = ?");
		        preparedStatement.setString(1, newPassword);
		        preparedStatement.setString(2, username);
		        
		        result = preparedStatement.executeUpdate() > 0;
		    } catch (Exception e) {
		        e.printStackTrace();
		        result = false;
		    } finally {
		        ConnectDB.disconnect();
		    }
		    return result;
		}
		public static boolean updateFirstLogin(int accountId, boolean isFirstLogin) {
		    boolean result = false;
		    try {
		        PreparedStatement preparedStatement = ConnectDB.connection()
		            .prepareStatement("UPDATE account SET isFirstLogin = ? WHERE id = ?");
		        preparedStatement.setBoolean(1, isFirstLogin);
		        preparedStatement.setInt(2, accountId);

		        result = preparedStatement.executeUpdate() > 0;
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        ConnectDB.disconnect();
		    }
		    return result;
		}
		public static boolean updateFirstLoanType(int id, String loanType) {
			boolean result = false;
			try {
				PreparedStatement preparedStatement = ConnectDB.connection()
						.prepareStatement("UPDATE account SET firstLoanType = ? WHERE id = ?");
				preparedStatement.setString(1, loanType);
				preparedStatement.setInt(2, id);

				result = preparedStatement.executeUpdate() > 0;
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			} finally {
				ConnectDB.disconnect();
			}
			return result;
		}
		public boolean updateAccountBalance(int accountId, BigDecimal newBalance) {
		    boolean result = false;
		    Connection conn = null;
		    PreparedStatement updateBalanceStmt = null;

		    try {
		        conn = ConnectDB.connection();
		        String updateBalanceSql = "UPDATE account SET balance = ? WHERE id = ?";
		        updateBalanceStmt = conn.prepareStatement(updateBalanceSql);
		        updateBalanceStmt.setBigDecimal(1, newBalance);
		        updateBalanceStmt.setInt(2, accountId);

		        result = updateBalanceStmt.executeUpdate() > 0;
		    } catch (SQLException e) {
		        e.printStackTrace();
		        result = false;
		    } finally {
		        try {
		            if (updateBalanceStmt != null) updateBalanceStmt.close();
		            if (conn != null) conn.close();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		    return result;
		}

		
		
	
	
	}
