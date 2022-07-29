package lav.valentine.accountclient;

import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;

import java.util.List;

public class AccountClientApplication {

    public static void main(String[] args) throws Exception{
        final TestNG testNG = new TestNG(true);
        final Parser parser = new Parser("test-client.xml");
        final List<XmlSuite> suites = parser.parseToList();
        testNG.setXmlSuites(suites);
        testNG.run();
    }
}
