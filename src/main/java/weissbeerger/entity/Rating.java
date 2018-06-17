package weissbeerger.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class Rating implements Serializable {

    private String Source;
    private String Value;

    public String getSource() {
        return Source;
    }
    @XmlElement
    public void setSource(String source) {
        this.Source = source;
    }

    public String getValue() {
        return Value;
    }
    @XmlElement
    public void setValue(String value) {
        this.Value = value;
    }
}
