<template>
  <div id="addTestDrive" class="main-container--add">
     <h1 class="main-container--add__title">Dodaj nową jazdę testową</h1>

     <button class="main-container--add__arrow" @click="stepBack">
      <font-awesome-icon class="plus-icon icon" icon="arrow-left" />
    </button>
     
     <form v-if="!submitted" class="form">
      <h2 class="form__title--main">Dane szczegółowe</h2>
      <p class="form__wrapper">
        <label class="form__label" for="startDate">Data rozpoczęcia:</label>
        <input class="form__input date-input" type="datetime-local" name="startDate" id="startDate" required v-model.lazy="testDrive.startDrive" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="endDate">Data zakończenia:</label>
        <input class="form__input date-input" type="datetime-local" name="endDate" id="endDate" required v-model.lazy="testDrive.endDrive" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="customer">Klient:</label> 
        <select class="form__input" name="customer" id="customer" required v-model="testDrive.customer">
          <option v-for="(singleCustomer, idx) in customers" :key="idx" :value="singleCustomer">{{singleCustomer.firstName}} - {{singleCustomer.lastName}}</option>
        </select>
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="employee">Pracownik:</label> 
        <select class="form__input" name="employee" id="employee" required v-model="testDrive.employee">
          <option v-for="(singleEmployee, idx) in employees" :key="idx" :value="singleEmployee">{{singleEmployee.firstName}} - {{singleEmployee.position}}</option>
        </select>
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="motorcycle">Motocykl:</label> 
        <select class="form__input" name="motorcycle" id="motorcycle" required v-model="testDrive.motorcycleDetails">
          <option v-for="(singleMotorcycle, idx) in motorcycles" :key="idx" :value="singleMotorcycle">{{singleMotorcycle.motorcycleCode}} - {{singleMotorcycle.motorcycle.model}}</option>
        </select>
      </p>
      <p class="form__errors" v-if="errors.length">
        <span>Popraw następujące błędy:</span>
        <ul>
          <li v-for="error in errors" :key="error">
            {{error}}
          </li>
        </ul>
      </p>
        <button class="form__send-button" @click.prevent="post"><font-awesome-icon class="plus-icon icon" icon="plus" />Dodaj jazdę testową</button>
        <button class="form__clear-button" @click.prevent="clear"><font-awesome-icon class="plus-icon icon" icon="eraser" />Wyczyść</button>
      </form>

    <div class="after-post" v-if="submitted">
      <h2 class="after-post__title">Jazda testowa została dodana!</h2>
    </div>

    <div class="preview">
      <h3 class="preview__header">Podgląd</h3>
      <p class="preview__data">
        Data rozpoczęcia:
        <span class="preview__value">{{testDrive.startDrive}}</span>
      </p>
      <p class="preview__data">
        Data zakończenia:
        <span class="preview__value">{{testDrive.endDrive | format-date}}</span>
      </p>
      <h4 class="preview__header">Klient</h4>
      <p class="preview__data">
        Dane ogólne:
        <span class="preview__value">{{testDrive.customer.firstName}} {{testDrive.customer.lastName}}</span>
      </p>
      <h4 class="preview__header">Pracownik</h4>
      <p class="preview__data">
        Dane ogólne:
        <span class="preview__value">{{testDrive.employee.firstName}} {{testDrive.employee.position}}</span>
      </p>
      <h4 class="preview__header">Motocykl</h4>
      <p class="preview__data">
        Dane ogólne:
        <span class="preview__value">{{testDrive.motorcycleDetails.motorcycleCode}}</span>
      </p>
    </div>

    <InfoModal
      v-if="showModal"
      :headerText="modalHeaderContent"
      :bodyText="modalBodyContent"
      @confirm="close"
    />
  </div>
</template>

<script>
import { testDriveUrl, motorcycleDetailsSoldFalseUrl, customerUrl, employeeUrl } from "@/variables";
import * as moment from "moment/moment";
import InfoModal from "@/components/modal/InfoModal";

export default {
  name: "addTestDrive",
  components: {
    InfoModal,
  },
  data() {
    return {
        testDrive: {
            startDrive: '',
            endDrive: '',
            customer: {},
            employee: {},
            motorcycleDetails: {},
        },
        motorcycles: [],
        customers: [],
        employees: [],
        submitted: false,
        errors: [],
        visible: false,
        hideErrors: false,
        modalHeaderContent: "Uwaga!",
        modalBodyContent: "Coś poszło nie tak, sprawdź błędy!",
        showModal: false
    };
  },
  filters: {
    formatDate(value) {
      if(value)
      return moment(String(value)).format('YYYY-MM-DD HH:mm')
    },
  },
  methods: {
    stepBack() {
      this.$router.go(-1);
    },
    back(){
        this.$router.push('/motorcycleDetails');
    },
    show() {
      this.visible = true;
    },
    showErrors() {
      this.visible = false;
      this.hideErrors = true;
    },
   checkForm() {
      this.errors = [];
      if(this.testDrive.startDrive && this.testDrive.customer && this.testDrive.employee && this.testDrive.motorcycleDetails) {
        return true;
      }

      if(!this.testDrive.startDrive) {
        this.errors.push('Data rozpoczęcia jest wymagana!');
      }
      if(!this.testDrive.customer || Object.keys(this.testDrive.customer).length === 0) {
        this.errors.push('Klient jest wymagany!');
      }
      if(!this.testDrive.employee || Object.keys(this.testDrive.employee).length === 0) {
        this.errors.push('Pracownik jest wymagany!');
      }
      if(!this.testDrive.motorcycleDetails || Object.keys(this.testDrive.motorcycleDetails).length === 0) {
        this.errors.push('Motocykl jest wymagany!');
      }
    },
    post() {
      const isValid = this.checkForm();
      if(isValid) {
        this.testDrive.startDrive = moment(String(this.testDrive.startDrive)).format('YYYY-MM-DD HH:mm');
        this.testDrive.endDrive = moment(String(this.testDrive.endDrive)).format('YYYY-MM-DD HH:mm');
        this.$http.post(`${testDriveUrl}`, this.testDrive)
        .then(() => {
          this.submitted = true;
          setTimeout(() => {
            this.$router.push('/testDrives');
          }, 1500);
        })
        .catch(() => {
           this.showModal = true;
           this.errors.push("Podałeś/aś niepoprawne dane, sprawdź ponownie!");
          })
      } else {
        this.showModal = true;
      }
    },
     clear() {
      this.testDrive.startDrive = "",
      this.testDrive.endDrive = "",
      this.testDrive.customer = "",
      this.testDrive.employee = "",
      this.testDrive.motorcycleDetails = ""
    },
    close() {
      this.showModal = false;
    },
    getMotorcycles() {
      this.$http
        .get(`${motorcycleDetailsSoldFalseUrl}`)
        .then(resp => resp.json())
        .then(data => {
          this.motorcycles = data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    getCustomers() {
      this.$http
        .get(`${customerUrl}`)
        .then(resp => resp.json())
        .then(data => {
          this.customers = data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    getEmployees() {
      this.$http
        .get(`${employeeUrl}`)
        .then(resp => resp.json())
        .then(data => {
          this.employees = data;
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  created() {
      this.getCustomers();
      this.getEmployees();
      this.getMotorcycles();
  },
};
</script>

<style lang="scss" scoped>
</style>