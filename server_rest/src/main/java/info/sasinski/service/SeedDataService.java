package info.sasinski.service;

import info.sasinski.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j

@Service
public class SeedDataService {

    final CustomerRepository _customerRepository;
    final EmployeeRepository _employeeRepository;
    final MotorcycleDetailsRepository _motorcycleDetailsRepository;
    final TestDriveRepository _testDriveRepository;
    final TransactionRepository _transactionRepository;
    final ServiceRepository _serviceRepository;

    //TODO: implement data seed
    public void seed() {
//
//        List<Address> aList = new ArrayList<>();
//        List<Employee> eList = new ArrayList<>();
//        List<Customer> cList = new ArrayList<>();
//        Set<Motorcycle> mSet = new HashSet<>();
//
//        List<MotorcycleDetails> mDList = new ArrayList<>();
//        List<TestDrive> tDList = new ArrayList<>();
//        List<Transaction> tList = new ArrayList<>();
//        List<info.sasinski.entity.Service> sList = new ArrayList<>();
//
//        aList.add(new Address("Olsztyn", "65-152", "Włoskowa", 4));
//        aList.add(new Address("Krakow", "54-332", "Koralowa", 23));
//        aList.add(new Address("Warszawa", "23-333", "Polna", 44));
//        aList.add(new Address("Zarzecze", "34-325", "Jasna", 11));
//
//        Employee januszEmpl = createEmployee("Janusz", "Markowski", LocalDate.of(1993, Month.AUGUST, 19), new BigDecimal("5000.500"),
//                Position.DEALER, LocalDate.of(2013, Month.FEBRUARY, 20), null, aList.get(0));
//
//        Employee annaEmpl = createEmployee("Anna", "Kowalska", LocalDate.of(1982, Month.FEBRUARY, 4), new BigDecimal("2500.500"),
//                Position.MECHANIC, LocalDate.of(2005, Month.APRIL, 10), LocalDate.of(2008, Month.APRIL, 10), aList.get(1));
//
//        eList.add(januszEmpl);
//        eList.add(annaEmpl);
//
//        cList.add(new Customer("Dominik", "Wyrwak", LocalDate.of(1993, Month.FEBRUARY, 11), aList.get(2)));
//        cList.add(new Customer("Ania", "Szpak", LocalDate.of(1994, Month.MARCH, 1), aList.get(3)));
//
//        mSet.add(new Motorcycle(Classification.Supersport, "Suzuki", "Hayabusa GSXR 1300", "4 cylinder engine", new BigDecimal("1300.145"), 200, new BigDecimal("226.21")));
//        mSet.add(new Motorcycle(Classification.Sport_Adventure_Tourer, "Suzuki", "V-Strom 1000", "4 cylinder engine", new BigDecimal("1037.145"), 150, new BigDecimal("160.20")));
//
//
//        mDList.add(new MotorcycleDetails(new BigDecimal("45000.900"), getMotorcycle(mSet, 0), null, null));
//        mDList.add(new MotorcycleDetails(new BigDecimal("30000.550"), getMotorcycle(mSet, 1), null, null));
//
//        tDList.add(new TestDrive(cList.get(0), eList.get(0), mDList.get(0), LocalDateTime.of(2018, Month.OCTOBER, 10, 14, 10), LocalDateTime.of(2018, Month.OCTOBER, 10, 16, 10)));
//        tDList.add(new TestDrive(cList.get(1), eList.get(1), mDList.get(1), LocalDateTime.of(2018, Month.OCTOBER, 11, 13, 10), LocalDateTime.of(2018, Month.OCTOBER, 11, 14, 40)));
//
//
//        tList.add(new Transaction("Purchase", new BigDecimal("50000.300"), cList.get(1), eList.get(1), mDList.get(1),
//                LocalDateTime.of(2014, Month.APRIL, 23, 14, 24)));
//        tList.add(new Transaction("Purchase", new BigDecimal("40000.100"), cList.get(0), eList.get(0), mDList.get(0),
//                LocalDateTime.of(2010, Month.OCTOBER, 20, 10, 45)));
//
//
//        sList.add(new info.sasinski.entity.Service("review", new BigDecimal("200.00"), LocalDate.of(2017, Month.JULY, 11),
//                LocalDate.of(2017, Month.JULY, 12), cList.get(0), eList.get(1), mDList.get(1)));
//        sList.add(new info.sasinski.entity.Service("review", new BigDecimal("200.00"), LocalDate.of(2014, Month.MAY, 21),
//                LocalDate.of(2014, Month.MAY, 21), cList.get(1), eList.get(0), mDList.get(0)));
//
//        log.info("======= Save some data to database =======");
//
//
//        for (Employee e : eList) {
//            employeeRepository.save(e);
//        }
//        for (Customer c : cList) {
//            customerRepository.save(c);
//        }
//        for (MotorcycleDetails m : mDList) {
//            motorcycleDetailsRepository.save(m);
//        }
//        for (TestDrive tD : tDList) {
//            testDriveRepository.save(tD);
//        }
//        for (Transaction t : tList) {
//            transactionRepository.save(t);
//        }
//        for (info.sasinski.entity.Service s : sList) {
//            serviceRepository.save(s);
//        }
//
//        List<Employee> empLst = employeeRepository.findAll();
//        logger.info("======= Unsorted list =======");
//        printAll(empLst);
//
//        empLst.sort(Comparator.comparing(Employee::getFirstName));
//        logger.info("======= Sorted list =======");
//        printAll(empLst);
//
//        logger.info("======= Tests =======");
//        List<Employee> ignoreCaseList = employeeRepository.findByFirstNameIgnoreCase("anna");
//        printAll(ignoreCaseList);
//
//        List<Employee> byLastNameList = employeeRepository.findByLastNameOrderByFirstName("Rybowska");
//        printAll(byLastNameList);
//
//        List<Employee> salaryBetween = employeeRepository.findBySalaryBetween(new BigDecimal("1000.000"), new BigDecimal("3000.000"));
//        printAll(salaryBetween);
//
//        List<Employee> allByDateOfEmployment = employeeRepository.findAllByDateOfEmployment(LocalDate.of(2013, Month.FEBRUARY, 20));
//        printAll(allByDateOfEmployment);
//
//        Employee e = employeeRepository.findFirstByLastName("Rybowska");
//        System.out.println(e);
//
//        List<Employee> top3ByLastName = employeeRepository.findTop3ByLastName("Rybowska");
//        printAll(top3ByLastName);
//
//        int countByLastAndFirstName = employeeRepository.countByFirstNameAndLastNameIgnoreCase("Anna", "Rybowska");
//        System.out.println(countByLastAndFirstName);
//
//        Page<Employee> AnnaPage = employeeRepository.findByLastName("Rybowska", new PageRequest(1, 2));
//        printAll(AnnaPage.getContent());
//
//        System.out.println(findTop3StreamUse(Position.MECHANIC));
//
//        List<MotorcycleDetails> MdWithHighestPrice = motorcycleDetailsRepository.findMotorcycleWithHighestPrice();
//        for (MotorcycleDetails md : MdWithHighestPrice) {
//            System.out.println(md.getPrice());
//        }
//
//        List<MotorcycleDetails> powerGreaterThan = motorcycleDetailsRepository.powerGreaterThan(150);
//        for (MotorcycleDetails md : powerGreaterThan) {
//            System.out.println(md.getMotorcycle().getModel());
//        }
//
//        int numberOfUpdates = customerRepository.setLastNameforCustomer("Wykwak", "Wykwar");
//        System.out.println(numberOfUpdates);
//
//
//        log.info("======= Refresh data =======");
//
//
//        for (Address a : aList) {
//
//            em.refresh(a);
//        }
//
//        for (Customer c : cList) {
//            customerRepository.refresh(c);
//
//        }
//        for (Employee e : eList) {
//            employeeRepository.refresh(e);
//        }

    }

//    private void printAll(List<Employee> unsorted) {
////        unsorted.forEach(System.out::println);
//    }
//
////    public List<String> findTop3StreamUse(Position position) {
////        List<Employee> items = employeeRepository.findTop3ByPosition(position);
////        return items.stream().map(Employee::getFirstName).collect(Collectors.toList());
////    }
//
//    private Employee createEmployee(
//            String firstName, String lastName, LocalDate birthDate,
//            BigDecimal salary, Position position, LocalDate dateOfEmployment,
//            LocalDate dateOfTermination, Address address) {
//        Employee employee = new Employee();
//        employee.setDateOfEmployment(dateOfEmployment);
//        employee.setDateOfTermination(dateOfTermination);
//        employee.setPosition(position);
//        employee.setSalary(salary);
//        employee.setAddress(address);
//        employee.setFirstName(firstName);
//        employee.setLastName(lastName);
//        employee.setBirthDate(birthDate);
//        return _employeeRepository.save(employee);
//    }
}
