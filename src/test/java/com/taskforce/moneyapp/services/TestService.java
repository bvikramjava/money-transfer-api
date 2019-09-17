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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.abcbank.moneytransfer.dao.DAOFactory;
import com.abcbank.moneytransfer.service.AccountService;
import com.abcbank.moneytransfer.service.ServiceExceptionMapper;
import com.abcbank.moneytransfer.service.TransactionService;
import com.abcbank.moneytransfer.service.UserService;

import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.AfterClass;
import org.junit.BeforeClass;



/**
 * The Class TestService.
 */
public abstract class TestService {
    
    /** The server. */
    protected static Server server = null;
    
    /** The conn manager. */
    protected static PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();

    /** The client. */
    protected static HttpClient client ;
    
    /** The h 2 dao factory. */
    protected static DAOFactory h2DaoFactory =
            DAOFactory.getDAOFactory(DAOFactory.H2);
    
    /** The mapper. */
    protected ObjectMapper mapper = new ObjectMapper();
    
    /** The builder. */
    protected URIBuilder builder = new URIBuilder().setScheme("http").setHost("localhost:8084");


    /**
     * Setup.
     *
     * @throws Exception the exception
     */
    @BeforeClass
    public static void setup() throws Exception {
        h2DaoFactory.populateTestData();
        startServer();
        connManager.setDefaultMaxPerRoute(100);
        connManager.setMaxTotal(200);
        client= HttpClients.custom()
                .setConnectionManager(connManager)
                .setConnectionManagerShared(true)
                .build();

    }

    /**
     * Close client.
     *
     * @throws Exception the exception
     */
    @AfterClass
    public static void closeClient() throws Exception {
        //server.stop();
        HttpClientUtils.closeQuietly(client);
    }


    /**
     * Start server.
     *
     * @throws Exception the exception
     */
    private static void startServer() throws Exception {
        if (server == null) {
            server = new Server(8084);
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/");
            server.setHandler(context);
            ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/*");
            servletHolder.setInitParameter("jersey.config.server.provider.classnames",
                    UserService.class.getCanonicalName() + "," +
                            AccountService.class.getCanonicalName() + "," +
                            ServiceExceptionMapper.class.getCanonicalName() + "," +
                            TransactionService.class.getCanonicalName());
            server.start();
        }
    }
}
