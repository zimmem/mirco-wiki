define(["eventMgr",
	"helpers/wikiHelper",
	"fileMgr"
], function (eventMgr, helper, fileMgr) {

	var wikiMgr = {};

	wikiMgr.post = function (f, callback) {

		helper.post(f, function (wiki) {
			 f.update({id:wiki.id})
		});
		callback || callback();
	}



	wikiMgr.load = function (id, callback) {
		helper.getWiki(id, callback);
	}

	eventMgr.onWikiMgrCreated(wikiMgr);
	return wikiMgr;

});
