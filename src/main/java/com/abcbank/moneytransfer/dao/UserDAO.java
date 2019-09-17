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
import com.abcbank.moneytransfer.model.User;

import java.util.List;


/**
 * The Interface UserDAO.
 */
public interface UserDAO {
	
	/**
	 * Gets the all users.
	 *
	 * @return the all users
	 * @throws CustomException the custom exception
	 */
	List<User> getAllUsers() throws CustomException;

	/**
	 * Gets the user by id.
	 *
	 * @param userId the user id
	 * @return the user by id
	 * @throws CustomException the custom exception
	 */
	User getUserById(long userId) throws CustomException;

	/**
	 * Gets the user by name.
	 *
	 * @param userName the user name
	 * @return the user by name
	 * @throws CustomException the custom exception
	 */
	User getUserByName(String userName) throws CustomException;

	/**
	 * Insert user.
	 *
	 * @param user the user
	 * @return userId generated from insertion. return -1 on error
	 * @throws CustomException the custom exception
	 */
	long insertUser(User user) throws CustomException;

	/**
	 * Update user.
	 *
	 * @param userId the user id
	 * @param user the user
	 * @return the int
	 * @throws CustomException the custom exception
	 */
	int updateUser(Long userId, User user) throws CustomException;

	/**
	 * Delete user.
	 *
	 * @param userId the user id
	 * @return the int
	 * @throws CustomException the custom exception
	 */
	int deleteUser(long userId) throws CustomException;

}
