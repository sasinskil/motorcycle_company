<template>
  <div id="addTransaction" class="main-container--add">
     <h1 class="main-container--add__title">Dodaj transakcje</h1>
     
     <form v-if="!submitted" class="form">
      <h2 class="form__title--main">Dane szczegółowe</h2>
      <p class="form__wrapper">
        <label class="form__label" for="operation">Operacja:</label>
        <input class="form__input" type="text" name="operation" id="operation" required v-model.lazy="transaction.operation" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="price">Cena (PLN):</label>
        <input class="form__input" type="number" placeholder="0.00" step="0.01" name="price" id="price" required v-model.lazy="transaction.price" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="transactionTime">Data operacji:</label>
        <input class="form__input date-input" type="datetime-local" name="transactionTime" id="transactionTime" required v-model.lazy="transaction.transactionTime" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="customer">Klient:</label> 
        <select class="form__input" name="customer" id="customer" required v-model="transaction.customer">
          <option v-for="(singleCustomer, idx) in customers" :key="idx" :value="singleCustomer">{{singleCustomer.firstName}} - {{singleCustomer.lastName}}</option>
        </select>
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="employee">Pracownik:</label> 
        <select class="form__input" name="employee" id="employee" required v-model="transaction.employee">
          <option v-for="(singleEmployee, idx) in employees" :key="idx" :value="singleEmployee">{{singleEmployee.firstName}} - {{singleEmployee.position}}</option>
        </select>
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="motorcycle">Motocykl:</label> 
        <select class="form__input" name="motorcycle" id="motorcycle" required v-model="transaction.motorcycleDetails">
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
        <button class="form__send-button" @click.prevent="post"><font-awesome-icon class="plus-icon icon" icon="plus" />Dodaj motocykl</button>
        <button class="form__clear-button" @click.prevent="clear"><font-awesome-icon class="plus-icon icon" icon="eraser" />Wyczyść</button>
      </form>

    <div class="after-post" v-if="submitted">
      <h2 class="after-post__title">Transakcja została dodana!</h2>
    </div>

    <div class="preview">
      <h3 class="preview__header">Podgląd</h3>
      <p class="preview__data">
        Operacja:
        <span class="preview__value">{{transaction.operation}}</span>
      </p>
      <p class="preview__data">
        Cena:
        <span class="preview__value">{{transaction.price}}</span>
      </p>
      <p class="preview__data">
        Data wykonania operacji:
        <span class="preview__value">{{transaction.transactionTime | format-date}}</span>
      </p>
      <h4 class="preview__header">Klient</h4>
      <p class="preview__data">
        Dane ogólne:
        <span class="preview__value">{{transaction.customer.firstName}} {{transaction.customer.lastName}}</span>
      </p>
      <h4 class="preview__header">Pracownik</h4>
      <p class="preview__data">
        Dane ogólne:
        <span class="preview__value">{{transaction.employee.firstName}} {{transaction.employee.position}}</span>
      </p>
      <h4 class="preview__header">Motocykl</h4>
      <p class="preview__data">
        Dane ogólne:
        <span class="preview__value">{{transaction.motorcycleDetails.motorcycleCode}}</span>
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
import { transactionUrl, motorcycleDetailsSoldFalseUrl, customerUrl, employeeUrl } from "@/variables";
import InfoModal from "@/components/modal/InfoModal";
import * as moment from "moment/moment";

export default {
  name: "addTransaction",
  components: {
    InfoModal
  },
  data() {
    return {
        transaction: {
            operation: '',
            price: '',
            transactionTime: '',
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
    back(){
        this.$router.push('/transactions');
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
      if(this.motorcycleDetails.motorcycleCode && this.motorcycleDetails.price && this.motorcycleDetails.motorcycle) {
        return true;
      }

      if(!this.motorcycleDetails.motorcycleCode) {
        this.errors.push('Unikalny kod motocykla jest wymagany!');
      }
      if(!this.motorcycleDetails.price) {
        this.errors.push('Cena jest wymagana!');
      }
      if(!this.motorcycleDetails.motorcycle || Object.keys(this.motorcycleDetails.motorcycle).length === 0) {
        this.errors.push('Motocykl jest wymagany!');
      }
    },
    post() {
      const isValid = true;
      if(isValid) {
        this.transaction.transactionTime = moment(String(this.transaction.transactionTime)).format('YYYY-MM-DD HH:mm')
        this.$http.post(`${transactionUrl}`, this.transaction)
        .then(() => {
          this.submitted = true;
          setTimeout(() => {
            this.$router.push('/transactions');
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
      this.transaction.operation = "",
      this.transaction.price = ""
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
@import "@/assets/styles/main.scss";

.main-container--add {
  width: 100%;
  max-width: 950px;
  padding: 0 1rem 1rem;
  margin: 0 auto;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  align-items: flex-start;
  &__title {
    margin: 0 0 4rem 0;
    flex-basis: 100%;
    text-align: center;
  }
}

.cubic-centimeters {
    position: absolute;
    top: -4px;
    left: 162px;
    font-size: 0.7rem;
}

.form {
  width: 100%;
  max-width: 400px;
  text-align: left;
  &__wrapper {
    margin: 1rem 0;
    &--checkbox {
      display: inline-block;
    }
    &--checkbox + &--checkbox {
      margin-left: 1rem;
    }
  }
  &__label {
    display: block;
    margin-bottom: 0.4rem;
    font-weight: bold;
  }
  &__input {
    display: block;
    width: 100%;
    padding: 0.6rem;
    border: none;
    border-radius: 0.2rem;
    background: #fff;
    box-shadow: $default-shadow;
    &--text-area {
      min-height: 80px;
    }
  }
  &__send-button {
    margin: 1rem 0;
   @include default-button($navy-blue);
  }
  &__clear-button {
    margin: 1rem 0 0 0.5rem;
   @include default-button($dark-grey);
  }
  &__errors {
    margin-top: 2rem;
    color: #dc6868;
  }
}
svg {
    margin-right: 0.5rem;
    animation: rotateYe 1s;
}
.preview {
  width: 100%;
  max-width: 400px;
  margin-top: 2.5rem;
  padding: 0.4rem 1rem;
  background: #fff;
  box-shadow: $default-shadow;
  border-radius: 0.2rem;
  text-align: left;
  overflow-wrap: break-word;
  &__data {
      position: relative;
  }
  &__content {
    display: block;
  }
  &__value {
    color: #2c3e508e;
  }
}
</style>