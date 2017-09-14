
package parliament2profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "uuid",
    "joined"
})
public class Parliament {

    /**
     * The name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
	 @JsonProperty("joined")
	    @JsonPropertyDescription("An explanation about the purpose of this instance.")
	    private String joined = "";
	    /**
	     * The uuid schema
	     * <p>
	     * An explanation about the purpose of this instance.
	     * 
	     */
	   
    @JsonProperty("name")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String name = "";
    /**
     * The uuid schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("uuid")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String uuid = "";

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
