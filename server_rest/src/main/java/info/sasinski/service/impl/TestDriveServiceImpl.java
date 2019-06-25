package info.sasinski.service.impl;

import info.sasinski.entity.TestDrive;
import info.sasinski.repository.TestDriveRepository;
import info.sasinski.service.TestDriveService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestDriveServiceImpl implements TestDriveService {

    private final TestDriveRepository testDriveRepository;

    public TestDriveServiceImpl(TestDriveRepository testDriveRepository) {
        this.testDriveRepository = testDriveRepository;
    }

    @Override
    public List<TestDrive> getAllTD() {
        return testDriveRepository.getAllTD();
    }

    @Override
    public void saveTestDrive(TestDrive testDrive) {
        testDriveRepository.save(testDrive);
    }
}
