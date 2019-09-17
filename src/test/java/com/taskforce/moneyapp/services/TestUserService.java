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
package com.taskforce.moneyapp.services;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.abcbank.moneytransfer.model.User;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertTrue;



/**
 * Integration testing for RestAPI
 * Test data are initialised from src/test/resources/demo.sql
 * INSERT INTO User (UserName, EmailAddress) VALUES ('test2','test2@gmail.com');  --ID=1
   INSERT INTO User (UserName, EmailAddress) VALUES ('test1','test1@gmail.com');  --ID=2
 */
public class TestUserService extends TestService {

    /**
     * Test get user.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws URISyntaxException the URI syntax exception
     */
    /*
       TC D1 Positive Category = UserService
       Scenario: test get user by given user name
                 return 200 OK
    */
    @Test
    public void testGetUser() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/user/vikram").build();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();

        assertTrue(statusCode == 200);
        //check the content
        String jsonString = EntityUtils.toString(response.getEntity());
        User user = mapper.readValue(jsonString, User.class);
        assertTrue(user.getUserName().equals("vikram"));
        assertTrue(user.getEmailAddress().equals("vikram@gmail.com"));

    }

     /**
      * Test get all users.
      *
      * @throws IOException Signals that an I/O exception has occurred.
      * @throws URISyntaxException the URI syntax exception
      */
     /*
     TC D2 Positive Category = UserService
     Scenario: test get all users
               return 200 OK
      */
    @Test
    public void testGetAllUsers() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/user/all").build();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
        //check the content
        String jsonString = EntityUtils.toString(response.getEntity());
        User[] users = mapper.readValue(jsonString, User[].class);
        assertTrue(users.length > 0);
    }

    /**
     * Test create user.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws URISyntaxException the URI syntax exception
     */
    /*
        TC D3 Positive Category = UserService
        Scenario: Create user using JSON
                  return 200 OK
     */
    @Test
    public void testCreateUser() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/user/create").build();
        User user = new User("liandre", "liandre@gmail.com");
        String jsonInString = mapper.writeValueAsString(user);
        StringEntity entity = new StringEntity(jsonInString);
        HttpPost request = new HttpPost(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
        String jsonString = EntityUtils.toString(response.getEntity());
        User uAfterCreation = mapper.readValue(jsonString, User.class);
        assertTrue(uAfterCreation.getUserName().equals("liandre"));
        assertTrue(uAfterCreation.getEmailAddress().equals("liandre@gmail.com"));
    }

    /**
     * Test create existing user.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws URISyntaxException the URI syntax exception
     */
    /*
        TC D4 Negative Category = UserService
        Scenario: Create user already existed using JSON
                  return 400 BAD REQUEST
    */
    @Test
    public void testCreateExistingUser() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/user/create").build();
        User user = new User("test1", "test1@gmail.com");
        String jsonInString = mapper.writeValueAsString(user);
        StringEntity entity = new StringEntity(jsonInString);
        HttpPost request = new HttpPost(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 400);

    }

    /**
     * Test update user.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws URISyntaxException the URI syntax exception
     */
    /*
     TC D5 Positive Category = UserService
     Scenario: Update Existing User using JSON provided from client
               return 200 OK
     */
    @Test
    public void testUpdateUser() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/user/2").build();
        User user = new User(2L, "test1", "test1123@gmail.com");
        String jsonInString = mapper.writeValueAsString(user);
        StringEntity entity = new StringEntity(jsonInString);
        HttpPut request = new HttpPut(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
    }


    /**
     * Test update non existing user.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws URISyntaxException the URI syntax exception
     */
    /*
    TC D6 Negative Category = UserService
    Scenario: Update non existed User using JSON provided from client
              return 404 NOT FOUND
    */
    @Test
    public void testUpdateNonExistingUser() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/user/100").build();
        User user = new User(2L, "test1", "test1123@gmail.com");
        String jsonInString = mapper.writeValueAsString(user);
        StringEntity entity = new StringEntity(jsonInString);
        HttpPut request = new HttpPut(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 404);
    }

    /**
     * Test delete user.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws URISyntaxException the URI syntax exception
     */
    /*
     TC D7 Positive Category = UserService
     Scenario: test delete user
                return 200 OK
    */
    @Test
    public void testDeleteUser() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/user/3").build();
        HttpDelete request = new HttpDelete(uri);
        request.setHeader("Content-type", "application/json");
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
    }


    /**
     * Test delete non existing user.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws URISyntaxException the URI syntax exception
     */
    /*
    TC D8 Negative Category = UserService
    Scenario: test delete non-existed user
              return 404 NOT FOUND
   */
    @Test
    public void testDeleteNonExistingUser() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/user/300").build();
        HttpDelete request = new HttpDelete(uri);
        request.setHeader("Content-type", "application/json");
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 404);
    }


}
