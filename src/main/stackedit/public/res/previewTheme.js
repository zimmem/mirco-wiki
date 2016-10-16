define([
    'eventMgr',
    'text!../res-min/preview-style/default.css'
], function(eventMgr, themeCss){
	
	var previewTheme = {};
	
	eventMgr.addListener('onReady', function(){
		var element = previewTheme.element = document.createElement('style');
		element.innerHTML = themeCss;
		element.id = "preview-theme";
		document.head.appendChild(element);
		eventMgr.onPreviewThemeInited(previewTheme);
	});
	
	eventMgr.onPreviewThemeCreated(previewTheme);
	return previewTheme;
	
});