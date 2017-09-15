
package parliament2profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "number",
    "result",
    "uuid",
    "won"
})
public class Constituency {

    /**
     * The name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String name = "Bremen-Neustadt";
    /**
     * The number schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("number")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String number = "11";
    /**
     * The result schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("result")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private Object result = null;
    /**
     * The uuid schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("uuid")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String uuid = "12eacf1d-4528-46d3-a833-e9c02d70b876";
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
     *@return name 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The name schema
     * <p>
     * An explanation about the purpose of this instance.
     * @param  name input name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The number schema
     * <p>
     * An explanation about the purpose of this instance.
     * @return number
     */
    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    /**
     * The number schema
     * <p>
     * An explanation about the purpose of this instance.
     * @param  number input number
     */
    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * The result schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("result")
    public Object getResult() {
        return result;
    }

    /**
     * The result schema
     * <p>
     * An explanation about the purpose of this instance.
     * no @param result input result
     */
    @JsonProperty("result")
    public void setResult(Object result) {
        this.result = result;
    }

    /**
     * The uuid schema
     * <p>
     * An explanation about the purpose of this instance.
     * return uuid
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
     * @return won
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
