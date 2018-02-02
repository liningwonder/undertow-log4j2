package org.lining.undertow;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import org.lining.undertow.servlet.MessageServlet;

import javax.servlet.ServletException;

import static io.undertow.servlet.Servlets.*;

/**
 * description:
 * date 2018/1/25
 *
 * @author lining1
 * @version 1.0.0
 */
public class ServletServer {

    public static final String MYAPP = "/portal";

    public static void main(String[] args) {
        try {
            DeploymentInfo servletBuilder = deployment()
                    .setClassLoader(ServletServer.class.getClassLoader())
                    .setContextPath(MYAPP)
                    .setDeploymentName("portal.war")
                    .addServlet(servlet("MessageServlet", MessageServlet.class).addMapping("/message"));
            DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
            manager.deploy();

            HttpHandler servletHandler = manager.start();
            PathHandler path = Handlers.path(Handlers.redirect(MYAPP))
                    .addPrefixPath(MYAPP, servletHandler);
            Undertow server = Undertow.builder()
                    .addHttpListener(8080, "localhost")
                    .setHandler(path)
                    .build();
            server.start();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
