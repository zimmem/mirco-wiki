package com.zimmem.gae.wiki.seo;

import java.util.ArrayList;
import java.util.List;

public class HtmlMetaDataTool {

    private List<HtmlMetaData> datas = new ArrayList<HtmlMetaData>();

    public void add(String name, String content) {
        HtmlMetaData data = new HtmlMetaData();
        data.setName(name);
        data.setContent(content);
        datas.add(data);
    }

    public List<HtmlMetaData> getAll() {
        return datas;
    }

}
