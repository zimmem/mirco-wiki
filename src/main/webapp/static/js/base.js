var mirco_wiki = {
    diff : function() {
        $('#diff').html(
                diffString('$!esc.javascript($preRevision.wiki)',
                        '$!esc.javascript($afterRevision.wiki)').replace(
                        /\r\n/gm, '<br />').replace(/\r/gm, '<br />'));
    }
}