package events.common.audit;


import com.mysema.commons.lang.Assert;

public class AuditorContextHolder {

    private static final ThreadLocal<AuditorContext> contextHolder = new ThreadLocal<>();

    public static void clearContext() {
        contextHolder.remove();
    }

    public static AuditorContext getContext() {
        AuditorContext ctx = contextHolder.get();

        if (ctx == null) {
            ctx = createEmptyContext();
            contextHolder.set(ctx);
        }
        return ctx;
    }


    public static void setContext(AuditorContext context) {
        Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
        contextHolder.set(context);
    }

    private static AuditorContext createEmptyContext() {
        return AuditorContext.ofEmpty();
    }


}
