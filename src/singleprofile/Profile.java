
package singleprofile;

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
    "committees",
    "votes",
    "questions"
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
    private singleprofile.List list;
    @JsonProperty("committees")
    private java.util.List<Object> committees = null;
    @JsonProperty("votes")
    private java.util.List<Object> votes = null;
    @JsonProperty("questions")
    private java.util.List<Question> questions = null;
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
    public singleprofile.List getList() {
        return list;
    }

    @JsonProperty("list")
    public void setList(singleprofile.List list) {
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

    @JsonProperty("votes")
    public java.util.List<Object> getVotes() {
        return votes;
    }

    @JsonProperty("votes")
    public void setVotes(java.util.List<Object> votes) {
        this.votes = votes;
    }

    @JsonProperty("questions")
    public java.util.List<Question> getQuestions() {
        return questions;
    }

    @JsonProperty("questions")
    public void setQuestions(java.util.List<Question> questions) {
        this.questions = questions;
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
