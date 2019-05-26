package rockets.model;

import org.neo4j.ogm.annotation.CompositeIndex;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@NodeEntity
@CompositeIndex(properties = {"launchDate", "launchVehicle", "launchSite", "orbit"}, unique = true)
public class Launch extends Entity {

    public enum LaunchOutcome {
        FAILED, SUCCESSFUL
    }

    @Property(name = "launchDate")
    private LocalDate launchDate;

    @Relationship(type = "PROVIDES", direction = INCOMING)
    private Rocket launchVehicle;

   // private Set<String> payload;

    @Property(name = "launchSite")
    private String launchSite;

    @Property(name = "orbit")
    private String orbit;

    @Property(name = "function")
    private String function;

    @Property(name = "launchOutcome")
    private LaunchOutcome launchOutcome;

    @Property(name = "price")
    //private int price;
    private BigDecimal price;

    private Set<Payload> payloads;


    private LaunchServiceProvider launchServiceProvider;


    public LocalDate getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(LocalDate launchDate) {
        this.launchDate = launchDate;
    }

    public void setLaunchVehicle(Rocket launchVehicle) {
        this.launchVehicle = launchVehicle;
    }

    public void setOrbit(String orbit) {
        notBlank(orbit, "Orbit cannot be null or empty");
        String regEx = "^[a-zA-Z]+[0-9]*";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(orbit);
        if (!m.matches()) {
            throw new IllegalArgumentException("Please using alphabetic characters");
        }
        this.orbit = orbit;
    }

    public LaunchServiceProvider getLaunchServiceProvider() {
        return launchServiceProvider;
    }

    public void setLaunchServiceProvider(LaunchServiceProvider launchServiceProvider) {
        this.launchServiceProvider = launchServiceProvider;
    }


    public Rocket getLaunchVehicle() {
        return launchVehicle;
    }

   /* public Set<String> getPayload() {
        return payload;
    }*/

    public String getLaunchSite() {
        return launchSite;
    }

    public String getOrbit() {
        return orbit;
    }

    public String getFunction() {
        return function;
    }

    public LaunchOutcome getLaunchOutcome() {
        return launchOutcome;
    }

    /*public void setPayload(Set<String> payload) {
        this.payload = payload;
    }*/

    public Set<Payload> getPayload() {
        return payloads;
    }

    public void setPayload(Set<Payload> payload) {
        this.payloads=payload;
    }

    public void setLaunchSite(String launchSite) {
        notBlank(launchSite, "Launch site cannot be null or empty");
        String regEx = "^[a-zA-Z]+[0-9]*";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(launchSite);
        if (!m.matches()) {
            throw new IllegalArgumentException("Please using alphabetic characters");
        }
        this.launchSite = launchSite;
    }

    public void setFunction(String function) {
        notBlank(function, "Launch function cannot be null or empty");
        String regEx = "^[a-zA-Z]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(function);
        if (!m.matches()) {

            throw new IllegalArgumentException("Please using alphabetic characters");
        }
        this.function = function;
    }

    public void setLaunchOutcome(LaunchOutcome launchOutcome) {
        this.launchOutcome = launchOutcome;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price should larger than 0");
        }
        String str = price.toString();
        String regEx = "^[0-9]+[.]?[0-9]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (!m.matches()) {
            throw new IllegalArgumentException("Please using double value");
        }
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Launch launch = (Launch) o;
        return Objects.equals(launchDate, launch.launchDate) &&
                Objects.equals(launchVehicle, launch.launchVehicle) &&
                Objects.equals(orbit, launch.orbit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(launchDate, launchVehicle, orbit);
    }
}
