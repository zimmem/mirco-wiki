package com.zimmem.velocity.reference;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.velocity.app.event.ReferenceInsertionEventHandler;
import org.apache.velocity.context.Context;
import org.apache.velocity.util.ContextAware;

public class AutoEascepeReference implements ContextAware, ReferenceInsertionEventHandler {

    private Context        context;

    private static Pattern pattern = Pattern.compile("[^\\\\$\\\\{\\\\}!]+");

    @Override
    public Object referenceInsert(String reference, Object value) {

        return value;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    public static void main(String[] args) {
        String[] a = { "$!{sdfsdf}", "${sfsfdf}", "sdf sdf ", "$sfsdf.sdfsdf" };
        for (String string : a) {
            Matcher matcher = pattern.matcher(string);
            if (matcher.find()) System.out.println(matcher.group());
        }
    }

}
