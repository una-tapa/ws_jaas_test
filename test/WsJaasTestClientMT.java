package test;

import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
//import com.ibm.itsohello.bean.SecuredHello;
//import com.ibm.itsohello.bean.SecuredHelloHome;
import javax.rmi.PortableRemoteObject;

import com.ibm.websphere.security.auth.callback.WSCallbackHandlerImpl;
import java.time.LocalDateTime;

public class WsJaasTestClientMT implements PrivilegedExceptionAction<Object> {
	static {
	    System.setProperty("java.security.auth.login.config", "./jaas.config");
	    System.setProperty("com.ibm.CORBA.securityEnabled", "true");
	}

	public void sendRequest() throws Exception {

	    System.out.println("Try my machine localhost:2809");
		System.setProperty("com.ibm.CORBA.securityServerHost", "DESKTOP-BBBKTCU");
		System.setProperty("com.ibm.CORBA.securityServerPort", "2809");

		Object ejbHome = null; 

		LocalDateTime currentTime = LocalDateTime.now();
		System.out.println(currentTime);
		for (int i=0; i < 3; i++) {   //Tried 100 times, no luck
		    try {
			System.out.println("Trying " + i + " times"); 
			//ejbHome = new WsJaasTestClient().doWork();
			ejbHome = doWork();
			System.out.println("ejbHome=" + ejbHome);
		    }
		    catch (Exception e) {
			System.out.println("Ignoring the Exception"); 
		    }
		}
		currentTime = LocalDateTime.now();
		System.out.println(currentTime);

	}

	protected String getUser() {
		System.out.println("user=websphereadmin");
		return "websphereadmin";
	}

	protected String getPassword() {
		return "";
	}

	protected String getUrl() {
	    return "corbaloc::localhost:2809";
	}

	protected String getJndiName() {
		//Cannot lookup right now 
	    return "cell/nodes/DESKTOP-BBBKTCU/servers/server1/ejb/itsohello/securedhello";

	}

	protected final Object doWork() throws Exception {
	    System.out.println("Getting LoginContext");

		LoginContext loginCtx = getNewLoginContext();
		Object output = null;
		System.out.println("Calling getSubject"); 
		Subject subject = loginCtx.getSubject();
		System.out.println("Subject="+ subject);
		try {
		        System.out.println("Calling Subject.doAs()");
			output = (Object) Subject.doAs(subject, this);
		} catch (PrivilegedActionException pae) {
			Throwable t = pae.getCause();
			if (t instanceof Exception) {
				throw (Exception) t;
			} else {
				throw new Exception(t);
			}
		} finally {
			try {
				loginCtx.logout();
			} catch (Exception e) {
			    e.printStackTrace();
			}
		}
		return output;
	}

	protected LoginContext getNewLoginContext() throws LoginException {
		LoginContext loginCtx = new LoginContext("WebSphereJaasBasicLogin", new WSCallbackHandlerImpl(getUser(), getPassword()));
		loginCtx.login();
		return loginCtx;
	}

	public Object run() throws Exception {
	    InitialContext ctx = getNewInitialContext(getUrl());
	    return ctx.lookup(getJndiName());
	}

	public static InitialContext getNewInitialContext(String providerUrl) throws NamingException {


      	System.out.println("Entering getNewInitialContext providerUrl=" + providerUrl);
		Hashtable<String, Object> ht = new Hashtable<String, Object>();
		ht.put(Context.INITIAL_CONTEXT_FACTORY, "com.ibm.websphere.naming.WsnInitialContextFactory");
		ht.put(Context.PROVIDER_URL, providerUrl);
		ht.put("org.omg.CORBA.ORBClass", "com.ibm.CORBA.iiop.ORB"); // Used for WS 7.0
		ht.put("org.omg.CORBA.ORBSingletonClass", "com.ibm.rmi.corba.ORBSingleton"); // Used for WS 7.0

		// Turns off JNDI caching.
		//ht.put("com.ibm.websphere.naming.jndicache.cacheobject", "none");
		//ht.put("com.ibm.websphere.naming.hostname.normalizer", "...none...");
		return new InitialContext(ht);

	}
}
