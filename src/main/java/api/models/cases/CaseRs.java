package api.models.cases;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaseRs {

    @SerializedName("status")
    @Expose
    public boolean status;
    @SerializedName("result")
    @Expose
    public Result result;
}

