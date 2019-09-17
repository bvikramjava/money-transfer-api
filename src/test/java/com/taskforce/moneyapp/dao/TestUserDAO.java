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
package com.taskforce.moneyapp.dao;

import com.abcbank.moneytransfer.dao.DAOFactory;
import com.abcbank.moneytransfer.exception.CustomException;
import com.abcbank.moneytransfer.model.User;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertTrue;


/**
 * The Class TestUserDAO.
 */
public class TestUserDAO {
	
	/** The log. */
	private static Logger log = Logger.getLogger(TestUserDAO.class);
	
	/** The Constant h2DaoFactory. */
	private static final DAOFactory h2DaoFactory = DAOFactory.getDAOFactory(DAOFactory.H2);

	/**
	 * Setup.
	 */
	@BeforeClass
	public static void setup() {
		// prepare test database and test data by executing sql script demo.sql
		log.debug("setting up test database and sample data....");
		h2DaoFactory.populateTestData();
	}

	/**
	 * Tear down.
	 */
	@After
	public void tearDown() {

	}

	/**
	 * Test get all users.
	 *
	 * @throws CustomException the custom exception
	 */
	@Test
	public void testGetAllUsers() throws CustomException {
		List<User> allUsers = h2DaoFactory.getUserDAO().getAllUsers();
		assertTrue(allUsers.size() > 1);
	}

	/**
	 * Test get user by id.
	 *
	 * @throws CustomException the custom exception
	 */
	@Test
	public void testGetUserById() throws CustomException {
		User u = h2DaoFactory.getUserDAO().getUserById(2L);
		assertTrue(u.getUserName().equals("kiran"));
	}

	/**
	 * Test get non existing user by id.
	 *
	 * @throws CustomException the custom exception
	 */
	@Test
	public void testGetNonExistingUserById() throws CustomException {
		User u = h2DaoFactory.getUserDAO().getUserById(500L);
		assertTrue(u == null);
	}

	/**
	 * Test get non existing user by name.
	 *
	 * @throws CustomException the custom exception
	 */
	@Test
	public void testGetNonExistingUserByName() throws CustomException {
		User u = h2DaoFactory.getUserDAO().getUserByName("abcdeftg");
		assertTrue(u == null);
	}

	/**
	 * Test create user.
	 *
	 * @throws CustomException the custom exception
	 */
	@Test
	public void testCreateUser() throws CustomException {
		User u = new User("liandre", "liandre@gmail.com");
		long id = h2DaoFactory.getUserDAO().insertUser(u);
		User uAfterInsert = h2DaoFactory.getUserDAO().getUserById(id);
		assertTrue(uAfterInsert.getUserName().equals("liandre"));
		assertTrue(u.getEmailAddress().equals("liandre@gmail.com"));
	}

	/**
	 * Test update user.
	 *
	 * @throws CustomException the custom exception
	 */
	@Test
	public void testUpdateUser() throws CustomException {
		User u = new User(1L, "test2", "test2@gmail.com");
		int rowCount = h2DaoFactory.getUserDAO().updateUser(1L, u);
		// assert one row(user) updated
		assertTrue(rowCount == 1);
		assertTrue(h2DaoFactory.getUserDAO().getUserById(1L).getEmailAddress().equals("test2@gmail.com"));
	}

	/**
	 * Test update non existing user.
	 *
	 * @throws CustomException the custom exception
	 */
	@Test
	public void testUpdateNonExistingUser() throws CustomException {
		User u = new User(500L, "test2", "test2@gmail.com");
		int rowCount = h2DaoFactory.getUserDAO().updateUser(500L, u);
		// assert one row(user) updated
		assertTrue(rowCount == 0);
	}

	/**
	 * Test delete user.
	 *
	 * @throws CustomException the custom exception
	 */
	@Test
	public void testDeleteUser() throws CustomException {
		int rowCount = h2DaoFactory.getUserDAO().deleteUser(1L);
		// assert one row(user) deleted
		assertTrue(rowCount == 1);
		// assert user no longer there
		assertTrue(h2DaoFactory.getUserDAO().getUserById(1L) == null);
	}

	/**
	 * Test delete non existing user.
	 *
	 * @throws CustomException the custom exception
	 */
	@Test
	public void testDeleteNonExistingUser() throws CustomException {
		int rowCount = h2DaoFactory.getUserDAO().deleteUser(500L);
		// assert no row(user) deleted
		assertTrue(rowCount == 0);

	}

}
