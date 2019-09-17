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
import java.math.RoundingMode;
import java.util.Currency;

import org.apache.log4j.Logger;


/**
 * Utilities class to operate on money.
 */
public enum MoneyUtil {
	
    /** The instance. */
    INSTANCE;

    /** The log. */
    static Logger log = Logger.getLogger(MoneyUtil.class);

    /** The Constant zeroAmount. */
    //zero amount with scale 4 and financial rounding mode
    public static final BigDecimal zeroAmount = new BigDecimal(0).setScale(4, RoundingMode.HALF_EVEN);


    /**
     * Validate ccy code.
     *
     * @param inputCcyCode String Currency code to be validated
     * @return true if currency code is valid ISO code, false otherwise
     */
    public boolean validateCcyCode(String inputCcyCode) {
        try {
            Currency instance = Currency.getInstance(inputCcyCode);
            if(log.isDebugEnabled()){
                log.debug("Validate Currency Code: " + instance.getSymbol());
            }
            return instance.getCurrencyCode().equals(inputCcyCode);
        } catch (Exception e) {
            log.warn("Cannot parse the input Currency Code, Validation Failed: ", e);
        }
        return false;
    }

}
