package lav.valentine.accountclient;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.MatchesPattern.matchesPattern;

public class AccountServiceTest {

    private final RestTemplate restTemplate = new RestTemplate();

    private String hostname;
    private String port;

    private int wCount;
    private int rCount;
    private List<Integer> idList;

    @Parameters({"wCount", "rCount", "idList", "hostname", "port"})
    @BeforeSuite
    public void setUp (int wCount, int rCount, String idList, String hostname, String port) {

        this.hostname = hostname;
        this.port = port;

        this.wCount = wCount;
        this.rCount = rCount;
        this.idList = Arrays.stream(idList.split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }

    @DataProvider(name = "getting-amount")
    public Object[] listIdGettingAmount() {
        return read(rCount);
    }
    @DataProvider(name = "changing-amount")
    public Object[] listIdChangingAmount() {
        return read(wCount);
    }

    private Object[] read(int readers) {
        List<Integer> list = new ArrayList<>();

        while (list.size() <= readers) {
            list.addAll(idList);
        }
        return list.stream().limit(readers).toArray();
    }

    @Test(dataProvider = "getting-amount")
    public void testGettingAmount(int number) {
        String url = String.format("http://%s:%s/api/%d/amount/", hostname, port, number);
        restTemplate.getForObject(url, String.class);
    }

    @Test(dataProvider = "changing-amount")
    public void testChangingAmount(int number) {
        Random random = new Random();
        long value = random.nextLong();
        String url = String.format("http://%s:%s/api/%d/amount?value=%d", hostname, port, number, value);
        try {
            restTemplate.postForObject(url, null, String.class);
        }
        catch (HttpClientErrorException ex) {
            assertThat(ex.getMessage(), matchesPattern("400.+"));
        }
    }
}