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
import com.abcbank.moneytransfer.model.Account;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static junit.framework.TestCase.assertTrue;


/**
 * The Class TestAccountDAO.
 */
public class TestAccountDAO {

	/** The Constant h2DaoFactory. */
	private static final DAOFactory h2DaoFactory = DAOFactory.getDAOFactory(DAOFactory.H2);

	/**
	 * Setup.
	 */
	@BeforeClass
	public static void setup() {
		// prepare test database and test data. Test data are initialised from
		// src/test/resources/demo.sql
		h2DaoFactory.populateTestData();
	}

	/**
	 * Tear down.
	 */
	@After
	public void tearDown() {

	}

	/**
	 * Test get all accounts.
	 *
	 * @throws CustomException the custom exception
	 */
	@Test
	public void testGetAllAccounts() throws CustomException {
		List<Account> allAccounts = h2DaoFactory.getAccountDAO().getAllAccounts();
		assertTrue(allAccounts.size() > 1);
	}

	/**
	 * Test get account by id.
	 *
	 * @throws CustomException the custom exception
	 */
	@Test
	public void testGetAccountById() throws CustomException {
		Account account = h2DaoFactory.getAccountDAO().getAccountById(1L);
		assertTrue(account.getUserName().equals("vikram"));
	}

	/**
	 * Test get non existing acc by id.
	 *
	 * @throws CustomException the custom exception
	 */
	@Test
	public void testGetNonExistingAccById() throws CustomException {
		Account account = h2DaoFactory.getAccountDAO().getAccountById(100L);
		assertTrue(account == null);
	}

	/**
	 * Test create account.
	 *
	 * @throws CustomException the custom exception
	 */
	@Test
	public void testCreateAccount() throws CustomException {
		BigDecimal balance = new BigDecimal(10).setScale(4, RoundingMode.HALF_EVEN);
		Account a = new Account("test2", balance, "CNY");
		long aid = h2DaoFactory.getAccountDAO().createAccount(a);
		Account afterCreation = h2DaoFactory.getAccountDAO().getAccountById(aid);
		assertTrue(afterCreation.getUserName().equals("test2"));
		assertTrue(afterCreation.getCurrencyCode().equals("CNY"));
		assertTrue(afterCreation.getBalance().equals(balance));
	}

	/**
	 * Test delete account.
	 *
	 * @throws CustomException the custom exception
	 */
	@Test
	public void testDeleteAccount() throws CustomException {
		int rowCount = h2DaoFactory.getAccountDAO().deleteAccountById(2L);
		// assert one row(user) deleted
		assertTrue(rowCount == 1);
		// assert user no longer there
		assertTrue(h2DaoFactory.getAccountDAO().getAccountById(2L) == null);
	}

	/**
	 * Test delete non existing account.
	 *
	 * @throws CustomException the custom exception
	 */
	@Test
	public void testDeleteNonExistingAccount() throws CustomException {
		int rowCount = h2DaoFactory.getAccountDAO().deleteAccountById(500L);
		// assert no row(user) deleted
		assertTrue(rowCount == 0);

	}

	/**
	 * Test update account balance sufficient fund.
	 *
	 * @throws CustomException the custom exception
	 */
	@Test
	public void testUpdateAccountBalanceSufficientFund() throws CustomException {

		BigDecimal deltaDeposit = new BigDecimal(50).setScale(4, RoundingMode.HALF_EVEN);
		BigDecimal afterDeposit = new BigDecimal(150).setScale(4, RoundingMode.HALF_EVEN);
		int rowsUpdated = h2DaoFactory.getAccountDAO().updateAccountBalance(1L, deltaDeposit);
		assertTrue(rowsUpdated == 1);
		assertTrue(h2DaoFactory.getAccountDAO().getAccountById(1L).getBalance().equals(afterDeposit));
		BigDecimal deltaWithDraw = new BigDecimal(-50).setScale(4, RoundingMode.HALF_EVEN);
		BigDecimal afterWithDraw = new BigDecimal(100).setScale(4, RoundingMode.HALF_EVEN);
		int rowsUpdatedW = h2DaoFactory.getAccountDAO().updateAccountBalance(1L, deltaWithDraw);
		assertTrue(rowsUpdatedW == 1);
		assertTrue(h2DaoFactory.getAccountDAO().getAccountById(1L).getBalance().equals(afterWithDraw));

	}

	/**
	 * Test update account balance not enough fund.
	 *
	 * @throws CustomException the custom exception
	 */
	@Test(expected = CustomException.class)
	public void testUpdateAccountBalanceNotEnoughFund() throws CustomException {
		BigDecimal deltaWithDraw = new BigDecimal(-50000).setScale(4, RoundingMode.HALF_EVEN);
		int rowsUpdatedW = h2DaoFactory.getAccountDAO().updateAccountBalance(1L, deltaWithDraw);
		assertTrue(rowsUpdatedW == 0);

	}

}