package lav.valentine.accounttestclient;

import lav.valentine.accounttestclient.domain.Settings;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class SettingsConfig {

    public static Settings readFromFile(String filename) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Settings.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return (Settings) unmarshaller.unmarshal(new File(filename));
    }
}
