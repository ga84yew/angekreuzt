
package parliament2profile;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "meta",
    "personal",
    "party",
    "parliament",
    "constituency",
    "list",
    "committees"
})
public class Profile {

    @JsonProperty("meta")
    private Meta meta;
    @JsonProperty("personal")
    private Personal personal;
    @JsonProperty("party")
    private String party;
    @JsonProperty("parliament")
    private Parliament parliament;
    @JsonProperty("constituency")
    private Constituency constituency;
    @JsonProperty("list")
    private parliament2profile.List list;
    @JsonProperty("committees")
    private java.util.List<Object> committees = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("meta")
    public Meta getMeta() {
        return meta;
    }

    @JsonProperty("meta")
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @JsonProperty("personal")
    public Personal getPersonal() {
        return personal;
    }

    @JsonProperty("personal")
    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    @JsonProperty("party")
    public String getParty() {
        return party;
    }

    @JsonProperty("party")
    public void setParty(String party) {
        this.party = party;
    }

    @JsonProperty("parliament")
    public Parliament getParliament() {
        return parliament;
    }

    @JsonProperty("parliament")
    public void setParliament(Parliament parliament) {
        this.parliament = parliament;
    }

    @JsonProperty("constituency")
    public Constituency getConstituency() {
        return constituency;
    }

    @JsonProperty("constituency")
    public void setConstituency(Constituency constituency) {
        this.constituency = constituency;
    }

    @JsonProperty("list")
    public parliament2profile.List getList() {
        return list;
    }

    @JsonProperty("list")
    public void setList(parliament2profile.List list) {
        this.list = list;
    }

    @JsonProperty("committees")
    public java.util.List<Object> getCommittees() {
        return committees;
    }

    @JsonProperty("committees")
    public void setCommittees(java.util.List<Object> committees) {
        this.committees = committees;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
