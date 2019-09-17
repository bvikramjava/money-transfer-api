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
package com.abcbank.moneytransfer.service;

import com.abcbank.moneytransfer.dao.DAOFactory;
import com.abcbank.moneytransfer.exception.CustomException;
import com.abcbank.moneytransfer.model.Account;
import com.abcbank.moneytransfer.model.MoneyUtil;

import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


/**
 * Account Service.
 */
@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountService {
	
    /** The dao factory. */
    private final DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.H2);
    
    /** The log. */
    private static Logger log = Logger.getLogger(AccountService.class);

    
    /**
     * Find all accounts.
     *
     * @return the all accounts
     * @throws CustomException the custom exception
     */
    @GET
    @Path("/all")
    public List<Account> getAllAccounts() throws CustomException {
        return daoFactory.getAccountDAO().getAllAccounts();
    }

    /**
     * Find by account id.
     *
     * @param accountId the account id
     * @return the account
     * @throws CustomException the custom exception
     */
    @GET
    @Path("/{accountId}")
    public Account getAccount(@PathParam("accountId") long accountId) throws CustomException {
        return daoFactory.getAccountDAO().getAccountById(accountId);
    }
    
    /**
     * Find balance by account Id.
     *
     * @param accountId the account id
     * @return the balance
     * @throws CustomException the custom exception
     */
    @GET
    @Path("/{accountId}/balance")
    public BigDecimal getBalance(@PathParam("accountId") long accountId) throws CustomException {
        final Account account = daoFactory.getAccountDAO().getAccountById(accountId);

        if(account == null){
            throw new WebApplicationException("Account not found", Response.Status.NOT_FOUND);
        }
        return account.getBalance();
    }
    
    /**
     * Create Account.
     *
     * @param account the account
     * @return the account
     * @throws CustomException the custom exception
     */
    @PUT
    @Path("/create")
    public Account createAccount(Account account) throws CustomException {
        final long accountId = daoFactory.getAccountDAO().createAccount(account);
        return daoFactory.getAccountDAO().getAccountById(accountId);
    }

    /**
     * Deposit amount by account Id.
     *
     * @param accountId the account id
     * @param amount the amount
     * @return the account
     * @throws CustomException the custom exception
     */
    @PUT
    @Path("/{accountId}/deposit/{amount}")
    public Account deposit(@PathParam("accountId") long accountId,@PathParam("amount") BigDecimal amount) throws CustomException {

        if (amount.compareTo(MoneyUtil.zeroAmount) <=0){
            throw new WebApplicationException("Invalid Deposit amount", Response.Status.BAD_REQUEST);
        }

        daoFactory.getAccountDAO().updateAccountBalance(accountId,amount.setScale(4, RoundingMode.HALF_EVEN));
        return daoFactory.getAccountDAO().getAccountById(accountId);
    }

    /**
     * Withdraw amount by account Id.
     *
     * @param accountId the account id
     * @param amount the amount
     * @return the account
     * @throws CustomException the custom exception
     */
    @PUT
    @Path("/{accountId}/withdraw/{amount}")
    public Account withdraw(@PathParam("accountId") long accountId,@PathParam("amount") BigDecimal amount) throws CustomException {

        if (amount.compareTo(MoneyUtil.zeroAmount) <=0){
            throw new WebApplicationException("Invalid Deposit amount", Response.Status.BAD_REQUEST);
        }
        BigDecimal delta = amount.negate();
        if (log.isDebugEnabled())
            log.debug("Withdraw service: delta change to account  " + delta + " Account ID = " +accountId);
        daoFactory.getAccountDAO().updateAccountBalance(accountId,delta.setScale(4, RoundingMode.HALF_EVEN));
        return daoFactory.getAccountDAO().getAccountById(accountId);
    }


    /**
     * Delete amount by account Id.
     *
     * @param accountId the account id
     * @return the response
     * @throws CustomException the custom exception
     */
    @DELETE
    @Path("/{accountId}")
    public Response deleteAccount(@PathParam("accountId") long accountId) throws CustomException {
        int deleteCount = daoFactory.getAccountDAO().deleteAccountById(accountId);
        if (deleteCount == 1) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
