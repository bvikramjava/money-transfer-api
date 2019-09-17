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

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The Class Account.
 */
public class Account {

    /** The account id. */
    private long accountId;

    /** The user name. */
    @JsonProperty(required = true)
    private String userName;

    /** The balance. */
    @JsonProperty(required = true)
    private BigDecimal balance;

    /** The currency code. */
    @JsonProperty(required = true)
    private String currencyCode;

    /**
     * Instantiates a new account.
     */
    public Account() {
    }

    /**
     * Instantiates a new account.
     *
     * @param userName the user name
     * @param balance the balance
     * @param currencyCode the currency code
     */
    public Account(String userName, BigDecimal balance, String currencyCode) {
        this.userName = userName;
        this.balance = balance;
        this.currencyCode = currencyCode;
    }

    /**
     * Instantiates a new account.
     *
     * @param accountId the account id
     * @param userName the user name
     * @param balance the balance
     * @param currencyCode the currency code
     */
    public Account(long accountId, String userName, BigDecimal balance, String currencyCode) {
        this.accountId = accountId;
        this.userName = userName;
        this.balance = balance;
        this.currencyCode = currencyCode;
    }

    /**
     * Gets the account id.
     *
     * @return the account id
     */
    public long getAccountId() {
        return accountId;
    }

    /**
     * Gets the user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the balance.
     *
     * @return the balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Gets the currency code.
     *
     * @return the currency code
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (accountId != account.accountId) return false;
        if (!userName.equals(account.userName)) return false;
        if (!balance.equals(account.balance)) return false;
        return currencyCode.equals(account.currencyCode);

    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = (int) (accountId ^ (accountId >>> 32));
        result = 31 * result + userName.hashCode();
        result = 31 * result + balance.hashCode();
        result = 31 * result + currencyCode.hashCode();
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userName='" + userName + '\'' +
                ", balance=" + balance +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
