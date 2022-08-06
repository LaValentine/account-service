package lav.valentine.accounttestclient;

import lav.valentine.accounttestclient.domain.RequestMethod;
import lav.valentine.accounttestclient.domain.Settings;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.stream.Stream;

public class AccountTestClientApplication {

    public static void main(String[] args) throws JAXBException {
        Settings settings = SettingsConfig.readFromFile("test-client-settings.xml");

        int idListSize = settings.getIdList().size();

        Stream.concat(
                generateRequests(settings.getIdList(), idListSize, RequestMethod.GET_AMOUNT)
                        .limit(settings.getrCount()),
                generateRequests(settings.getIdList(), idListSize, RequestMethod.ADD_AMOUNT)
                        .limit(settings.getwCount())
        ).map(Thread::new).forEach(thread -> {
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public static Stream<Request> generateRequests(List<Integer> idList, Integer idListSize, RequestMethod method) {
        return Stream.generate(() -> new Request(
                idList.get((int) (Math.random() * idListSize)), method));
    }
}