const APIUrl = "http://localhost:9090/api";

// Auth

const loginUrl = `${APIUrl}/auth/signin`;
const registerUrl = `${APIUrl}/auth/signup`;


// Customer

const customerUrl = `${APIUrl}/customer`;

// Employee

const employeeUrl = `${APIUrl}/employee`;

// Motorcycle

const motorcycleUrl = `${APIUrl}/motorcycle`;
const motorcycleDetailsUrl = `${APIUrl}/motorcycleDetails`;
     // sold
const motorcycleDetailsSoldTrueUrl = `${motorcycleDetailsUrl}/soldTrue`;
const motorcycleDetailsSoldFalseUrl = `${motorcycleDetailsUrl}/soldFalse`;

//Test drive

const testDriveUrl = `${APIUrl}/testDrive`;

// Transaction

const transactionUrl = `${APIUrl}/transaction`;

// Service

const serviceUrl = `${APIUrl}/service`;

// Count

const countCustomers = `${customerUrl}/count`;
const countEmployees = `${employeeUrl}/count`;
const countMotorcycleDetails = `${motorcycleDetailsUrl}/count`;
const countTestDrives = `${testDriveUrl}/count`;
const countTransactions = `${transactionUrl}/count`;
const countServices = `${serviceUrl}/count`;


export {
     loginUrl, registerUrl, customerUrl, employeeUrl,
     motorcycleUrl, motorcycleDetailsUrl, motorcycleDetailsSoldTrueUrl, motorcycleDetailsSoldFalseUrl, testDriveUrl,
     transactionUrl, serviceUrl,
     countCustomers, countEmployees, countMotorcycleDetails, countTestDrives,
     countTransactions, countServices
    };