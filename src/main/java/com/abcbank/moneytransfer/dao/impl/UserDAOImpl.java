/*
 * (C) Copyright 2019 Vikram Boyapati.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */ 
package com.abcbank.moneytransfer.dao.impl;

import com.abcbank.moneytransfer.dao.H2DAOFactory;
import com.abcbank.moneytransfer.dao.UserDAO;
import com.abcbank.moneytransfer.exception.CustomException;
import com.abcbank.moneytransfer.model.User;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



/**
 * The Class UserDAOImpl.
 */
public class UserDAOImpl implements UserDAO {
	
    /** The log. */
    private static Logger log = Logger.getLogger(UserDAOImpl.class);
    
    /** The Constant SQL_GET_USER_BY_ID. */
    private final static String SQL_GET_USER_BY_ID = "SELECT * FROM User WHERE UserId = ? ";
    
    /** The Constant SQL_GET_ALL_USERS. */
    private final static String SQL_GET_ALL_USERS = "SELECT * FROM User";
    
    /** The Constant SQL_GET_USER_BY_NAME. */
    private final static String SQL_GET_USER_BY_NAME = "SELECT * FROM User WHERE UserName = ? ";
    
    /** The Constant SQL_INSERT_USER. */
    private final static String SQL_INSERT_USER = "INSERT INTO User (UserName, EmailAddress) VALUES (?, ?)";
    
    /** The Constant SQL_UPDATE_USER. */
    private final static String SQL_UPDATE_USER = "UPDATE User SET UserName = ?, EmailAddress = ? WHERE UserId = ? ";
    
    /** The Constant SQL_DELETE_USER_BY_ID. */
    private final static String SQL_DELETE_USER_BY_ID = "DELETE FROM User WHERE UserId = ? ";
    
    /**
     * Find all users.
     *
     * @return the all users
     * @throws CustomException the custom exception
     */
    public List<User> getAllUsers() throws CustomException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<User>();
        try {
            conn = H2DAOFactory.getConnection();
            stmt = conn.prepareStatement(SQL_GET_ALL_USERS);
            rs = stmt.executeQuery();
            while (rs.next()) {
                User u = new User(rs.getLong("UserId"), rs.getString("UserName"), rs.getString("EmailAddress"));
                users.add(u);
                if (log.isDebugEnabled())
                    log.debug("getAllUsers() Retrieve User: " + u);
            }
            return users;
        } catch (SQLException e) {
            throw new CustomException("Error reading user data", e);
        } finally {
            DbUtils.closeQuietly(conn, stmt, rs);
        }
    }
    
    /**
     * Find user by userId.
     *
     * @param userId the user id
     * @return the user by id
     * @throws CustomException the custom exception
     */
    public User getUserById(long userId) throws CustomException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User u = null;
        try {
            conn = H2DAOFactory.getConnection();
            stmt = conn.prepareStatement(SQL_GET_USER_BY_ID);
            stmt.setLong(1, userId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                u = new User(rs.getLong("UserId"), rs.getString("UserName"), rs.getString("EmailAddress"));
                if (log.isDebugEnabled())
                    log.debug("getUserById(): Retrieve User: " + u);
            }
            return u;
        } catch (SQLException e) {
            throw new CustomException("Error reading user data", e);
        } finally {
            DbUtils.closeQuietly(conn, stmt, rs);
        }
    }
    
    /**
     * Find user by userName.
     *
     * @param userName the user name
     * @return the user by name
     * @throws CustomException the custom exception
     */
    public User getUserByName(String userName) throws CustomException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User u = null;
        try {
            conn = H2DAOFactory.getConnection();
            stmt = conn.prepareStatement(SQL_GET_USER_BY_NAME);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next()) {
                u = new User(rs.getLong("UserId"), rs.getString("UserName"), rs.getString("EmailAddress"));
                if (log.isDebugEnabled())
                    log.debug("Retrieve User: " + u);
            }
            return u;
        } catch (SQLException e) {
            throw new CustomException("Error reading user data", e);
        } finally {
            DbUtils.closeQuietly(conn, stmt, rs);
        }
    }
    
    /**
     * Save User.
     *
     * @param user the user
     * @return the long
     * @throws CustomException the custom exception
     */
    public long insertUser(User user) throws CustomException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        try {
            conn = H2DAOFactory.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getEmailAddress());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                log.error("insertUser(): Creating user failed, no rows affected." + user);
                throw new CustomException("Users Cannot be created");
            }
            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                log.error("insertUser():  Creating user failed, no ID obtained." + user);
                throw new CustomException("Users Cannot be created");
            }
        } catch (SQLException e) {
            log.error("Error Inserting User :" + user);
            throw new CustomException("Error creating user data", e);
        } finally {
            DbUtils.closeQuietly(conn,stmt,generatedKeys);
        }

    }
    
    /**
     * Update User.
     *
     * @param userId the user id
     * @param user the user
     * @return the int
     * @throws CustomException the custom exception
     */
    public int updateUser(Long userId,User user) throws CustomException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = H2DAOFactory.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE_USER);
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getEmailAddress());
            stmt.setLong(3, userId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            log.error("Error Updating User :" + user);
            throw new CustomException("Error update user data", e);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(stmt);
        }
    }
    
    /**
     * Delete User.
     *
     * @param userId the user id
     * @return the int
     * @throws CustomException the custom exception
     */
    public int deleteUser(long userId) throws CustomException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = H2DAOFactory.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE_USER_BY_ID);
            stmt.setLong(1, userId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            log.error("Error Deleting User :" + userId);
            throw new CustomException("Error Deleting User ID:"+ userId, e);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(stmt);
        }
    }

}
