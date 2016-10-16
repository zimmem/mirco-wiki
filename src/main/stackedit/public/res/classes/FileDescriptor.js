define([
	"underscore",
	"utils",
	"storage",
	"DBRunner",
	'logger'
], function(_, utils, storage, DBRunner, logger) {

	function FileDescriptor(fileIndex) {
		
		
		this.note = {
			key : fileIndex
		};
		
		Object.defineProperty(this, 'fileIndex', {
			get: function() {
				return this.note.key;
			}
		});
		
		Object.defineProperty(this, 'key', {
			get: function() {
				return this.note.key;
			},
		});
		
		Object.defineProperty(this, 'guid', {
			get: function() {
				return this.note.guid;
			},
			set: function(guid) {
				this.update({guid:guid});
			}
		});
		
		Object.defineProperty(this, 'title', {
			get: function() {
				return this.note.title;
			},
			set: function(title) {
				this.update({title:title});
			}
		});
		Object.defineProperty(this, 'content', {
			get: function() {
				return storage[this.key + ".content"];
			},
			set: function(content) {
				storage[this.key + ".content"] = content;
			}
		});
		Object.defineProperty(this, 'editorScrollTop', {
			get: function() {
				return this.note.editorScrollTop;
			},
			set: function(editorScrollTop) {
				this.update({editorScrollTop:editorScrollTop});
			}
		});
		Object.defineProperty(this, 'editorStart', {
			get: function() {
				return this.note.editorStart;
			},
			set: function(editorStart) {
				this.update({editorStart:editorStart});
			}
		});
		Object.defineProperty(this, 'editorEnd', {
			get: function() {
				return this.note.editorEnd;
			},
			set: function(editorEnd) {
				this.update({editorEnd:editorEnd});
			}
		});
		Object.defineProperty(this, 'previewScrollTop', {
			get: function() {
				return this.note.previewScrollTop;
			},
			set: function(previewScrollTop) {
				this.update({previewScrollTop:previewScrollTop});
			}
		});
		Object.defineProperty(this, 'selectTime', {
			get: function() {
				return this.note.selectTime;
			},
			set: function(selectTime) {
				this.update({selectTime:selectTime});
			}
		});
		
		Object.defineProperty(this, 'localEdite', {
			get: function() {
				return this.note.localEdite;
			},
			set: function(localEdite) {
				this.update({localEdite:localEdite});
			}
		});
		
	}

	FileDescriptor.prototype.update = function(target, callback){
		_.extend(this.note, target);
		if(target.content){
			this.content = target.content;
			delete this.note.content;
		}
		this.store(callback);
		
	};
	
	FileDescriptor.prototype.store = function(callback){
		var that = this;
		DBRunner.run(function(db){
			var tx = db.transaction("notes", "readwrite");
			var store = tx.objectStore("notes");
			store.put(that.note);
			tx.oncomplete = function() {
				callback && callback(that);
				logger.log("save note to indexdDB success");
			};
		});
	};
	
	return FileDescriptor;
});
