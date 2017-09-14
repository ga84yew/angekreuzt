
package parliament2profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "birthyear",
    "degree",
    "education",
    "email",
    "first_name",
    "simplefirst_name",
    "gender",
    "last_name",
    "simplelast_name",
    "simplefull_name",
    "location",
    "picture",
    "profession",
    "twitter"
})
public class Personal {

    /**
     * The birthyear schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("birthyear")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String birthyear = "";
    /**
     * The degree schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("degree")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String degree = "Dr. Dr.";
    /**
     * The education schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("education")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private Object education = null;
    /**
     * The email schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("email")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private Object email = null;
    /**
     * The first_name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("first_name")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String firstName = "";
    
    /**
     * The simplefirst_name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("simplefirst_name")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String simpleFirstName = "";    
    
    /**
     * The simplelast_name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("simplelast_name")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String simpleLastName = "";    
       
    /**
     * The simplefull_name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("simplefull_name")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String simpleFullName = "";   
    
    /**
     * The gender schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("gender")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String gender = "male";
    /**
     * The last_name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("last_name")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String lastName = "";
    @JsonProperty("location")
    private Location location;
    @JsonProperty("picture")
    private Picture picture;
    /**
     * The profession schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("profession")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String profession = "Baudirektor";
    /**
     * The twitter schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("twitter")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private Object twitter = null;

    /**
     * The birthyear schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("birthyear")
    public String getBirthyear() {
        return birthyear;
    }

    /**
     * The birthyear schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("birthyear")
    public void setBirthyear(String birthyear) {
        this.birthyear = birthyear;
    }

    /**
     * The degree schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("degree")
    public String getDegree() {
        return degree;
    }

    /**
     * The degree schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("degree")
    public void setDegree(String degree) {
        this.degree = degree;
    }

    /**
     * The education schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("education")
    public Object getEducation() {
        return education;
    }

    /**
     * The education schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("education")
    public void setEducation(Object education) {
        this.education = education;
    }

    /**
     * The email schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("email")
    public Object getEmail() {
        return email;
    }

    /**
     * The email schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("email")
    public void setEmail(Object email) {
        this.email = email;
    }

    /**
     * The first_name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    /**
     * The first_name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * The simplefirst_name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("simpleFirst_name")
    public String getSimpleFirstName() {
        return this.simpleFirstName;
    }
    
    /**
     * The simplefirst_name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("simpleFirst_name")
    public void setSimpleFirstName(String firstName) {
        this.simpleFirstName = firstName;
    }
    /**
     * The simplelast_name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("simpleLast_name")
    public void setSimpleLastName(String Name) {
        this.simpleLastName = Name;
    }
    /**
     * The simplelast_name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("simpleLast_name")
    public String getSimpleLastName() {
        return simpleLastName;
    }
    /**
     * The simplefull_name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("simplefull_name")
    public String getSimpleFullName() {
        return simpleFullName;
    }
    /**
     * The simplefull_name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("simplefull_name")
    public void setSimpleFullName(String Name) {
        this.simpleFullName = Name;
    }


    /**
     * The gender schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    /**
     * The gender schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * The last_name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    /**
     * The last_name schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    /**
     * The profession schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("profession")
    public String getProfession() {
        return profession;
    }

    /**
     * The profession schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("profession")
    public void setProfession(String profession) {
        this.profession = profession;
    }

    /**
     * The twitter schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("twitter")
    public Object getTwitter() {
        return twitter;
    }

    /**
     * The twitter schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("twitter")
    public void setTwitter(Object twitter) {
        this.twitter = twitter;
    }

}
