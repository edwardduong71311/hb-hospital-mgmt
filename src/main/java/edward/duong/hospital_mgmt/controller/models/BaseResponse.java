package edward.duong.hospital_mgmt.controller.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse {
    private Object data;
    private String error;
}
