package eu.geekplace.xmpp.testclient;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.util.TLSUtils;

public class TCPTest extends SmackTest<XMPPTCPConnection> {
	
	static {
		SmackConfiguration.setDefaultPacketReplyTimeout(1000*60*5);
		SmackConfiguration.DEBUG_ENABLED = true;
	}

	public static void main(String args[]) throws Exception {
		SmackTest<XMPPTCPConnection> test = new TCPTest();
		test.runTest();
	}

	@Override
	protected void runTestSubclass() throws SmackException, IOException,
			XMPPException, InterruptedException, KeyManagementException, NoSuchAlgorithmException {
		System.out.println("Using Smack Version: " + SmackConfiguration.getVersion());
		ConnectionConfiguration conf = new ConnectionConfiguration(SERV);
		conf.setSecurityMode(SecurityMode.disabled);
//		conf.setLegacySessionDisabled(true);
		conf.setCompressionEnabled(true);
		TLSUtils.acceptAllCertificates(conf);
		connection = new XMPPTCPConnection(conf);

		connection.connect();

		connection.login(USER, PASS);

		send( "Hi, what's up?");

		connection.disconnect();
		System.out.println("Disconnected without Exception");
	}
}