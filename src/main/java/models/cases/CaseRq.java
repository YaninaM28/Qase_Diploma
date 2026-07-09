package models.cases;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaseRq {

    @SerializedName("step_type")
    @Expose
    private String steps_type;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("preconditions")
    @Expose
    private String preconditions;
    @SerializedName("postconditions")
    @Expose
    private String postconditions;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("severity")
    @Expose
    private int severity;
    @SerializedName("priority")
    @Expose
    private int priority;
    @SerializedName("behavior")
    @Expose
    private int behavior;
    @SerializedName("type")
    @Expose
    private int type;
    @SerializedName("layer")
    @Expose
    private int layer;
    @SerializedName("is_flaky")
    @Expose
    private int is_flaky;
    @SerializedName("isManual")
    @Expose
    private int isManual;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("steps")
    @Expose
    private List<Step> steps;
}
