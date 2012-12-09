package com.zimmem.springframework.web.servlet.view.velocity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

/**
 * @author Zimmem
 */
public class COCVelocityLayoutVeiwResolver extends VelocityViewResolver {

    private static final Logger logger = LoggerFactory.getLogger(COCVelocityLayoutVeiwResolver.class);

    private String              layouts;

    private String              layoutKey;

    private String              screenContentKey;

    public void setLayouts(String layouts) {
        this.layouts = layouts;
    }

    public void setLayoutKey(String layoutKey) {
        this.layoutKey = layoutKey;
    }

    public void setScreenContentKey(String screenContentKey) {
        this.screenContentKey = screenContentKey;
    }

    @Override
    protected Class requiredViewClass() {
        return COCVelocityLayoutView.class;
    }

    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {

        if (logger.isDebugEnabled()) {
            logger.debug("Building view using coc layout resolver. View name is {}", viewName);
        }

        COCVelocityLayoutView view = (COCVelocityLayoutView) super.buildView(viewName);
       
        if (this.layoutKey != null) {
            view.setLayoutKey(this.layoutKey);
        }
        if (this.screenContentKey != null) {
            view.setScreenContentKey(this.screenContentKey);
        }
        
        if (this.layouts != null) {
            view.setLayouts(this.layouts);
        }
        return view;
    }

}
