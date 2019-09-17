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
package com.abcbank.moneytransfer.utils;

import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



/**
 * The Class Utils.
 */
public class Utils {

    /** The properties. */
    private static Properties properties = new Properties();

    /** The log. */
    static Logger log = Logger.getLogger(Utils.class);

    /**
     * Load config.
     *
     * @param fileName the file name
     */
    public static void loadConfig(String fileName) {
        if (fileName == null) {
            log.warn("loadConfig: config file name cannot be null");
        } else {
            try {
                log.info("loadConfig(): Loading config file: " + fileName );
                final InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
                properties.load(fis);

            } catch (FileNotFoundException fne) {
                log.error("loadConfig(): file name not found " + fileName, fne);
            } catch (IOException ioe) {
                log.error("loadConfig(): error when reading the config " + fileName, ioe);
            }
        }

    }


    /**
     * Gets the string property.
     *
     * @param key the key
     * @return the string property
     */
    public static String getStringProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            value = System.getProperty(key);
        }
        return value;
    }

    /**
     * Gets the string property.
     *
     * @param key the key
     * @param defaultVal the default value if the key not present in config file
     * @return string property based on lookup key
     */
    public static String getStringProperty(String key, String defaultVal) {
        String value = getStringProperty(key);
        return value != null ? value : defaultVal;
    }


    /**
     * Gets the integer property.
     *
     * @param key the key
     * @param defaultVal the default val
     * @return the integer property
     */
    public static int getIntegerProperty(String key, int defaultVal) {
        String valueStr = getStringProperty(key);
        if (valueStr == null) {
            return defaultVal;
        } else {
            try {
                return Integer.parseInt(valueStr);

            } catch (Exception e) {
                log.warn("getIntegerProperty(): cannot parse integer from properties file for: " + key + "fail over to default value: " + defaultVal, e);
                return defaultVal;
            }
        }
    }

    //initialise

    static {
        String configFileName = System.getProperty("application.properties");

        if (configFileName == null) {
            configFileName = "application.properties";
        }
        loadConfig(configFileName);

    }


}
