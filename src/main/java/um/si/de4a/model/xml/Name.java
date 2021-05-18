
package xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "name", namespace = "http://data.europa.eu/europass/model/credentials#", propOrder = {
    "text"
})
public class Name {

    @XmlElement(namespace = "http://data.europa.eu/europass/model/credentials#", required = true)
    protected Text text;


    public Text getText() {
        return text;
    }


    public void setText(Text value) {
        this.text = value;
    }

}
