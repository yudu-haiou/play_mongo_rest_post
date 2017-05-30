package models;

import org.jongo.MongoCollection;
import play.api.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.Set;

/**
 * Created by friend on 5/27/17.
 */
public class HealthCheckSetItems  {

    public static PlayJongo playJongo = Play.current().injector().instanceOf(PlayJongo.class);

    public static MongoCollection healthchecksetitems() {
        return playJongo.getCollection("healthchecksetitems");
    }

    public HealthCheckSetItems() {}

    public HealthCheckSetItems(String name, Set<Integer> sets) {
        this.sets = sets;
        this.name = name;
    }

    public String name;

    public Set<Integer> sets;

    public HealthCheckSetItems insert() {
        healthchecksetitems().save(this);
        return this;
    }

    public void remove() {
        healthchecksetitems().remove(this.name);
    }

    public static HealthCheckSetItems findByName(String name) {
        return healthchecksetitems().findOne("{name: #}", name).as(HealthCheckSetItems
                .class);
    }

    @Override
    public String toString() {
        return "HealthCheckSetItems name: " + this.name + ", set: " + this.sets.toString();
    }
}
