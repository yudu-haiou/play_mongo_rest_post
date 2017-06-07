package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.HealthCheckSetItems;
import models.HealthCheckSets;
import org.jongo.MongoCursor;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by friend on 5/29/17.
 */
public class HealthCheckSetItemsController extends Controller {

    private HttpExecutionContext ec;

    @Inject
    public HealthCheckSetItemsController(HttpExecutionContext ec) {
        this.ec = ec;
    }

    public CompletionStage<Result> saveHealthCheckSetItems() {
        return CompletableFuture.supplyAsync(() -> {
            JsonNode json = request().body().asJson();
            final HealthCheckSetItems healthCheckSetItems = Json.fromJson(json, HealthCheckSetItems.class);
            return healthCheckSetItems.insert().toString();
        }, ec.current()).thenApply(i -> ok("Got result: " + i));
    }

    public CompletionStage<Result> getHealthCheckSetItemsBySetsId(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            MongoCursor<HealthCheckSetItems> cursor = HealthCheckSetItems.findBySetsId(id);
            StringBuffer sb = new StringBuffer();
            while (cursor.hasNext()) {
                sb.append((HealthCheckSetItems) cursor.next()).toString();
            }
            return null != sb ? sb.toString() : "Empty result!";
        },ec.current()).thenApply(i -> ok("Got result: " + i));

    }

    public CompletionStage<Result> getHealthCheckSetsByName(String name) {
        return CompletableFuture.supplyAsync(() -> {
            HealthCheckSetItems healthCheckSetItems = HealthCheckSetItems.findByName(name);
            StringBuffer sb = new StringBuffer();
            if (null != healthCheckSetItems) {
                sb.append(healthCheckSetItems.toString());
                sb.append(System.lineSeparator());
                healthCheckSetItems.sets.stream().forEach(id -> {
                    HealthCheckSets healthCheckSets = HealthCheckSets.findBySetId(id);
                    sb.append(healthCheckSets.toString());
                    sb.append(System.lineSeparator());
                });
            }
            return null != sb ? sb.toString() : "Empty result!";
        },ec.current()).thenApply(i -> ok("Got result: " + i));
    }
}

