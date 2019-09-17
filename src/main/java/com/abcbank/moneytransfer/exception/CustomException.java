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
package com.abcbank.moneytransfer.exception;


/**
 * The Class CustomException.
 */
public class CustomException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new custom exception.
	 *
	 * @param msg the msg
	 */
	public CustomException(String msg) {
		super(msg);
	}

	/**
	 * Instantiates a new custom exception.
	 *
	 * @param msg the msg
	 * @param cause the cause
	 */
	public CustomException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
