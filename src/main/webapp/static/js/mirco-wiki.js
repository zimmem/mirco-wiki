var mirco_wiki={ 

prettyCode:function(){
	$(function(){
		$('pre').addClass('prettyprint').addClass('linenums:1');
		prettyPrint();
	});
},

postPage:function(){
	// $('#wiki-tab').tabs();
	$('#preview-tab').click(function() {
		jQuery.post('/api-wiki/render', {
			'wiki' : $('#wiki-text').val()
		}, function(data) {
			$('#wiki-content').html(data);
			$('#wiki-edit').hide();
			$('#wiki-content-preview').show();
		});
	});
	$('#edit-tab').click(function() {
		$('#wiki-content-preview').hide();
		$('#wiki-edit').show();
	});
},

diff:function(){
	$('#diff').html( diffString('$!esc.javascript($preRevision.wiki)','$!esc.javascript($afterRevision.wiki)')
			.replace(/\r\n/gm,'<br />')
			.replace(/\r/gm,'<br />'));
}

}