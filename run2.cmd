c:/WAS855ND/java/bin/java ^
-classpath .;c:/WAS855ND/runtimes/com.ibm.ws.ejb.thinclient_8.5.0.jar;c:c:/WAS855ND/runtimes/com.ibm.ws.orb_8.5.0.jar; ^
-Dcom.ibm.ws.orb.transport.alwaysResolveHostIP=true  ^
-Dcom.ibm.CORBA.ConfigURL="file:c:\WAS855ND\profiles\AppSrv01\properties\sas.client.props" ^
-Dcom.ibm.SSL.ConfigURL="file:c:\WAS855ND\profiles\AppSrv01\properties\ssl.client.props" ^
-DtraceSettingsFile=TraceSettings.properties ^
test.MultithreadRequestSender


