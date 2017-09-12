
package parliament2profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "uuid"
})
public class Parliament {

    /**
     * The name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String name = "Bremen";
    /**
     * The uuid schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("uuid")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String uuid = "1764827c-4a36-40f5-a6f0-5c368a40f217";

    /**
     * The name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The uuid schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }

    /**
     * The uuid schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("uuid")
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
