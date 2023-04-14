# ORB client sample program


This is a java client program that performs the EJB lookup. The command files are created on a Windows platform.  

## How to set up

- Download the files by either `git clone` command or using the download link.

![Download Link](https://sites.northwestern.edu/researchcomputing/files/2021/05/github.png)


## Source code

The client program [WsJaasTestClient.java](https://github.com/una-tapa/ws_jaas_test/blob/main/test/WsJaasTestClient.java) needs to be edited 
- WebSphere host
- WebSphere port
```
	    System.out.println("Try my machine localhost:2809");
		System.setProperty("com.ibm.CORBA.securityServerHost", "DESKTOP-BBBKTCU");
		System.setProperty("com.ibm.CORBA.securityServerPort", "2809");
```
- userid
- password
- url
- JndiName
```
	protected String getUser() {
		String user = "Admin"; 
		System.out.println("Returning user=" + user); 
		return user;
	}

	protected String getPassword() {
		String password = ""; 
		System.out.println("Returning password=" + password); 
		return password;
	}

	protected String getUrl() {
	    return "corbaloc::localhost:2809";
	}

	protected String getJndiName() {
		//Cannot lookup right now 
	    return "cell/nodes/DESKTOP-BBBKTCU/servers/server1/ejb/itsohello/securedhello";

	}
```

## How to compile

In the directory above `test` where `compile.cmd`. Before running the command, please adjust the path so that all the necessary jar files are accessible. 

compile.cmd
```
\WAS855nd\java\bin\javac.exe -classpath c:\WAS855nd\lib\j2ee.jar;C:\WAS855ND\runtimes\com.ibm.ws.orb_8.5.0.jar;c:\WAS855ND\plugins\com.ibm.ws.runtime.jar;.ItsohelloEJB.jar test\WsJaasTestClient.java
```

## How to run 

In the same directory we ran `compile.cmd`, please run `run.cmd`.
```
c:/WAS855ND/java/bin/java ^
-classpath .;c:/WAS855ND/runtimes/com.ibm.ws.ejb.thinclient_8.5.0.jar;c:c:/WAS855ND/runtimes/com.ibm.ws.orb_8.5.0.jar; ^
-Dcom.ibm.ws.orb.transport.alwaysResolveHostIP=true  ^
-Dcom.ibm.CORBA.ConfigURL="file:c:\WAS855ND\profiles\AppSrv01\properties\sas.client.props" ^
-Dcom.ibm.SSL.ConfigURL="file:c:\WAS855ND\profiles\AppSrv01\properties\ssl.client.props" ^
-DtraceSettingsFile=TraceSettings.properties ^
test.WsJaasTestClient 
```

## Sample output

### client trace
- [Client console output](https://github.com/una-tapa/ws_jaas_test/blob/main/traceoutput/client/console-output.log)
- [Client trace](https://github.com/una-tapa/ws_jaas_test/blob/main/traceoutput/client/MyTraceFile.log)
### server trace
- [server1 log directory](https://github.com/una-tapa/ws_jaas_test/tree/main/traceoutput/server1)


## TODO
- Make the calls multithreaded
- Update the program to take the parameters (or input file)

## Notes

An authentication happens below.
```
[4/14/23 17:52:34:894 EDT] 0000007e SecurityServe >  simple_authenticate Entry

                                 BasicAuthData: userid=websphereadmin,password=xxxxxxx,realm=<default>

[4/14/23 17:52:34:894 EDT] 0000007e DomainInfo    >  getAdminRealm Entry

                                 wasldap1.fyre.ibm.com:636

[4/14/23 17:52:34:894 EDT] 0000007e SecurityServe 3   Pushing resourceName: application

[4/14/23 17:52:34:894 EDT] 0000007e ContextManage >  getInstance Entry

[4/14/23 17:52:34:894 EDT] 0000007e ContextManage <  getInstance Exit

[4/14/23 17:52:34:894 EDT] 0000007e SecurityServe 3   simple_authenticate using local registry to authenticate.

...
[4/14/23 17:52:34:894 EDT] 0000007e ContextManage >  login(realm, user, password) -> login(<default>, websphereadmin, **********, system.RMI_INBOUND) Entry
....

[[4/14/23 17:52:34:894 EDT] 0000007e LdapRegistryI >  getUsers Entry

                                 websphereadmin

                                 2

[4/14/23 17:52:34:894 EDT] 0000007e LdapRegistryI >  search Entry

[4/14/23 17:52:34:894 EDT] 0000007e LdapRegistryI 3   DN: DC=WASSEC,DC=LOCAL

[4/14/23 17:52:34:894 EDT] 0000007e LdapRegistryI 3   Search scope: 2

[4/14/23 17:52:34:894 EDT] 0000007e LdapRegistryI 3   Filter: (&(sAMAccountName=websphereadmin)(objectcategory=user))

[4/14/23 17:52:34:894 EDT] 0000007e LdapRegistryI 3   Time limit: 3

[4/14/23 17:52:34:894 EDT] 0000007e LdapRegistryI 3   Attr[0]: 1.1

[4/14/23 17:52:34:894 EDT] 0000007e LdapRegistryI 3   Search 0 of 3

[4/14/23 17:52:34:894 EDT] 0000007e LdapRegistryI >  getDirContext Entry

[4/14/23 17:52:34:894 EDT] 0000007e LdapRegistryI <  getDirContext Exit

[4/14/23 17:52:34:894 EDT] 0000007e LdapRegistryI <  getDirContext Exit

[4/14/23 17:52:34:894 EDT] 0000007e LdapRegistryI 3   enterJNDI:ORB.thread.pool : 0

[4/14/23 17:52:34:925 EDT] 0000007e LdapRegistryI 3   exitJNDI:ORB.thread.pool : 0

[4/14/23 17:52:34:925 EDT] 0000007e LdapRegistryI 3   Time elapsed: 31

[4/14/23 17:52:34:925 EDT] 0000007e LdapRegistryI <  search Exit

[4/14/23 17:52:34:925 EDT] 0000007e LdapRegistryI 3   Number of users returned = 1

[4/14/23 17:52:34:925 EDT] 0000007e LdapRegistryI <  getUsers Exit

                                 websphereadmin

                                 2

[4/14/23 17:52:34:925 EDT] 0000007e LdapRegistryI 3   Found user

                                 CN=websphereadmin,CN=Users,DC=WASSEC,DC=LOCAL


``