

package rockets.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.Validate.notBlank;

public class Payload extends Entity{
    private Long capacity;
    private String payloadType;
    public Long getCapacity() {
        return capacity;
    }

    public String getPayloadType() {
        return payloadType;
    }

    public void setCapacity(Long capacity) {
        if (capacity<0)
        {
            throw new IllegalArgumentException("Capacity cannot be negative value");
        }
        this.capacity = capacity;
    }

    public void setPayloadType(String payloadType) {
        notBlank(payloadType, "PayloadType cannot be null or empty");
        String regEx = "^[a-zA-Z]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(payloadType);
        if (!m.matches()) {
            throw new IllegalArgumentException("Please using alphabetic characters");
        }
        this.payloadType = payloadType;
    }
}
