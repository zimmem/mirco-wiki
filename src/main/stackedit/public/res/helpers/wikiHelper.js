define(["jquery",
		'underscore',
		"constants",
		"core",
		"utils",
		"storage",
		"logger",
		"eventMgr",
		"classes/AsyncTask",
		'previewTheme'],
	function ($, _, C, core,  utils, storage, log, eventMgr, AsyncTask, previewTheme) {

		var wikiHelper = {};

		wikiHelper.post = function (f, callback) {

			if (!f) {
				return;
			}

			var url = "/post_page"
			var method = 'POST';
			var markdown = f.content;
			var $clone = $('#preview-contents').clone();
			$clone.find('#wmd-preview').remove();

			//applayCss(previewTheme.element.sheet.rules, $clone);

			// remove all unsupport attribute
			// removeAttribute($clone, 'id', ".prettyprint");
			// removeAttribute($clone, 'class', ".prettyprint");


			$clone.find(".prettyprint").each(function(i,e){
				$(e).find("code").empty().text($(e).data("plain"));
				removeAttribute($(e), "style");
				removeAttribute($(e), "data-plain");
			});

			$clone.find("script").each(function(){
				var $this = $(this);
                if($this.attr("type") == "math/tex"){
					$this.replaceWith( "$" + $this.text() +"$")
				}else if($this.attr("type") == "math/tex; mode=display"){
					$this.replaceWith( "$$" + $this.text() +"$$")
				}

			})

			$clone.find(".MathJax_Display").remove();
			$clone.find(".MathJax_Preview").remove();
			$clone.find(".MathJax").remove();

			// 暂时这么处理 br吧
			var html = $clone.html()
			//.replace(/<br>/g, "<br/>")
			// .replace(/<hr([^>\/])*>/g, function(a ){
			// 	return a.substr(0, a.length-1) + '/>';
			// });

			console.info(html)

			var task = new AsyncTask();
			task.onRun(function () {
				$.ajax({
					url: url,
					type: method,
					contentType: "application/json",
					dataType: 'json',
					beforeSend: function (xhrObj) {
						xhrObj.setRequestHeader("Content-Type",
							"application/json");
						xhrObj.setRequestHeader("Accept",
							"application/json");
					},
					data: JSON.stringify({
						id: f.id,
						title:f.title,
						wiki: markdown,
						html: html
					}),
					success: function (wiki) {
						callback(wiki)
						task.chain();
					},
					error: function () {
						task.error();
					}

				});
			});
			task.onError(function (e) {
				//callback(e);
			});
			task.onSuccess(function () {
				//callback();
			});
			task.enqueue();
		}

		wikiHelper.getWiki = function(id, callback){
			var task = new AsyncTask();
			task.onRun(function () {
				$.get("/wiki/" + id , function(wiki){
					callback(wiki);
					task.chain();
				});
			});
			task.onError(function (e) {
				//callback(e);
			});
			task.onSuccess(function () {

			});
			task.enqueue();
		}

		function removeAttribute($root, attName, excludeSelector) {
			if(excludeSelector && $root.is(excludeSelector)){
				return;
			}
			$root.removeAttr(attName);
			$root.children().each(function (i, e) {
				removeAttribute($(e), attName,excludeSelector);
			});
		}

		function applayCss(rules, $root) {
			_.each(rules, function (rule) {
				$root.find(rule.selectorText).each(function (i, e) {
					e.style.cssText += rule.style.cssText;
				});
			});
		}

		return wikiHelper;
	});
