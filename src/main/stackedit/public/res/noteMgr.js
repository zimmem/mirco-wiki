define([
        'underscore',
        'eventMgr',
        'helpers/evernoteHelper',
        'fileSystem',
        'classes/FileDescriptor',
        'utils'
], function(_, eventMgr, helper, fileSystem, FileDescriptor, utils){
	
	var noteMgr = {};
	
	noteMgr.listNots = function(callback){
		helper.listNotes(callback);
	};
	
	noteMgr.refreshNotes = function(){
		noteMgr.listNots(function(error, notes){
			if(error){
				eventMgr.onError(error);
				return;
			}
			fileSystem.listFiles(function(files){
				var noteMap = {};
				_.each(notes, function(note){
					noteMap[note.guid] = note;
 				});
 				
 				var fileMap = {};
				_.each(files, function(file){
					fileMap[file.guid] = file;
				});
				
				var filesToCreate = [];
				var filteredNotes = _.filter(notes, function(note){
					return !fileMap[note.guid];
				});
				_.each(filteredNotes, function(note){
					var file = new FileDescriptor('file.'+utils.id());
					file.update(_.extend(note, {selectTime:0}));
					// TODO 改面批量异步
					filesToCreate.push(file);
				});
				
				//update file
				var filteredFile = _.filter(files, function(file){
					return _.has(noteMap, file.guid);
				});
				_.each(filteredFile, function(file){
					// 如果本地修改过， 就不变更了
					if(!file.localEdite){
						//TODO 改面批量异步
						file.update(noteMap[file.guid]);
					}
				});
				
				eventMgr.onNotesRefresh();
				
			});
		});
	};
	noteMgr.downloadNote = function(guid, callback){
		helper.downloadNote(guid, callback);
	};
	
	noteMgr.postNote = function(file, callback){
		helper.postNote(file, callback);
	};
	
	noteMgr.shareNote = function(file, callback){
		helper.shareNote(file, callback);
	};
	
	noteMgr.stopShare = function(guid, callback){
		helper.stopShare(guid, callback);
	};
	
	eventMgr.onNoteMgrCreated(noteMgr);
	
	return noteMgr;
	
});