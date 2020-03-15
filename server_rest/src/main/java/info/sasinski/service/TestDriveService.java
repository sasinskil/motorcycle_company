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
    final MotorcycleDetailsService _motorcycleDetailsService;

    public List<TestDrive> getAll() {
        return _testDriveRepository.findAll();
    }

    public long countTestDrives() { return _testDriveRepository.count(); }

    public void saveTestDrive(TestDrive testDrive) {

        boolean checkIsSold = testDrive.getMotorcycleDetails().getIsSold();

        if(!checkIsSold) {
            if(testDrive.getEndDrive() != null) {
                if(testDrive.getStartDrive().isBefore(testDrive.getEndDrive())) {
                    _testDriveRepository.save(testDrive);
                }
            } else {
                _testDriveRepository.save(testDrive);
            }
        }
    }

    public TestDrive getById(long id) {
        return _testDriveRepository
                .findById(id)
                .orElse(null);
    }

    public void removeTestDrive(long id) {
        _testDriveRepository.deleteById(id);
    }
}

