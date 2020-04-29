<template>
  <div id="addService" class="main-container--add">
     <h1 class="main-container--add__title">Dodaj serwis</h1>

     <button class="main-container--add__arrow" @click="stepBack">
      <font-awesome-icon class="plus-icon icon" icon="arrow-left" />
    </button>
     
     <form class="form">
      <h2 class="form__title--main">Dane szczegółowe</h2>
      <p class="form__wrapper">
        <label class="form__label" for="operation">Operacja:</label>
        <input class="form__input" type="text" name="operation" id="operation" required v-model.lazy="service.operation" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="price">Cena (PLN):</label>
        <input class="form__input" type="number" placeholder="0.00" step="0.01" name="price" id="price" required v-model.lazy="service.price" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="serviceStart">Data rozpoczęcia serwisu:</label>
        <input class="form__input" type="date" name="serviceStart" id="serviceStart" required v-model.lazy="service.startWorkingDate" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="serviceEnd">Data zakończenia serwisu:</label>
        <input class="form__input" type="date" name="serviceEnd" id="serviceEnd" required v-model.lazy="service.endWorkingDate" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="customer">Klient:</label> 
        <select class="form__input" name="customer" id="customer" required v-model="service.customer">
          <option v-for="(singleCustomer, idx) in customers" :key="idx" :value="singleCustomer">{{singleCustomer.firstName}} - {{singleCustomer.lastName}}</option>
        </select>
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="employee">Pracownik:</label> 
        <select class="form__input" name="employee" id="employee" required v-model="service.employee">
          <option v-for="(singleEmployee, idx) in employees" :key="idx" :value="singleEmployee">{{singleEmployee.firstName}} - {{singleEmployee.position}}</option>
        </select>
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="motorcycle">Motocykl:</label> 
        <select class="form__input" name="motorcycle" id="motorcycle" required v-model="service.motorcycleDetails">
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
        <button class="form__send-button" @click.prevent="post"><font-awesome-icon class="plus-icon icon" icon="plus" />Dodaj</button>
        <button class="form__clear-button" @click.prevent="clear"><font-awesome-icon class="plus-icon icon" icon="eraser" />Wyczyść</button>
      </form>

    <div class="after-post" v-if="submitted">
      <h2 class="after-post__title">Serwis został dodany
        <span class="after-post__title--pulse">
            <font-awesome-icon
              class="after-post__title--icon"
              icon="check"
            />
          </span>
      </h2>
    </div>

    <div class="preview">
      <h3 class="preview__header">Podgląd</h3>
      <p class="preview__data">
        Operacja:
        <span class="preview__value">{{service.operation}}</span>
      </p>
      <p class="preview__data">
        Cena:
        <span class="preview__value">{{service.price}}</span>
      </p>
      <p class="preview__data">
        Data rozpoczęcia serwisu:
        <span class="preview__value">{{service.startWorkingDate | format-date-long}}</span>
      </p>
      <p class="preview__data">
        Data zakończenia serwisu:
        <span class="preview__value">{{service.endWorkingDate | format-date-long}}</span>
      </p>
      <h4 class="preview__header">Klient</h4>
      <p class="preview__data">
        Dane ogólne:
        <span class="preview__value">{{service.customer.firstName}} {{service.customer.lastName}}</span>
      </p>
      <h4 class="preview__header">Pracownik</h4>
      <p class="preview__data">
        Dane ogólne:
        <span class="preview__value">{{service.employee.firstName}} {{service.employee.position}}</span>
      </p>
      <h4 class="preview__header">Motocykl</h4>
      <p class="preview__data">
        Dane ogólne:
        <span class="preview__value">{{service.motorcycleDetails.motorcycleCode}}</span>
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
import { serviceUrl, motorcycleDetailsSoldTrueUrl, customerUrl, employeeUrl } from "@/variables";
import InfoModal from "@/components/modal/InfoModal";

export default {
  name: "addService",
  components: {
    InfoModal
  },
  data() {
    return {
        service: {
            operation: '',
            price: '',
            startWorkingDate: '',
            endWorkingDate: '',
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
        modalHeaderContent: "Uwaga",
        modalBodyContent: "Coś poszło nie tak, sprawdź błędy!",
        showModal: false
    };
  },
  methods: {
    stepBack() {
      this.$router.go(-1);
    },
    back(){
        this.$router.push('/services');
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
      if(this.service.operation && this.service.price && this.service.startWorkingDate &&
        this.service.customer && this.service.employee && this.service.motorcycleDetails) {
        return true;
      }

      if(!this.service.operation) {
        this.errors.push('Operacja jest wymagana!');
      }
      if(!this.service.price) {
        this.errors.push('Cena jest wymagana!');
      }
      if(!this.service.startWorkingDate) {
        this.errors.push('Data rozpoczęcia jest wymagana!');
      }
      if(!this.service.customer || Object.keys(this.service.customer).length === 0) {
        this.errors.push('Klient jest wymagany!');
      }
      if(!this.service.employee || Object.keys(this.service.employee).length === 0) {
        this.errors.push('Pracownik jest wymagany!');
      }
      if(!this.service.motorcycleDetails || Object.keys(this.service.motorcycleDetails).length === 0) {
        this.errors.push('Motocykl jest wymagany!');
      }
    },
    post() {
      const isValid = this.checkForm();
      if(isValid) {
        this.$http.post(`${serviceUrl}`, this.service)
        .then(() => {
          this.submitted = true;
          setTimeout(() => {
            this.$router.push('/services');
          }, 2000);
        })
        .catch((err) => {
           this.showModal = true;
           this.errors.push("Podałeś/aś niepoprawne dane, sprawdź ponownie!");
           console.log(err);
          })
      } else {
        this.showModal = true;
      }
    },
     clear() {
      this.transaction.operation = "",
      this.transaction.price = ""
    },
    close() {
      this.showModal = false;
    },
    getMotorcycles() {
      this.$http
        .get(`${motorcycleDetailsSoldTrueUrl}`)
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