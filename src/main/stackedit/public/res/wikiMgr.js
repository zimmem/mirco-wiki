define(["eventMgr",
	"helpers/wikiHelper",
	"fileMgr"
], function (eventMgr, helper, fileMgr) {

	var wikiMgr = {};

	wikiMgr.post = function (f, callback) {

		helper.post(f)
		callback || callback();
	}
	
	

	wikiMgr.load = function (id, callback) {
		helper.getWiki(id, callback);
	}

	eventMgr.onWikiMgrCreated(wikiMgr);
	return wikiMgr;

});
