
package parliament2profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "city",
    "country",
    "county",
    "postal_code"
})
public class Location {

    /**
     * The city schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("city")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String city = "Bremen-Neustadt";
    /**
     * The country schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("country")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String country = "DE";
    /**
     * The county schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("county")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String county = "";
    /**
     * The postal_code schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("postal_code")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String postalCode = "28201";

    /**
     * The city schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    /**
     * The city schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * The country schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    /**
     * The country schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * The county schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("county")
    public String getCounty() {
        return county;
    }

    /**
     * The county schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("county")
    public void setCounty(String county) {
        this.county = county;
    }

    /**
     * The postal_code schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("postal_code")
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * The postal_code schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("postal_code")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}
