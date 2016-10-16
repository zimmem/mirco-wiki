define([
    "jquery",
    "underscore",
    "utils",
    "classes/Extension",
    "fileSystem"
    
], function($, _, utils, Extension,  fileSystem) {

    var documentPanel = new Extension("documentPanel", 'Document Panel');

    var fileMgr;
    documentPanel.onFileMgrCreated = function(fileMgrParameter) {
        fileMgr = fileMgrParameter;
    };
    
    var noteMgr;
    documentPanel.onNoteMgrCreated = function(theNoteMgr){
    	noteMgr = theNoteMgr;
    };
    

//    var folderEltTmpl = [
//        '<a href="#"',
//        ' class="list-group-item folder clearfix"',
//        ' data-folder-index="<%= folderDesc.folderIndex %>"',
//        ' data-toggle="collapse"',
//        ' data-target=".file-list.<%= id %>">',
//        '   <div class="pull-right file-count">',
//        '       <%= _.size(folderDesc.fileList) %>',
//        '   </div>',
//        '   <i class="icon-folder"></i> <%= folderDesc.name %>',
//        '</a>',
//        '<div class="file-list collapse <%= id %> clearfix">',
//        '   <%= fileListHtml %>',
//        '</div>'
//    ].join('');
    var documentEltTmpl = [
        '<a href="#"',
        ' class="list-group-item file<%= fileDesc === selectedFileDesc ? " active" : "" %>"',
        ' data-file-index="<%= fileDesc.fileIndex %>">',
        '<i class="icon-doc"></i>',
        '   <%= fileDesc.title %>',
        '</a>',
    ].join('');
    

    var panelElt;
    var documentListElt;
    var $documentListElt;
    var documentListFilteredElt;
    var $documentListFilteredElt;
    var selectedFileDesc;
    var refreshPanel = _.debounce(function() {
    	
    	fileSystem.listFiles(function(files){
    		// List orphan documents
            var orphanDocumentList = _.filter(files, function(fileDesc) {
                return fileDesc.folder === undefined;
            });

            // Add orphan documents
            var documentListHtml = _.chain(orphanDocumentList).sortBy(function(fileDesc) {
                return fileDesc.title.toLowerCase();
            }).reduce(function(result, fileDesc) {
                return result + '<li>' + _.template(documentEltTmpl, {
                    fileDesc: fileDesc,
                    selectedFileDesc: selectedFileDesc
                }) + '</li>';
            }, '').value();
            documentListHtml = documentListHtml && '<ul class="nav">' + documentListHtml + '</ul>';

            documentListElt.innerHTML = documentListHtml;

            // Create filtered list
            var documentListFilteredHtml = _.chain(files).sortBy(function(fileDesc) {
                return fileDesc.title.toLowerCase();
            }).reduce(function(result, fileDesc) {
                return result + '<li>' + _.template(documentEltTmpl, {
                    fileDesc: fileDesc,
                    selectedFileDesc: selectedFileDesc
                }) + '</li>';
            }, '').value();
            documentListFilteredHtml = '<ul class="nav">' + documentListFilteredHtml + '</ul>';

            documentListFilteredElt.innerHTML = documentListFilteredHtml;
    	});

    }, 50);

    documentPanel.onFileSelected = function(fileDesc) {
        selectedFileDesc = fileDesc;
        refreshPanel();
    };

    documentPanel.onFileCreated = refreshPanel;
    documentPanel.onFileDeleted = refreshPanel;
    documentPanel.onTitleChanged = refreshPanel;
    documentPanel.onNotesRefresh = refreshPanel;
    
    // Filter for search input in file selector
    var panelContentElt;
    var previousFilterValue = '';
    function filterFiles(filterValue) {
        if(filterValue == previousFilterValue) {
            return;
        }
        previousFilterValue = filterValue;

        // Scroll to top
        panelContentElt.scrollTop = 0;

        if(!filterValue) {
            $documentListFilteredElt.addClass('hide');
            $documentListElt.removeClass('hide');
            return;
        }
        var wordList = filterValue.toLowerCase().split(/\s+/);
        _.each(documentListFilteredElt.querySelectorAll('.file'), function(fileElt) {
            var $fileElt = $(fileElt);
            var fileTitle = $fileElt.text().toLowerCase();
            $fileElt.toggle(!_.some(wordList, function(word) {
                return fileTitle.indexOf(word) === -1;
            }));
        });
        $documentListFilteredElt.removeClass('hide');
        $documentListElt.addClass('hide');
    }
    
       documentPanel.onReady = function() {
        panelElt = document.querySelector('.document-panel');
        panelContentElt = panelElt.querySelector('.panel-content');
        documentListElt = panelElt.querySelector('.document-list');
        $documentListElt = $(documentListElt);
        documentListFilteredElt = panelElt.querySelector('.document-list-filtered');
        $documentListFilteredElt = $(documentListFilteredElt);

        // Open current folder before opening
        $(panelElt).on('shown.layout.toggle', function() {
            // Scroll to the active file
            var activeElt = documentListElt.querySelector('.file.active');
            activeElt && (panelContentElt.scrollTop += activeElt.getBoundingClientRect().top - 240);
        }).on('hidden.layout.toggle', function() {
            // Unset the filter
            $filterInputElt.val('');
            filterFiles('');
        }).on('click', '.file', function() {
            var $fileElt = $(this);
            fileMgr.selectFile($fileElt.data('fileIndex'));
        });

        // Search bar input change
        var $filterInputElt = $(panelElt.querySelector('.search-bar .form-control'));
        $filterInputElt.bind("propertychange keyup input paste", function() {
            filterFiles($filterInputElt.val());
        });
        
        $('.action-fresh-notes').click(function(e){
        	e.stopPropagation();
        	$(this).find('.icon-refresh').addClass('spin');
        	noteMgr.refreshNotes();
        	return false;
        });

    };

    return documentPanel;

});
