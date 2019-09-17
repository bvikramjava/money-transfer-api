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


/**
 * A factory for creating DAO objects.
 */
public abstract class DAOFactory {

	/** The Constant H2. */
	public static final int H2 = 1;

	/**
	 * Gets the user DAO.
	 *
	 * @return the user DAO
	 */
	public abstract UserDAO getUserDAO();

	/**
	 * Gets the account DAO.
	 *
	 * @return the account DAO
	 */
	public abstract AccountDAO getAccountDAO();

	/**
	 * Populate test data.
	 */
	public abstract void populateTestData();

	/**
	 * Gets the DAO factory.
	 *
	 * @param factoryCode the factory code
	 * @return the DAO factory
	 */
	public static DAOFactory getDAOFactory(int factoryCode) {

		switch (factoryCode) {
		case H2:
			return new H2DAOFactory();
		default:
			// by default using H2 in memory database
			return new H2DAOFactory();
		}
	}
}
