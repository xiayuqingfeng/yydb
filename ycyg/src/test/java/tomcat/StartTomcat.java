package tomcat;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

/**
 * The Class StartMainTomcat.
 * 
 * @author nibili
 */
public class StartTomcat {

	/** The Constant PORT. */
	public static final int PORT = 80;

	/** The Constant CONTEXT. */
	public static final String CONTEXT = "";

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {
		System.setProperty("catalina.base", System.getProperty("user.dir") + "/target");
		System.setProperty("log.sql.port", "80");
		Tomcat server = new Tomcat();
		server.setBaseDir(System.getProperty("catalina.base"));
		server.setPort(PORT);
		server.addWebapp(CONTEXT, System.getProperty("user.dir") + "/src/main/webapp");
		Connector connector = server.getConnector();
		connector.setURIEncoding("UTF-8");
		server.start();
		System.out.println("Hit Enter in console to stop server");
		if (System.in.read() != 0) {
			server.stop();
			System.out.println("Server stopped");
		}
	}
}
