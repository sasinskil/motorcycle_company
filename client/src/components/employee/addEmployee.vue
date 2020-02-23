<template>
  <div id="addEmployee" class="main-container--add">
     <h1 class="main-container--add__title">Dodaj nowego pracownika</h1>
     
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
        <button class="form__send-button" @click.prevent="post"><font-awesome-icon class="plus-icon icon" icon="plus" />Dodaj pracownika</button>
        <button class="form__clear-button" @click.prevent="clear"><font-awesome-icon class="plus-icon icon" icon="eraser" />Wyczyść</button>
      </form>

    <div class="after-post" v-if="submitted">
      <h2 class="after-post__title">Pracownik został dodany!</h2>
    </div>

    <div class="preview">
      <h3 class="preview__header">Podgląd</h3>
      <h4 class="preview__header--main">Dane szczegółowe</h4>
      <p class="preview__data">
        Imię:
        <span class="preview__value">{{employee.firstName}}</span>
      </p>
      <p class="preview__data">
        Nazwisko:
        <span class="preview__value">{{employee.lastName}}</span>
      </p>
      <p class="preview__data">
        Stanowisko:
        <span class="preview__value">{{employee.position}}</span>
      </p>
      <p class="preview__data">
        Wynagrodzenie(PLN):
        <span class="preview__value">{{employee.salary}}</span>
      </p>
      <p class="preview__data">
        Data zatrudnienia:
        <span class="preview__value">{{employee.dateOfEmployment}}</span>
      </p>
      <p class="preview__data">
        Data urodzin:
        <span class="preview__value">{{employee.birthDate | format-date}}</span>
      </p>
      <h4 class="preview__header--address">Adres zamieszkania</h4>
      <p class="preview__data">
        Mijescowość:
        <span class="preview__value">{{employee.address.locality}}</span>
      </p>
      <p class="preview__data">
        Kod pocztowy:
        <span class="preview__value">{{employee.address.zipCode}}</span>
      </p>
      <p class="preview__data">
        Ulica:
        <span class="preview__value">{{employee.address.street}}</span>
      </p>
      <p class="preview__data">
        Numer:
        <span class="preview__value">{{employee.address.streetNumber}}</span>
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
import { employeeUrl } from "@/variables";
import * as moment from "moment/moment";
import InfoModal from "@/components/modal/InfoModal";

export default {
  name: "addEmployee",
  components: {
    InfoModal
  },
  data() {
    return {
     employee: {
          firstName: '',
          lastName: '',
          birthDate: '',
          address: {
            locality: '',
            zipCode: '',
            street: '',
            streetNumber: '',
          },
          salary: '',
          position: '',
          dateOfEmployment: '',
      },
      positions: ['DEALER','MECHANIC','MANAGER'],
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
      return moment(String(value)).format('YYYY-MM-DD')
    },
  },
  methods: {
    back(){
        this.$router.push('/employees');
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
      if(this.employee.firstName && this.employee.lastName && this.employee.birthDate && this.employee.position && this.employee.salary && this.employee.dateOfEmployment &&
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
      if(!this.employee.salary) {
        this.errors.push('Wynagrodzenie jest wymagane!');
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
    post() {
      const isValid = this.checkForm();
      if(isValid) {
        this.$http.post(`${employeeUrl}`, this.employee)
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
     clear() {
      this.employee.firstName = "",
      this.employee.lastName = "",
      this.employee.position = "",
      this.employee.salary = "",
      this.employee.dateOfEmployment = "",
      this.employee.birthDate = "",
      this.employee.address.locality = "";
      this.employee.address.zipCode = "";
      this.employee.address.street = "";
      this.employee.address.streetNumber = "";
    },
    close() {
      this.showModal = false;
    }
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
  &__content {
    display: block;
  }
  &__value {
    color: #2c3e508e;
  }
}
</style>