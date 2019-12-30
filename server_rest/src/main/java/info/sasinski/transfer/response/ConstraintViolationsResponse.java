package info.sasinski.transfer.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
public class ConstraintViolationsResponse {

    final String statusCode;
    final String errorContent;
    final List<String> errorMsg;
}
