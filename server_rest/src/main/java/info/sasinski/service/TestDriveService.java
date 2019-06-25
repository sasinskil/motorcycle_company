package info.sasinski.service;

import info.sasinski.entity.TestDrive;

import java.util.List;

public interface TestDriveService {

    List<TestDrive> getAllTD();

    void saveTestDrive(TestDrive testDrive);
}
