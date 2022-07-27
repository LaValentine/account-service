package lav.valentine.accountclient;

import org.springframework.web.client.RestTemplate;
import org.testng.annotations.*;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class AccountServiceTest {

    private final RestTemplate restTemplate = new RestTemplate();

    @DataProvider(name = "getting-amount")
    public Object[] listIdGettingAmount() {
        return read("rCount");
    }
    @DataProvider(name = "changing-amount")
    public Object[] listIdChangingAmount() {
        return read("wCount");
    }

    private Object[] read(String count) {
        Properties prop = new Properties();

        int readers;
        InputStream input = null;
        List<Integer> map = null;
        Object[] nums = null;

        try {
            input = getClass().getClassLoader().getResourceAsStream("application-test.properties");

            prop.load(input);

            readers = Integer.parseInt(prop.getProperty(count));
            map = Arrays.stream(prop.getProperty("base.module.elementToSearch").split(",")).map(Integer::parseInt).collect(Collectors.toList());

            while (map.size() <= readers) {
                map.addAll(map);
            }
            nums = Arrays.stream(map.stream().toArray()).limit(readers).toArray();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return nums;
    }

    @Test(dataProvider = "getting-amount")
    public void testGettingAmount(int number) {
        String url = String.format("http://localhost:8080/api/%d/amount/", number);
        String result = restTemplate.getForObject(url, String.class);
        System.out.println(result);
    }

    @Test(dataProvider = "changing-amount")
    public void testChangingAmount(int number) {
        Random random = new Random();
        long value = random.nextLong();
        String url = String.format("http://localhost:8080/api/%d/amount?value=%d", number, value);
        String result = restTemplate.postForObject(url, null,  String.class);
        System.out.println(result);
    }
}
