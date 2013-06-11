mirco_wiki.EditArticle = {
    postPage : function() {

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
}