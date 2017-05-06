package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import models.Hospital;
import post.PostAction;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by friend on 5/5/17.
 */
@With(PostAction.class)
public class HospitalsController extends Controller {

    private HttpExecutionContext ec;

    @Inject
    public HospitalsController(HttpExecutionContext ec) {
        this.ec =ec;
    }

    public CompletionStage<Result> saveHospital() {
        return CompletableFuture.supplyAsync(() -> {
            JsonNode json = request().body().asJson();
            final Hospital hospital = Json.fromJson(json, Hospital.class);
            return hospital.insert().toString();
        }, ec.current()).thenApply(i -> ok("Got result: " + i));
    }
}
