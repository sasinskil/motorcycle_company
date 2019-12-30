package info.sasinski.transfer.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConstraintViolationsResponse {

    String statusCode;
    String errorContent;
    List<String> errorMsg;
}
