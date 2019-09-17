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

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.abcbank.moneytransfer.dao.DAOFactory;
import com.abcbank.moneytransfer.exception.CustomException;
import com.abcbank.moneytransfer.model.MoneyUtil;
import com.abcbank.moneytransfer.model.UserTransaction;


/**
 * The Class TransactionService.
 */
@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionService {

	/** The dao factory. */
	private final DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.H2);
	
	/**
	 * Transfer fund between two accounts.
	 *
	 * @param transaction the transaction
	 * @return the response
	 * @throws CustomException the custom exception
	 */
	@POST
	public Response transferFund(UserTransaction transaction) throws CustomException {

		String currency = transaction.getCurrencyCode();
		if (MoneyUtil.INSTANCE.validateCcyCode(currency)) {
			int updateCount = daoFactory.getAccountDAO().transferAccountBalance(transaction);
			if (updateCount == 2) {
				return Response.status(Response.Status.OK).build();
			} else {
				// transaction failed
				throw new WebApplicationException("Transaction failed", Response.Status.BAD_REQUEST);
			}

		} else {
			throw new WebApplicationException("Currency Code Invalid ", Response.Status.BAD_REQUEST);
		}

	}

}
