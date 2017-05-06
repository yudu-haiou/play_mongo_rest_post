package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import play.api.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

/**
 * Created by friend on 5/5/17.
 */
public class Hospital {
    public static PlayJongo playJongo = Play.current().injector().instanceOf(PlayJongo.class);

    public static MongoCollection hospitals() {
        return playJongo.getCollection("hospitals");
    }

    public Hospital() {}

    public Hospital(String name) {
        this.name = name;
    }

    @JsonProperty("_id")
    public ObjectId id;

    public String name;

    public Hospital insert() {
        hospitals().save(this);
        return this;
    }

    public static Hospital findByName(String name) {
        return hospitals().findOne("{name: #}", name).as(Hospital
                .class);
    }
}
