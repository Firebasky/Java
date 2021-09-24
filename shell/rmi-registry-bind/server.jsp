<%@page import="java.io.*,java.util.*,java.net.*,java.sql.*,java.text.*,java.beans.*,java.lang.*,java.rmi.*,java.rmi.server.*,java.rmi.registry.*"%>
<%
class ServerImp extends UnicastRemoteObject {
    protected ServerImp() throws RemoteException {
    }

}
ServerImp server = new ServerImp();
int port = 1099;
String registry_name = "rmi";
Registry registry = LocateRegistry.createRegistry(port);
registry.bind(registry_name, server);
System.out.println("Port:1099,Name:rmi,Service Start!n");
%>
