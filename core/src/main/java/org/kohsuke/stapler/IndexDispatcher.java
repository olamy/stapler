package org.kohsuke.stapler;

import javax.servlet.ServletException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * {@link Dispatcher} for url=/ that handles the tail of an URL.
 *
 * @author Kohsuke Kawaguchi
 */
class IndexDispatcher extends Dispatcher {
    private final Function f;

    IndexDispatcher(Function f) {
        this.f = f;
    }

    public boolean dispatch(RequestImpl req, ResponseImpl rsp, Object node) throws IllegalAccessException, InvocationTargetException, ServletException, IOException {
        if (req.tokens.hasMore())
            return false;   // applicable only when there's no more token

        if (traceable())
            trace(req, rsp, "-> <%s>.%s(...)", node, f.getName());

        return f.bindAndInvokeAndServeResponse(node, req, rsp);
    }

    public String toString() {
        return f.getQualifiedName() + "(...) for url=/";
    }
}
