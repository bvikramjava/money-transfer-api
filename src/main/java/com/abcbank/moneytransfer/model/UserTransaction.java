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
package com.abcbank.moneytransfer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;


/**
 * The Class UserTransaction.
 */
public class UserTransaction {

	/** The currency code. */
	@JsonProperty(required = true)
	private String currencyCode;

	/** The amount. */
	@JsonProperty(required = true)
	private BigDecimal amount;

	/** The from account id. */
	@JsonProperty(required = true)
	private Long fromAccountId;

	/** The to account id. */
	@JsonProperty(required = true)
	private Long toAccountId;

	/**
	 * Instantiates a new user transaction.
	 */
	public UserTransaction() {
	}

	/**
	 * Instantiates a new user transaction.
	 *
	 * @param currencyCode the currency code
	 * @param amount the amount
	 * @param fromAccountId the from account id
	 * @param toAccountId the to account id
	 */
	public UserTransaction(String currencyCode, BigDecimal amount, Long fromAccountId, Long toAccountId) {
		this.currencyCode = currencyCode;
		this.amount = amount;
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
	}

	/**
	 * Gets the currency code.
	 *
	 * @return the currency code
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Gets the from account id.
	 *
	 * @return the from account id
	 */
	public Long getFromAccountId() {
		return fromAccountId;
	}

	/**
	 * Gets the to account id.
	 *
	 * @return the to account id
	 */
	public Long getToAccountId() {
		return toAccountId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserTransaction that = (UserTransaction) o;

		if (!currencyCode.equals(that.currencyCode))
			return false;
		if (!amount.equals(that.amount))
			return false;
		if (!fromAccountId.equals(that.fromAccountId))
			return false;
		return toAccountId.equals(that.toAccountId);

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = currencyCode.hashCode();
		result = 31 * result + amount.hashCode();
		result = 31 * result + fromAccountId.hashCode();
		result = 31 * result + toAccountId.hashCode();
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserTransaction{" + "currencyCode='" + currencyCode + '\'' + ", amount=" + amount + ", fromAccountId="
				+ fromAccountId + ", toAccountId=" + toAccountId + '}';
	}

}
