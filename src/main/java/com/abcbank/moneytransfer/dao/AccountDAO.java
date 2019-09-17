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

import com.abcbank.moneytransfer.exception.CustomException;
import com.abcbank.moneytransfer.model.Account;
import com.abcbank.moneytransfer.model.UserTransaction;

import java.math.BigDecimal;
import java.util.List;



/**
 * The Interface AccountDAO.
 */
public interface AccountDAO {

    /**
     * Gets the all accounts.
     *
     * @return the all accounts
     * @throws CustomException the custom exception
     */
    List<Account> getAllAccounts() throws CustomException;
    
    /**
     * Gets the account by id.
     *
     * @param accountId the account id
     * @return the account by id
     * @throws CustomException the custom exception
     */
    Account getAccountById(long accountId) throws CustomException;
    
    /**
     * Creates the account.
     *
     * @param account the account
     * @return the long
     * @throws CustomException the custom exception
     */
    long createAccount(Account account) throws CustomException;
    
    /**
     * Delete account by id.
     *
     * @param accountId the account id
     * @return the int
     * @throws CustomException the custom exception
     */
    int deleteAccountById(long accountId) throws CustomException;

    /**
     * Update account balance.
     *
     * @param accountId user accountId
     * @param deltaAmount amount to be debit(less than 0)/credit(greater than 0).
     * @return no. of rows updated
     * @throws CustomException the custom exception
     */
    int updateAccountBalance(long accountId, BigDecimal deltaAmount) throws CustomException;
    
    /**
     * Transfer account balance.
     *
     * @param userTransaction the user transaction
     * @return the int
     * @throws CustomException the custom exception
     */
    int transferAccountBalance(UserTransaction userTransaction) throws CustomException;
}
