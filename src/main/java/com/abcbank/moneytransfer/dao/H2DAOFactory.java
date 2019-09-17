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
package com.abcbank.moneytransfer.dao;

import com.abcbank.moneytransfer.dao.impl.AccountDAOImpl;
import com.abcbank.moneytransfer.dao.impl.UserDAOImpl;
import com.abcbank.moneytransfer.utils.Utils;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * H2 DAO.
 */
public class H2DAOFactory extends DAOFactory {
	
	/** The Constant h2_driver. */
	private static final String h2_driver = Utils.getStringProperty("h2_driver");
	
	/** The Constant h2_connection_url. */
	private static final String h2_connection_url = Utils.getStringProperty("h2_connection_url");
	
	/** The Constant h2_user. */
	private static final String h2_user = Utils.getStringProperty("h2_user");
	
	/** The Constant h2_password. */
	private static final String h2_password = Utils.getStringProperty("h2_password");
	
	/** The log. */
	private static Logger log = Logger.getLogger(H2DAOFactory.class);

	/** The user DAO. */
	private final UserDAOImpl userDAO = new UserDAOImpl();
	
	/** The account DAO. */
	private final AccountDAOImpl accountDAO = new AccountDAOImpl();

	/**
	 * Instantiates a new h 2 DAO factory.
	 */
	H2DAOFactory() {
		// init: load driver
		DbUtils.loadDriver(h2_driver);
	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws SQLException the SQL exception
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(h2_connection_url, h2_user, h2_password);

	}

	/* (non-Javadoc)
	 * @see com.abcbank.moneytransfer.dao.DAOFactory#getUserDAO()
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}

	/* (non-Javadoc)
	 * @see com.abcbank.moneytransfer.dao.DAOFactory#getAccountDAO()
	 */
	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	/* (non-Javadoc)
	 * @see com.abcbank.moneytransfer.dao.DAOFactory#populateTestData()
	 */
	@Override
	public void populateTestData() {
		log.info("Populating Test User Table and data ..... ");
		Connection conn = null;
		try {
			conn = H2DAOFactory.getConnection();
			RunScript.execute(conn, new FileReader("src/test/resources/demo.sql"));
		} catch (SQLException e) {
			log.error("populateTestData(): Error populating user data: ", e);
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			log.error("populateTestData(): Error finding test script file ", e);
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}

}
