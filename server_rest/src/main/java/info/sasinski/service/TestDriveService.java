package info.sasinski.service;

import info.sasinski.entity.TestDrive;
import info.sasinski.repository.TestDriveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class TestDriveService {

    final TestDriveRepository _testDriveRepository;

    public List<TestDrive> getAll() {
        return _testDriveRepository.findAll();
    }

    public long countTestDrives() { return _testDriveRepository.count(); }

    public void saveTestDrive(TestDrive testDrive) {
        _testDriveRepository.save(testDrive);
    }

    public TestDrive getById(long id) {
        return _testDriveRepository
                .findById(id)
                .orElse(null);
    }
}

