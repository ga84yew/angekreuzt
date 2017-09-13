
package parliament2profile;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "contributer",
    "profiles"
})
public class Parliament2Profile {

    @JsonProperty("contributer")
    private List<Object> contributer = null;
    @JsonProperty("profiles")
    private List<Profile> profiles = null;

    @JsonProperty("contributer")
    public List<Object> getContributer() {
        return contributer;
    }

    @JsonProperty("contributer")
    public void setContributer(List<Object> contributer) {
        this.contributer = contributer;
    }

    @JsonProperty("profiles")
    public List<Profile> getProfiles() {
        return profiles;
    }

    @JsonProperty("profiles")
    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

}
