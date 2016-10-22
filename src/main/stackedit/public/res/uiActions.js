define([
    'jquery',
    "eventMgr",
    'fileMgr',
    'noteMgr',
    'helpers/evernoteHelper'
], function($,eventMgr, fileMgr,noteMgr, evernoteHelper) {

	eventMgr.addListener("onReady", function() {
		

		$('.action-connect-evernote').click(function() {
			evernoteHelper.authenticate();
		});

		$('.action-create-note').click(function() {
			fileMgr.createFile('',function(file){
				fileMgr.selectFile(file.key);
			});
		});

		$('.action-delete-note').click(function() {

		});

		$('.action-share-note').click(function() {
			var file = fileMgr.currentFile;
			if(file){
				noteMgr.shareNote(file, function(url){
					console.info(url);
					var $a = $('<a target="_blank"/>').attr('href', url).html(url);
					$('.modal-share-note').find('.modal-body').html('').append($a);
					$('.modal-share-note').find('.action-stop-share').data('note-guid',file.guid );
					$('.modal-share-note').modal();
				});
			}
		});
		
		$('.action-stop-share').click(function() {
			var guid = $(this).data('note-guid');
			noteMgr.stopShare(guid, function(){
				eventMgr.onMessage('Stop sharing note success');
				$('.modal-share-note').modal('hide');
			});
		});
	});

});