package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import play.api.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

/**
 * Created by friend on 5/4/17.
 */
public class Car {

    public static PlayJongo playJongo = Play.current().injector().instanceOf(PlayJongo.class);

    public static MongoCollection cars() {
        return playJongo.getCollection("cars");
    }

    public Car() {}

    public Car(String name) {
        this.name = name;
    }

    @JsonProperty("_id")
    public ObjectId id;

    public String name;

    public Car insert() {
        cars().save(this);
        return this;
    }

    public void remove() {
        cars().remove(this.id);
    }

    public static Car findByName(String name) {
        return cars().findOne("{name: #}", name).as(Car
                .class);
    }

}
