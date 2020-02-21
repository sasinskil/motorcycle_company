const APIUrl = "https://localhost:9090/api";

// Auth

const loginUrl = `${APIUrl}/user/signin`;
const registerUrl = `${APIUrl}/user/signup`;


// Customer

const customerUrl = `${APIUrl}/customer`;

// Employee

const employeeUrl = `${APIUrl}/employee`;

// Motorcycle

const motorcycleUrl = `${APIUrl}/motorcycle`;
const motorcycleDetailsUrl = `${APIUrl}/motorcycleDetails`;

//Test drive

const testDriveUrl = `${APIUrl}/testDrive`;

// Transaction

const transactionUrl = `${APIUrl}/transaction`;

// Service

const serviceUrl = `${APIUrl}/service`;



export {
     loginUrl, registerUrl, customerUrl, employeeUrl,
     motorcycleUrl, motorcycleDetailsUrl, testDriveUrl,
     transactionUrl, serviceUrl 
    };