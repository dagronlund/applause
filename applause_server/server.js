var http = require("http")
var ws = require("../../")
var fs = require("fs")
var mssql = require("mssql")
var chat = require('./chat');

var config = {
  server   : 'njexeou0hw.database.windows.net',
  user     : 'applauded',
  password : 'Aardvark01',
  database : 'ApplauseM'

  options  : {
    encrypt: true
  }

/*
var db_connection = new sql.Connection(config, function(err){

  db_connection.connect();
  var request = new sql.Request(db_connection);
  request.query('SELECT ConnInd FROM Connections AS partner WHERE UsrID NOT LIKE \'' + ConnectionID + '\';', function(err, recordset) {
  var partner = recordset[0].partner; 
  });
}
*/

var server = ws.createServer(function(connection) {
	connection.username = null
	connection.state = 0
	connection.partner = null
	connection.on("text", function (str) {
		broadcast(str);
/*		if(connection.username === null) {
		connection.username = str
		} else {
		switch(str.charAt(0)){
			case 'a':
				switch(str.charAt(1)){
					case '0':
						break;

					case '1':
						break;

					case '2':
						break;

				}

				break;

			case 'b':
				switch(str.charAt(1)){
					case '0':
						break;

				}

				break;

			case 'c':
				if(connection.state !=1) {
					connection.sendText('Error - invalid state')
					break;
				} else {
					server.connections[howdoigetthisnumber].sendText(str.substring(1,str.length - 1))
				}
				break;


		}
*/						          
})
}
server.listen(8080)
