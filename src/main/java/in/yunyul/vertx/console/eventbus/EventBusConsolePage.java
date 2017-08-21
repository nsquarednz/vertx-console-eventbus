package in.yunyul.vertx.console.eventbus;

import in.yunyul.vertx.console.base.ConsolePage;
import io.vertx.core.Vertx;
import io.vertx.ext.dropwizard.MetricsService;
import io.vertx.ext.web.Router;

import static io.vertx.core.http.HttpHeaders.CONTENT_TYPE;

@SuppressWarnings("unused")
public class EventBusConsolePage implements ConsolePage {
    private static final String JSON_CONTENT_TYPE = "application/json";

    private final MetricsService metricsService;

    /**
     * Creates the event bus console page, using the provided metrics service
     *
     * @param metricsService the metrics service to retrieve data from
     * @return the console page
     */
    public static EventBusConsolePage create(MetricsService metricsService) {
        return new EventBusConsolePage(metricsService);
    }

    private EventBusConsolePage(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    public void mount(Vertx vertx, Router router, String basePath) {
        router.route(basePath + "/busmetrics").produces(JSON_CONTENT_TYPE).handler(ctx ->
                ctx.response().putHeader(CONTENT_TYPE, JSON_CONTENT_TYPE).end(
                        metricsService.getMetricsSnapshot("vertx.eventbus").toBuffer()
                ));
    }

    @Override
    public String getLoaderFileName() {
        return "/js/eventbus.js";
    }
}
