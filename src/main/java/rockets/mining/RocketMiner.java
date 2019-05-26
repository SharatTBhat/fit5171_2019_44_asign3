package rockets.mining;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rockets.dataaccess.DAO;
import rockets.model.Launch;
import rockets.model.LaunchServiceProvider;
import rockets.model.Payload;
import rockets.model.Rocket;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RocketMiner {
    private static Logger logger = LoggerFactory.getLogger(RocketMiner.class);

    private DAO dao;
    String negativeValueIfo="Cannot input negative value";


    public RocketMiner(DAO dao) {
        this.dao = dao;
    }

    /**
     * to be implemented & tested!
     * Returns the top-k active rocket, as measured by number of launches.
     *
     * @param k the number of rockets to be returned.
     * @return the list of k most active rockets.
     */
    public List<Rocket> mostLaunchedRockets(int k) {

        Collection<Launch> launches = dao.loadAll(Launch.class);
        List<Rocket> rocketList = new CopyOnWriteArrayList<>(launches.stream()
                .map(Launch::getLaunchVehicle)
                .collect(Collectors.toList()));
        for(Rocket rocket:rocketList){
            if(rocket==null){
                rocketList.remove(rocket);
            }
        }
        if (k <= 0) {
            throw new IllegalArgumentException(negativeValueIfo);
        } else if (k > rocketList.size()) {
            throw new IllegalArgumentException("Do not have enough valid rockets");
        } else {
            Map<Rocket, Long> rocketMap = rocketList.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            Map<Rocket, Long> sortedMap = new LinkedHashMap<>();
            rocketMap.entrySet().stream()
                    .sorted(Map.Entry.<Rocket, Long>comparingByValue().reversed())
                    .forEachOrdered(e -> sortedMap.put(e.getKey(), e.getValue()));
            List<Rocket> real = new ArrayList<>();
            sortedMap.entrySet().stream().limit(k).forEach(x -> real.add(x.getKey()));
            return real;
        }


    }

    /**
     * to be implemented & tested!
     * <p>
     * Returns the top-k most reliable launch service providers as measured
     * by percentage of successful launches.
     *
     * @param k the number of launch service providers to be returned.
     * @return the list of k most reliable ones.
     */
    public List<LaunchServiceProvider> mostReliableLaunchServiceProviders(int k) {


        Collection<Launch> launches = dao.loadAll(Launch.class);
        List<Launch> launchList = new CopyOnWriteArrayList<>(launches);
        for (Launch launch : launchList) {
            if (launch.getLaunchServiceProvider() == null) {
                launchList.remove(launch);
            }
        }
        if (k <= 0) {
            throw new IllegalArgumentException(negativeValueIfo);
        } else if (k > launchList.size()) {
            throw new IllegalArgumentException("Do not have enough launch service providers");
        }
        else {
            ArrayList<LaunchServiceProvider> sortedLsp = new ArrayList<>();
            Map<LaunchServiceProvider, Double> percentage = launchList.stream()
                    .collect(Collectors.groupingBy(Launch::getLaunchServiceProvider, Collectors
                            .mapping(Launch::getLaunchOutcome, Collectors.averagingDouble(
                                    success -> {
                                        return Launch.LaunchOutcome.SUCCESSFUL.equals(success) ? 1 : 0;
                                    }
                            ))));
            percentage.entrySet().stream().sorted(
                    Map.Entry.<LaunchServiceProvider, Double>comparingByValue().reversed()).forEachOrdered(e -> sortedLsp.add(e.getKey())
            );
            return sortedLsp.stream().limit(k).collect(Collectors.toList());
        }


    }

    /**
     * <p>
     * Returns the top-k most recent launches.
     *
     * @param k the number of launches to be returned.
     * @return the list of k most recent launches.
     */
    public List<Launch> mostRecentLaunches(int k) {
        logger.info("find most recent " + k + " launches");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        Comparator<Launch> launchDateComparator = (a, b) -> -a.getLaunchDate().compareTo(b.getLaunchDate());
        return launches.stream().sorted(launchDateComparator).limit(k).collect(Collectors.toList());
    }


    /**
     * <p>
     * Returns the dominant country who has the most launched rockets in an orbit.
     *
     * @param orbit the orbit
     * @return the country who sends the most payload to the orbit
     */
    public String dominantCountry(String orbit) {
        Validate.notBlank(orbit,"Orbit cannot be null or empty");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        ArrayList<String> sortedCountry=new ArrayList<>();
        List<String> orbits=launches.stream()
                .map(Launch::getOrbit)
                .collect(Collectors.toList());
        if(!orbits.contains(orbit)){
            throw new IllegalArgumentException("Orbit do not exist");
        }
        Map<String,Long> countryOrbit=launches.stream()
                .filter(launch -> launch.getOrbit().equals(orbit))
                .map(launch->launch.getLaunchServiceProvider().getCountry())
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        countryOrbit.entrySet().stream()
                .sorted(Map.Entry.<String,Long>comparingByValue().reversed())
                .forEachOrdered(e->sortedCountry.add(e.getKey()));
        return sortedCountry.get(0);
    }

    /**
     * <p>
     * Returns the top-k most expensive launches.
     *
     * @param k the number of launches to be returned.
     * @return the list of k most expensive launches.
     */
    public List<Launch> mostExpensiveLaunches(int k) {

        Collection<Launch> launches = dao.loadAll(Launch.class);
        if(k<=0){
            throw new IllegalArgumentException(negativeValueIfo);
        }else if(k>launches.size()){
            throw new IllegalArgumentException("Do not have enough rockets in database");
        }
        Comparator<Launch> bigDecimalComparator= Comparator.comparing(Launch::getPrice);
        List<Launch> sortedLaunch=launches.stream().sorted(bigDecimalComparator.reversed()).collect(Collectors.toList());
        return sortedLaunch.stream().limit(k).collect(Collectors.toList());
    }







}
