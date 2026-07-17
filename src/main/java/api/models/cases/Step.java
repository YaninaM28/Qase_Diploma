package api.models.cases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Step {
    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("expected_result")
    @Expose
    private String expected_result;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("value")
    @Expose
    private String value;
}
