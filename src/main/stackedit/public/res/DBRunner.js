define([], function() {

	var DBRunner = {};
	
	DBRunner.db = null;
	DBRunner.creating = false;
	DBRunner.created = false;
	
	function waitToRun(callback){
		if(DBRunner.creating){
			setTimeout(function(){
				waitToRun(callback);
			}, 100);
		}else{
			callback(DBRunner.db);
		}
	}
	
	DBRunner.run = function(callback) {
		if(DBRunner.created){
			callback(DBRunner.db);
		}else if(DBRunner.creating){
			waitToRun(callback);
		}else{
			var  request = window.indexedDB.open("notedown",1);
			DBRunner.creating = true;
			request.onupgradeneeded = function(event) {
				 if (event.oldVersion < 1) {
					// The database did not previously exist, so create object stores
					// and indexes.
					var db = request.result;
					var noteStore = db.createObjectStore("notes", {
						keyPath : "key"
					});
					noteStore.createIndex("by_guid", "guid", {
						unique : true
					});
					
					noteStore.createIndex("by_selectTime", "selectTime", {
						unique : false
					});
					}
			};

			request.onsuccess = function() {
				DBRunner.db = request.result;
				DBRunner.creating = false;
				DBRunner.created = true;
				callback(request.result);
			};

			request.onerror = function(){
				console.error("open indexedDB error");
			};
		}
		
	};

	return DBRunner;

});