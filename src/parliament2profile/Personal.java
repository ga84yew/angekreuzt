
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
    "degree",
    "first_name",
    "last_name",
    "gender",
    "birthyear",
    "education",
    "profession",
    "email",
    "twitter",
    "location",
    "picture"
})
public class Personal {

    @JsonProperty("degree")
    private Object degree;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("birthyear")
    private String birthyear;
    @JsonProperty("education")
    private String education;
    @JsonProperty("profession")
    private Object profession;
    @JsonProperty("email")
    private Object email;
    @JsonProperty("twitter")
    private Object twitter;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("picture")
    private Picture picture;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("degree")
    public Object getDegree() {
        return degree;
    }

    @JsonProperty("degree")
    public void setDegree(Object degree) {
        this.degree = degree;
    }

    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("birthyear")
    public String getBirthyear() {
        return birthyear;
    }

    @JsonProperty("birthyear")
    public void setBirthyear(String birthyear) {
        this.birthyear = birthyear;
    }

    @JsonProperty("education")
    public String getEducation() {
        return education;
    }

    @JsonProperty("education")
    public void setEducation(String education) {
        this.education = education;
    }

    @JsonProperty("profession")
    public Object getProfession() {
        return profession;
    }

    @JsonProperty("profession")
    public void setProfession(Object profession) {
        this.profession = profession;
    }

    @JsonProperty("email")
    public Object getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(Object email) {
        this.email = email;
    }

    @JsonProperty("twitter")
    public Object getTwitter() {
        return twitter;
    }

    @JsonProperty("twitter")
    public void setTwitter(Object twitter) {
        this.twitter = twitter;
    }

    @JsonProperty("location")
    public Location getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(Location location) {
        this.location = location;
    }

    @JsonProperty("picture")
    public Picture getPicture() {
        return picture;
    }

    @JsonProperty("picture")
    public void setPicture(Picture picture) {
        this.picture = picture;
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
