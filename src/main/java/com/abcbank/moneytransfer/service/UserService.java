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
import com.abcbank.moneytransfer.model.User;

import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;



/**
 * The Class UserService.
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {
 
	/** The dao factory. */
	private final DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.H2);
    
	/** The log. */
	private static Logger log = Logger.getLogger(UserService.class);

	/**
	 * Find by userName.
	 *
	 * @param userName the user name
	 * @return the user by name
	 * @throws CustomException the custom exception
	 */
    @GET
    @Path("/{userName}")
    public User getUserByName(@PathParam("userName") String userName) throws CustomException {
        if (log.isDebugEnabled())
            log.debug("Request Received for get User by Name " + userName);
        final User user = daoFactory.getUserDAO().getUserByName(userName);
        if (user == null) {
            throw new WebApplicationException("User Not Found", Response.Status.NOT_FOUND);
        }
        return user;
    }
    
    /**
     * Find by all.
     *
     * @return the all users
     * @throws CustomException the custom exception
     */
    @GET
    @Path("/all")
    public List<User> getAllUsers() throws CustomException {
        return daoFactory.getUserDAO().getAllUsers();
    }
    
    /**
     * Create User.
     *
     * @param user the user
     * @return the user
     * @throws CustomException the custom exception
     */
    @POST
    @Path("/create")
    public User createUser(User user) throws CustomException {
        if (daoFactory.getUserDAO().getUserByName(user.getUserName()) != null) {
            throw new WebApplicationException("User name already exist", Response.Status.BAD_REQUEST);
        }
        final long uId = daoFactory.getUserDAO().insertUser(user);
        return daoFactory.getUserDAO().getUserById(uId);
    }
    
    /**
     * Find by User Id.
     *
     * @param userId the user id
     * @param user the user
     * @return the response
     * @throws CustomException the custom exception
     */
    @PUT
    @Path("/{userId}")
    public Response updateUser(@PathParam("userId") long userId, User user) throws CustomException {
        final int updateCount = daoFactory.getUserDAO().updateUser(userId, user);
        if (updateCount == 1) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    /**
     * Delete by User Id.
     *
     * @param userId the user id
     * @return the response
     * @throws CustomException the custom exception
     */
    @DELETE
    @Path("/{userId}")
    public Response deleteUser(@PathParam("userId") long userId) throws CustomException {
        int deleteCount = daoFactory.getUserDAO().deleteUser(userId);
        if (deleteCount == 1) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


}
