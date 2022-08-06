package lav.valentine.accounttestclient.domain;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "Settings")
public class Settings {
    @XmlElement(name = "rCount")
    Integer rCount;
    @XmlElement(name = "wCount")
    Integer wCount;
    @XmlElementWrapper(name = "idList")
    @XmlElement(name = "id")
    List<Integer> idList;

    public Settings(Integer rCount, Integer wCount, List<Integer> idList) {
        this.rCount = rCount;
        this.wCount = wCount;
        this.idList = idList;
    }

    public Settings() {
    }

    public Integer getrCount() {
        return rCount;
    }

    public Integer getwCount() {
        return wCount;
    }

    public List<Integer> getIdList() {
        return idList;
    }
}
