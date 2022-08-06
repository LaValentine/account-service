package lav.valentine.accountclient;

import org.hamcrest.MatcherAssert;
import org.hamcrest.text.MatchesPattern;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
        List<Integer> list = new ArrayList<>(idList);

        while (list.size() <= readers) {
            list.addAll(list);
        }
        return list.stream().limit(readers).toArray();
    }

    @Test(dataProvider = "getting-amount")
    public void testGettingAmount(int number) {
        String url = String.format("http://%s:%s/api/%d/amount/", hostname, port, number);
        try {
            restTemplate.getForObject(url, String.class);
        }
        catch (HttpClientErrorException ex) {
            MatcherAssert.assertThat(ex.getMessage(), MatchesPattern.matchesPattern("400.+"));
        }
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
            MatcherAssert.assertThat(ex.getMessage(), MatchesPattern.matchesPattern("400.+"));
        }
    }
}