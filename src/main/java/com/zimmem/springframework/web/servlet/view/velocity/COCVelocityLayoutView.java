package com.zimmem.springframework.web.servlet.view.velocity;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;

public class COCVelocityLayoutView extends VelocityLayoutView {

    private static final Logger logger              = LoggerFactory.getLogger(COCVelocityLayoutView.class);

    private static final String DEFAULT_LAYOUT_PATH = "/layout";

    private static final String DEFAULT_LAYOUT      = "/default.vm";

    private String              layouts             = DEFAULT_LAYOUT_PATH;

    public void setLayouts(String layouts) {
        this.layouts = layouts;
    }
    
  
    

    @Override
    public boolean checkResource(Locale locale) throws Exception {
        setLayoutUrl(findLayoutUrl());
        return super.checkResource(locale);
    }

    private String findLayoutUrl() {
        String viewUrl = getUrl();
        if (!viewUrl.startsWith("/")) {
            viewUrl = "/" + viewUrl;
        }
        String layout = this.layouts + viewUrl;
        try {
            if(logger.isDebugEnabled()){
                logger.debug("try to find layout " + layout + " with view url " + getUrl());
            }
            getTemplate(layout);
            return layout;
        } catch (Exception e) {
            if(logger.isDebugEnabled()){
                logger.debug("try to find layout " + layout + " with view url " + getUrl() , e);
            }
        }

        int index = viewUrl.lastIndexOf("/");
        while (index > 0) {
            viewUrl = viewUrl.substring(0, index);
            layout = this.layouts + viewUrl + DEFAULT_LAYOUT;
            try {
                if(logger.isDebugEnabled()){
                    logger.debug("try to find layout " + layout + " with view url " + getUrl());
                }
                getTemplate(layout);
                return layout;
            } catch (Exception e) {
            }
            index = viewUrl.lastIndexOf("/");
        }
        return  this.layouts + DEFAULT_LAYOUT;
    }

  
}
