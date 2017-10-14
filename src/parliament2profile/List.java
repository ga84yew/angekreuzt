
package parliament2profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "position",
    "uuid",
    "won"
})
public class List {

    /**
     * The name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String name = "Wahlbereich Bremen";
    /**
     * The position schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("position")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String position = "2";
    /**
     * The uuid schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("uuid")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String uuid = "e5781950-2282-4a56-b566-ca238cd52529";
    /**
     * The won schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("won")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private Boolean won = false;

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
     * The position schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("position")
    public String getPosition() {
        return position;
    }

    /**
     * The position schema
     * <p>
     * An explanation about the purpose of this instance.
     * @param position input position
     */
    @JsonProperty("position")
    public void setPosition(String position) {
        this.position = position;
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

    /**
     * The won schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("won")
    public Boolean getWon() {
        return won;
    }

    /**
     * The won schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("won")
    public void setWon(Boolean won) {
        this.won = won;
    }

}
