
package parliament2profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "copyright",
    "url"
})
public class Picture {

    /**
     * The copyright schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("copyright")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String copyright = "";
    /**
     * The url schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("url")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String url = "https://www.abgeordnetenwatch.de/sites/abgeordnetenwatch.de/files/users/f01a6977.jpg";

    /**
     * The copyright schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("copyright")
    public String getCopyright() {
        return copyright;
    }

    /**
     * The copyright schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("copyright")
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /**
     * The url schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * The url schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

}
