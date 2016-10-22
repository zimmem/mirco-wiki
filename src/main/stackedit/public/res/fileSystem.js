define([
    "underscore",
    "utils",
    "classes/FileDescriptor",
    "storage",
    'DBRunner'
], function(_, utils, FileDescriptor, storage, DBRunner) {
    var fileSystem = {};
    
    var IDBCursor = window.IDBCursor;

    fileSystem.getLastFile = function(callback){
    	DBRunner.run(function(db){
    		var tx = db.transaction("notes", "readonly");
			var store = tx.objectStore("notes");
			var index = store.index('by_selectTime');
			
			var request = index.openCursor(null, 'prev');
			request.onsuccess = function(event) {
				var cursor = event.target.result;
				if (cursor) {
				    // Do something with the entries.
					var note = cursor.value;
					var file = new FileDescriptor();
					file.note = note;
					callback(file);
				    //cursor.continue();
				}else{
					callback();
				}
			};
			request.onerror = function(e){
			  	console.info(e);
			};
			
    	});
    };
    
    fileSystem.listFiles = function(callback){
    	DBRunner.run(function(db){
    		var tx = db.transaction("notes", "readonly");
			var store = tx.objectStore("notes");
			var index = store.index('by_selectTime');
			
			var request = index.openCursor(null, IDBCursor.prev);
			var files = [];
			request.onsuccess = function(event) {
				var cursor = event.target.result;
				if (cursor) {
				    // Do something with the entries.
					var note = cursor.value;
					var file = new FileDescriptor();
					file.note = note;
					files.push(file);
				    cursor.continue();
				}else{
					callback(files);
				}
			};
			request.onerror = function(e){
			  	console.info(e);
			};
			
    	});
    };

    return fileSystem;
});
