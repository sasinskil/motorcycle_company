package info.sasinski.validation;

import info.sasinski.entity.Address;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.text.ParseException;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressValidationTest {

    private static ValidatorFactory vf;
    private static Validator validator;
    private Set<ConstraintViolation<Address>> violations;


    @BeforeClass
    public static void init() throws ParseException
    {
        vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();

    }

    @Test
    public void withoutConstraintViolations() {

        Address address = new Address("Olsztyn", "65-152", "Włoskowa", 4);

        violations = validator.validate(address);
        if(violations.size() > 0)
        {
            for(ConstraintViolation violation : violations)
            {
                System.out.println("### " +
                        violation.getRootBeanClass().getSimpleName() +
                        "." + violation.getPropertyPath() +
                        " - Invalid value = " + violation.getInvalidValue() +
                        " - Error msg = " + violation.getMessage());
            }
        }

        assertEquals(0, violations.size());
    }
    @Test
    public void withConstraintViolations() {

        Address address = new Address("Ol", "6-152", "Włoskowa", 4);

        violations = validator.validate(address);
        if(violations.size() > 0)
        {
            for(ConstraintViolation violation : violations)
            {
                System.out.println("### " +
                        violation.getRootBeanClass().getSimpleName() +
                        "." + violation.getPropertyPath() +
                        " - Invalid value = " + violation.getInvalidValue() +
                        " - Error msg = " + violation.getMessage());
            }
        }

        assertEquals(2, violations.size());
    }

    @AfterClass
    public static void close()
    {
        vf.close();
    }
}
