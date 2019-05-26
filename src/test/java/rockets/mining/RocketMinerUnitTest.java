
package rockets.mining;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rockets.dataaccess.DAO;
import rockets.dataaccess.neo4j.Neo4jDAO;
import rockets.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RocketMinerUnitTest {

    private DAO dao;
    private RocketMiner miner;
    private List<Rocket> rockets;
    private List<LaunchServiceProvider> lsps;
    private List<Launch> launches;
    private List<Rocket> rocketTest;
    private List<Launch> launchesTest;


    @BeforeEach
    public void setUp() {
        dao = mock(Neo4jDAO.class);
        miner = new RocketMiner(dao);
        rockets = Lists.newArrayList();

        lsps = Arrays.asList(
                new LaunchServiceProvider("ULA", 1990, "Australia"),
                new LaunchServiceProvider("SpaceX", 2002, "USA"),
                new LaunchServiceProvider("ESA", 1975, "Europe ")
        );

        // index of lsp of each rocket
        int[] lspIndex = new int[]{0, 0, 0, 1, 1};
        // 5 rockets
        for (int i = 0; i < 5; i++) {
            rockets.add(new Rocket("rocket_" + i, "USA", lsps.get(lspIndex[i]),"Ariane"));
        }
        // month of each launch
        int[] months = new int[]{1, 6, 4, 3, 4, 11, 6, 5, 12, 5};

        // index of rocket of each launch
        int[] rocketIndex = new int[]{0, 0, 0, 0, 1, 1, 1, 2, 2, 3};

        // 10 launches
        launches = IntStream.range(0, 10).mapToObj(i -> {
//            logger.info("create " + i + " launch in month: " + months[i]);
            Launch l = new Launch();
            l.setLaunchDate(LocalDate.of(2017, months[i], 1));
            l.setLaunchVehicle(rockets.get(rocketIndex[i]));
            l.setLaunchSite("VAFB");
            l.setOrbit("LEO");
            spy(l);
            return l;
        }).collect(Collectors.toList());

        Rocket rocket1=new Rocket("r1","USA",lsps.get(0),"Ariane");
        Rocket rocket2=new Rocket("r2","Australia",lsps.get(1),"Ariane");
        Rocket rocket3=new Rocket("r3","USA",lsps.get(0),"Ariane");
        Rocket rocket4= new Rocket("r4","USA",lsps.get(2),"Ariane");
        rocketTest=Arrays.asList(rocket1,rocket2,rocket3,rocket4);

        Payload payload1=new Payload();
        payload1.setPayloadType("satellite");
        payload1.setCapacity(10000L);
        Payload payload2=new Payload();
        payload2.setPayloadType("spacecraft");
        payload2.setCapacity(5000L);
        Payload payload3=new Payload();
        payload3.setPayloadType("cargo");
        payload3.setCapacity(1000L);



        Launch launch1=new Launch();
        launch1.setLaunchDate(LocalDate.of(2017, 10, 1));
        launch1.setLaunchVehicle(rocket1);
        launch1.setLaunchSite("VAFB");
        launch1.setOrbit("LEO");
        launch1.setPrice(BigDecimal.valueOf(100));
        launch1.setLaunchServiceProvider(lsps.get(0));
        launch1.setLaunchOutcome(Launch.LaunchOutcome.SUCCESSFUL);
        launch1.setPayload(new HashSet<Payload>() { {add(payload1);add(payload2);}});
        spy(launch1);

        Launch launch2=new Launch();
        launch2.setLaunchDate(LocalDate.of(2016, 10, 1));
        launch2.setLaunchVehicle(rocket2);
        launch2.setLaunchSite("VA");
        launch2.setOrbit("LEO");
        launch2.setPrice(BigDecimal.valueOf(200));
        launch2.setLaunchServiceProvider(lsps.get(1));
        launch2.setPayload(new HashSet<Payload>() { {add(payload1);add(payload3);}});
        launch2.setLaunchOutcome(Launch.LaunchOutcome.SUCCESSFUL);
        spy(launch2);


        Launch launch3=new Launch();
        launch3.setLaunchDate(LocalDate.of(2017, 10, 1));
        launch3.setLaunchVehicle(rocket1);
        launch3.setLaunchSite("VAFB");
        launch3.setOrbit("LEO");
        launch3.setLaunchOutcome(Launch.LaunchOutcome.FAILED);
        launch3.setLaunchServiceProvider(lsps.get(0));
        launch3.setPayload(new HashSet<Payload>() { {add(payload1);}});
        launch3.setPrice(BigDecimal.valueOf(300));
        spy(launch3);

        Launch launch4=new Launch();
        launch4.setLaunchDate(LocalDate.of(2018, 10, 1));
        launch4.setLaunchVehicle(rocket3);
        launch4.setLaunchSite("VA");
        launch4.setOrbit("LE");
        launch4.setLaunchOutcome(Launch.LaunchOutcome.FAILED);
        launch4.setPrice(BigDecimal.valueOf(250));
        launch4.setPayload(new HashSet<Payload>() { {add(payload2);}});
        launch4.setLaunchServiceProvider(lsps.get(0));
        spy(launch4);


        Launch launch5=new Launch();
        launch5.setLaunchDate(LocalDate.of(2018, 10, 1));
        launch5.setLaunchVehicle(rocket4);
        launch5.setLaunchSite("VA");
        launch5.setOrbit("Mars");
        launch5.setLaunchOutcome(Launch.LaunchOutcome.FAILED);
        launch5.setPrice(BigDecimal.valueOf(210));
        launch5.setLaunchServiceProvider(lsps.get(2));
        launch5.setPayload(new HashSet<Payload>() { {add(payload1);add(payload2);}});
        spy(launch5);

        launchesTest=Arrays.asList(launch1,launch2,launch3,launch4,launch5);
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldReturnTopMostRecentLaunches(int k) {
        when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<Launch> sortedLaunches = new ArrayList<>(launches);
        sortedLaunches.sort((a, b) -> -a.getLaunchDate().compareTo(b.getLaunchDate()));
        List<Launch> loadedLaunches = miner.mostRecentLaunches(k);
        assertEquals(k, loadedLaunches.size());
        assertEquals(sortedLaunches.subList(0, k), loadedLaunches);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldReturnTopMostLaunchedRockets(int k) {
        when(dao.loadAll(Launch.class)).thenReturn(launches);
        ArrayList<Launch> rocketLaunches = new ArrayList<>(launches);
        List<Rocket> real=new ArrayList<>() ;
        real.add(rocketLaunches.get(0).getLaunchVehicle());
        real.add(rocketLaunches.get(4).getLaunchVehicle());
        real.add(rocketLaunches.get(7).getLaunchVehicle());
        real.add(rocketLaunches.get(9).getLaunchVehicle());
        List<Rocket> rocket=miner.mostLaunchedRockets(k) ;
        assertEquals(k, rocket.size());
        assertEquals(real.subList(0,k), rocket);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldReturnTopMostReliableProviderList(int k) {
        when(dao.loadAll(Launch.class)).thenReturn(launchesTest);
        ArrayList<Launch> rocketLaunches = new ArrayList<>(launchesTest);
        List<LaunchServiceProvider> reliable=new ArrayList<>() ;
        reliable.add(lsps.get(1));
        reliable.add(lsps.get(0));
        reliable.add(lsps.get(2));
        List<LaunchServiceProvider> sort=miner.mostReliableLaunchServiceProviders(k);
        assertEquals(k,sort.size());
        assertEquals(reliable.subList(0,k),sort);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldReturnTopMostExpensiveLaunches(int k) {
        when(dao.loadAll(Launch.class)).thenReturn(launchesTest);
        ArrayList<Launch> rocketLaunches = new ArrayList<>(launchesTest);
        List<Launch> expensive=new ArrayList<>() ;
        expensive.add(launchesTest.get(2));
        expensive.add(launchesTest.get(3));
        expensive.add(launchesTest.get(4));
        expensive.add(launchesTest.get(1));
        expensive.add(launchesTest.get(0));
        List<Launch> sort=miner.mostExpensiveLaunches(k);
        assertEquals(k,sort.size());
        assertEquals(expensive.subList(0,k),sort);
    }



    @Test
    public void shouldReturnDominantCountrySendMostRocketsToOrbit() {
        when(dao.loadAll(Launch.class)).thenReturn(launchesTest);
//        when(dao.loadAll(LaunchServiceProvider.class)).thenReturn(lsps);
        String country=miner.dominantCountry("LEO");
        assertEquals("Australia",country);
    }




    @Test
    public void shouldThrowExceptionWhenPutNegativeValueInMostLaunchedRocketsMethod() {
        when(dao.loadAll(Launch.class)).thenReturn(launchesTest);
        Exception exception=assertThrows(IllegalArgumentException.class, ()-> miner.mostLaunchedRockets(-1));
        assertEquals("Cannot input negative value", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenPutValueBiggerThanTheSizeOfLaunchListInMostLaunchedRocketsMethod() {
        when(dao.loadAll(Launch.class)).thenReturn(launchesTest);
        Exception exception=assertThrows(IllegalArgumentException.class, ()-> miner.mostLaunchedRockets(launchesTest.size()+1));
        assertEquals("Do not have enough valid rockets", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings  = {"aa","@","1"})
    public void shouldThrowExceptionWhenPutInvalidOrbitToDominantCountryMethod(String input) {
        when(dao.loadAll(Launch.class)).thenReturn(launchesTest);
        Exception exception=assertThrows(IllegalArgumentException.class, ()-> miner.dominantCountry(input));
        assertEquals("Orbit do not exist", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenPutNullValueToDominantCountryMethod() {
        when(dao.loadAll(Launch.class)).thenReturn(launchesTest);
        Exception exception=assertThrows(NullPointerException.class, ()-> miner.dominantCountry(null));
        assertEquals("Orbit cannot be null or empty", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings  = {""," ","   "})
    public void shouldThrowExceptionWhenPutEmptyValueToDominantCountryMethod(String input) {
        when(dao.loadAll(Launch.class)).thenReturn(launchesTest);
        Exception exception=assertThrows(IllegalArgumentException.class, ()-> miner.dominantCountry(input));
        assertEquals("Orbit cannot be null or empty", exception.getMessage());
    }



    @ParameterizedTest
    @ValueSource(ints  = {-2,0})
    public void shouldThrowExceptionWhenPutInvalidKToMostExpensiveLaunches(int k) {
        when(dao.loadAll(Launch.class)).thenReturn(launchesTest);
        Exception exception=assertThrows(IllegalArgumentException.class, ()-> miner.mostExpensiveLaunches(k));
        assertEquals("Cannot input negative value", exception.getMessage());
    }


}
