<template>
  <div id="editEmployee" class="main-container--edit">
     <h1 class="main-container--edit__title">Modyfikuj pracownika: {{employee.firstName}}</h1>
     
     <button class="single-employee__button" @click="back"><font-awesome-icon class="plus-icon icon" icon="arrow-left" />Powrót</button>
    <form v-if="!submitted" class="form">
      <h2 class="form__title--main">Dane szczegółowe</h2>
      <p class="form__wrapper">
        <label class="form__label" for="name">Imię:</label>
        <input class="form__input date-input" type="text" name="name" id="name" required v-model.lazy="employee.firstName" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="desc">Nazwisko:</label>
        <input class="form__input" type="text" name="desc" id="desc" required v-model.lazy="employee.lastName" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="position">Stanowisko:</label> 
        <select class="form__input" name="position" id="position" required v-model.lazy="employee.position">
          <option v-for="position in positions" :key="position">{{position}}</option>
        </select>
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="salary">Wynagrodzenie(PLN):</label>
        <input class="form__input" type="number" placeholder="0.00" step="0.01" name="salary" id="salary" required v-model.lazy="employee.salary" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="empDate">Data zatrudnienia:</label>
        <input class="form__input" type="date" name="empDate" id="empDate" required v-model.lazy="employee.dateOfEmployment" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="termDate">Data zwolnienia:</label>
        <input class="form__input" type="date" name="termDate" id="termDate" required v-model.lazy="employee.dateOfTermination" />
      </p>
      <p>
        <label class="form__label" for="date">Data urodzin:</label>
        <input class="form__input" type="date" name="date" id="date" required v-model.lazy="employee.birthDate">
      </p>
      <h2 class="form__title--address">Adres zamieszkania</h2>
      <p class="form__wrapper">
        <label class="form__label" for="locality">Miejscowość:</label>
        <input class="form__input date-input" type="text" name="locality" id="locality" required v-model.lazy="employee.address.locality" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="zipCode">Kod-pocztowy:</label>
        <input class="form__input" name="zipCode" id="zipCode" required v-model.lazy="employee.address.zipCode" />
      </p>
      <p>
        <label class="form__label" for="street">Ulica:</label>
        <input class="form__input" type="text" name="street" id="street" required v-model.lazy="employee.address.street">
      </p>
      <p>
        <label class="form__label" for="streetNumber">Numer:</label>
        <input class="form__input" placeholder="0" type="number" name="streetNumber" id="streetNumber" required v-model.lazy="employee.address.streetNumber">
      </p>
      <p class="form__errors" v-if="errors.length">
        <span>Popraw następujące błędy:</span>
        <ul>
          <li v-for="error in errors" :key="error">
            {{error}}
          </li>
        </ul>
      </p>
      <button class="form__send-button join" @click.prevent="put"><font-awesome-icon class="plus-icon icon" icon="check" />Zatwierdź</button>
    </form>

    <div class="after-post" v-if="submitted">
      <h2 class="after-post__title">Pracownik został zmodyfikowany!</h2>
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
import { employeeUrl } from "@/variables";
import * as moment from "moment/moment";
import InfoModal from "@/components/modal/InfoModal";

export default {
  name: "editEmployee",
  components: {
    InfoModal
  },
  data() {
    return {
       employee: {
          firstName: '',
          lastName: '',
          birthDate: '',
          position: '',
          salary: '',
          dateOfEmployment: '',
          dateOfTermination: '',
          address: {
              locality: '',
              street: '',
              streetNumber: '',
              zipCode: '',
          },
      },
      positions: ['DEALER','MECHANIC','MANAGER'],
      employeeId: this.$route.params.id,
      submitted: false,
      errors: [],
      visible: false,
      hideErrors: false,
      modalHeaderContent: "Uwaga!",
      modalBodyContent: "Coś poszło nie tak, sprawdź błędy!",
      showModal: false
    };
  },
  methods: {
    back(){
        this.$router.replace(`/employee/${this.employeeId}`);
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
      if(this.employee.firstName && this.employee.lastName && this.employee.birthDate && this.employee.position && this.employee.dateOfEmployment &&
      (this.employee.address.locality && this.employee.address.zipCode && this.employee.address.street && this.employee.address.streetNumber)) {
        return true;
      }
      if(!this.employee.firstName) {
        this.errors.push('Imię jest wymagane!');
      }
      if(!this.employee.lastName) {
        this.errors.push('Nazwisko jest wymagane!');
      }
      if(!this.employee.position) {
        this.errors.push('Stanowisko jest wymagane!');
      }
      if(!this.employee.dateOfEmployment) {
        this.errors.push('Data zatrudnienia jest wymagana!');
      }
      if(!this.employee.birthDate) {
        this.errors.push('Data urodzin jest wymagana!');
      }
      if(!this.employee.address.locality || !this.employee.address.zipCode || !this.employee.address.street || !this.employee.address.streetNumber) {
        this.errors.push('Pełny adres zamieszkania jest wymagany!');
      }
    },
    put() {
      const isValid = this.checkForm();
      if(isValid) {
        this.$http.put(`${employeeUrl}/${this.employeeId}`, this.employee)
        .then(() => {
          this.submitted = true;
          setTimeout(() => {
            this.$router.push('/employees');
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
    getEmployee() {
           this.$http.get(`${employeeUrl}/${this.employeeId}`)
          .then(response => response.json())
          .then(object => {
              const {firstName, lastName, position, salary, dateOfEmployment, dateOfTermination, birthDate, address} = object;
              this.employee.firstName = firstName;
              this.employee.lastName = lastName;
              this.employee.birthDate = moment(birthDate).format("YYYY-MM-DD");
              this.employee.position = position;
              this.employee.salary = salary;
              this.employee.dateOfEmployment = moment(dateOfEmployment).format("YYYY-MM-DD");
              this.employee.dateOfTermination = moment(dateOfTermination).format("YYYY-MM-DD") == null
               ? "" : moment(dateOfTermination).format("YYYY-MM-DD");
              this.employee.address = address;
          })
          .catch(err => {
            console.log(err)
            })
        },
    close() {
      this.showModal = false;
    }
  },
  created() {
      this.getEmployee();
  }
};
</script>

<style lang="scss" scoped>
</style>