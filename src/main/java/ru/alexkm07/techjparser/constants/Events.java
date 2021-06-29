package ru.alexkm07.techjparser.constants;

public interface Events {
     String eventNames = "DBMSSQL,Context,TLOCK,TTIMEOUT,EXCP,EXCPCNTX";

     String dbmssqlEvent = "DBMSSQL";
     String contextEvent = "Context";
     String tlockEvent = "TLOCK";
     String timeoutEvent = "TTIMEOUT";
     String excpEvent = "EXCP";
     String excpcntxEvent = "EXCPCNTX";
     String na  = "N/A";

     String dateTime = "dateTime";
     String process = "process";
     String processName = "p:processName";
     String clientID = "t:clientID";
     String applicationName = "t:applicationName";
     String computerName = "t:computerName";
     String connectID = "connectID";
     String sessionID = "SessionID";
     String usr = "Usr";
     String appID = "AppID";
     String trans = "Trans";
     String dbpid = "dbpid";
     String rows  = "Rows";
     String rowsAffected = "RowsAffected";
     String waitConnections = "WaitConnections";
     String regions = "Regions";
     String exception = "Exception";
     String descr = "Descr";
     String sql = "Sql";
     String context = "Context";
     String locks = "Locks";
     String duration = "duration";
     String clientComputerName = "ClientComputerName";
     String serverComputerName = "ServerComputerName";
     String userName  = "UserName";
     String connectString = "ConnectString";
     String eventName = "eventName";
     String exceptClientID = "ClientID";

}
