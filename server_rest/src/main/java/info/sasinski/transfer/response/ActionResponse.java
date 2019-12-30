package info.sasinski.transfer.response;

import info.sasinski.entity.Customer;
import info.sasinski.entity.Employee;
import info.sasinski.entity.MotorcycleDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
public class ActionResponse {

    final MotorcycleDetails motorcycleDetails;
    final Employee employee;
    final Customer customer;
}
