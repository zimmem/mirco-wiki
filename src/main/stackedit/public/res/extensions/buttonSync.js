define([
    "jquery",
    "underscore",
    "crel",
    "utils",
    "classes/Extension",
    "mousetrap",
    "text!html/buttonSyncSettingsBlock.html",
], function($, _, crel, utils, Extension, mousetrap, buttonSyncSettingsBlockHTML) {

    var buttonSync = new Extension("buttonSync", 'Button "Synchronize"', false, true);
    buttonSync.settingsBlock = buttonSyncSettingsBlockHTML;
    buttonSync.defaultConfig = {
        syncPeriod: 180000,
        syncShortcut: 'mod+s'
    };

    buttonSync.onLoadSettings = function() {
        utils.setInputValue("#input-sync-period", buttonSync.config.syncPeriod);
        utils.setInputValue("#input-sync-shortcut", buttonSync.config.syncShortcut);
    };

    buttonSync.onSaveSettings = function(newConfig, event) {
        newConfig.syncPeriod = utils.getInputIntValue("#input-sync-period", event, 0);
        newConfig.syncShortcut = utils.getInputTextValue("#input-sync-shortcut", event);
    };
    
    var fileMgr;
    buttonSync.onFileMgrCreated = function(f) {
        fileMgr = f;
    };
    
    var noteMgr;
    buttonSync.onNoteMgrCreated = function(n) {
    	noteMgr = n;
    };

    var $button;
    var syncRunning = false;
    var isOffline = false;
    // Enable/disable the button
    var updateButtonState = function() {
        if($button === undefined) {
            return;
        }
        if(syncRunning === true || isOffline) {
            $button.addClass("disabled");
        }
        else {
            $button.removeClass("disabled");
        }
    };

 
    buttonSync.onCreateButton = function() {
        var button = crel('a', {
            class: 'btn btn-success button-synchronize',
            title: 'Force synchronization Ctrl/Cmd+S'
        }, crel('i', {
            class: 'icon-upload-cloud'
        }));
        $button = $(button);
        $button.click(function() {
            if(!$button.hasClass("disabled")) {
            	postNote();
            }
        });
        return button;
    };

    buttonSync.onReady = updateButtonState;
    buttonSync.onFileCreated = updateButtonState;
    buttonSync.onFileDeleted = updateButtonState;
    buttonSync.onSyncRemoved = updateButtonState;

    buttonSync.onSyncRunning = function(isRunning) {
        syncRunning = isRunning;
        updateButtonState();
    };

    buttonSync.onOfflineChanged = function(isOfflineParameter) {
        isOffline = isOfflineParameter;
        updateButtonState();
    };

    buttonSync.onReady = function() {
        mousetrap.bind(buttonSync.config.syncShortcut, function(e) {
        	e.preventDefault();
        	postNote();
        });
        $(".action-force-synchronization").click(function() {
        	postNote();
        });
    };
    
    function postNote(){
    	noteMgr.postNote(fileMgr.currentFile, function(error){
    		if(!error){
    			console.info("post note success");
    		}
    	});
    }

    return buttonSync;

});
