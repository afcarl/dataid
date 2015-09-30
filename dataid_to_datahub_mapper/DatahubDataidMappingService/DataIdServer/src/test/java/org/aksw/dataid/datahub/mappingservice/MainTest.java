
package org.aksw.dataid.datahub.mappingservice;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.header.MediaTypes;
import junit.framework.TestCase;

import org.aksw.dataid.rdfunit.TestUtils;
import org.glassfish.grizzly.http.server.HttpServer;


public class MainTest extends TestCase {

    private HttpServer httpServer;
    
    private WebResource r;

    public MainTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        TestUtils.init();
        
        //start the Grizzly2 web container 
        httpServer = Main.configureServer();
        httpServer.start();
        // create the client
        Client c = Client.create();
        r = c.resource(Main.Base_Uri);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        httpServer.stop();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    public void _testMyResource() {
        String responseMsg = r.path("myresource").get(String.class);
        assertEquals("Got it!", responseMsg);
    }

    /**
     * Test if a WADL document is available at the relative path
     * "application.wadl".
     */
    public void testApplicationWadl() {
        String serviceWadl = r.path("application.wadl").
                accept(MediaTypes.WADL).get(String.class);
                
        assertTrue(serviceWadl.length() > 0);
    }
}
