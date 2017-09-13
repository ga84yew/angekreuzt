
package parliament2profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "answers",
    "edited",
    "questions",
    "status",
    "url",
    "username",
    "uuid"
})
public class Meta {

    /**
     * The answers schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("answers")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private Integer answers = 1;
    /**
     * The edited schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("edited")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String edited = "2015-04-20 07:51";
    /**
     * The questions schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("questions")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private Integer questions = 1;
    /**
     * The status schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String status = "1";
    /**
     * The url schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("url")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String url = "https://www.abgeordnetenwatch.de/profile/dr-dr-magnus-buhlert";
    /**
     * The username schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("username")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String username = "dr-dr-magnus-buhlert";
    /**
     * The uuid schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("uuid")
    @JsonPropertyDescription("An explanation about the purpose of this instance.")
    private String uuid = "624a1c1a-283c-4b0b-880f-76318f5134bb";

    /**
     * The answers schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("answers")
    public Integer getAnswers() {
        return answers;
    }

    /**
     * The answers schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("answers")
    public void setAnswers(Integer answers) {
        this.answers = answers;
    }

    /**
     * The edited schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("edited")
    public String getEdited() {
        return edited;
    }

    /**
     * The edited schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("edited")
    public void setEdited(String edited) {
        this.edited = edited;
    }

    /**
     * The questions schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("questions")
    public Integer getQuestions() {
        return questions;
    }

    /**
     * The questions schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("questions")
    public void setQuestions(Integer questions) {
        this.questions = questions;
    }

    /**
     * The status schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * The status schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
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

    /**
     * The username schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    /**
     * The username schema
     * <p>
     * An explanation about the purpose of this instance.
     * 
     */
    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
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

}
