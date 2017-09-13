
package parliament2profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "committees",
    "constituency",
    "list",
    "meta",
    "parliament",
    "party",
    "personal"
})
public class Profile {

    @JsonProperty("committees")
    private java.util.List<Object> committees = null;
    @JsonProperty("constituency")
    private Constituency constituency;
    @JsonProperty("list")
    private parliament2profile.List list;
    @JsonProperty("meta")
    private Meta meta;
    @JsonProperty("parliament")
    private Parliament parliament;
    /**
     * The party schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("party")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String party = "FDP";
    @JsonProperty("personal")
    private Personal personal;

    @JsonProperty("committees")
    public java.util.List<Object> getCommittees() {
        return committees;
    }

    @JsonProperty("committees")
    public void setCommittees(java.util.List<Object> committees) {
        this.committees = committees;
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

    @JsonProperty("meta")
    public Meta getMeta() {
        return meta;
    }

    @JsonProperty("meta")
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @JsonProperty("parliament")
    public Parliament getParliament() {
        return parliament;
    }

    @JsonProperty("parliament")
    public void setParliament(Parliament parliament) {
        this.parliament = parliament;
    }

    /**
     * The party schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("party")
    public String getParty() {
        return party;
    }

    /**
     * The party schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("party")
    public void setParty(String party) {
        this.party = party;
    }

    @JsonProperty("personal")
    public Personal getPersonal() {
        return personal;
    }

    @JsonProperty("personal")
    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

}
