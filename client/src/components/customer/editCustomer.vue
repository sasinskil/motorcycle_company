<template>
  <div id="editCustomer" class="main-container--edit">
     <h1 class="main-container--edit__title">Modyfikuj klienta: {{customer.firstName}}</h1>
     
     <button class="single-customer__button" @click="back"><font-awesome-icon class="plus-icon icon" icon="arrow-left" />Powrót</button>
    <form v-if="!submitted" class="form">
      <h2 class="form__title--main">Dane szczegółowe</h2>
      <p class="form__wrapper">
        <label class="form__label" for="name">Imię:</label>
        <input class="form__input date-input" type="text" name="name" id="name" required v-model.lazy="customer.firstName" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="desc">Nazwisko:</label>
        <input class="form__input" name="desc" id="desc" required v-model.lazy="customer.lastName" />
      </p>
      <p>
        <label class="form__label" for="date">Data urodzin:</label>
        <input class="form__input" type="date" name="date" id="date" required v-model.lazy="customer.birthDate">
      </p>
      <h2 class="form__title--address">Adres zamieszkania</h2>
      <p class="form__wrapper">
        <label class="form__label" for="locality">Miejscowość:</label>
        <input class="form__input date-input" type="text" name="locality" id="locality" required v-model.lazy="customer.address.locality" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="zipCode">Kod-pocztowy:</label>
        <input class="form__input" name="zipCode" id="zipCode" required v-model.lazy="customer.address.zipCode" />
      </p>
      <p>
        <label class="form__label" for="street">Ulica:</label>
        <input class="form__input" type="text" name="street" id="street" required v-model.lazy="customer.address.street">
      </p>
      <p>
        <label class="form__label" for="streetNumber">Numer:</label>
        <input class="form__input" placeholder="0" type="number" name="streetNumber" id="streetNumber" required v-model.lazy="customer.address.streetNumber">
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
      <h2 class="after-post__title">Klient został zmodyfikowany!</h2>
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
import { customerUrl } from "@/variables";
import * as moment from "moment/moment";
import InfoModal from "@/components/modal/InfoModal";

export default {
  name: "editCustomer",
  components: {
    InfoModal
  },
  data() {
    return {
      customer: {
        firstName: "",
        lastName: "",
        birthDate: "",
        address: {
            locality: "",
            zipCode: "",
            street: "",
            streetNumber: "",
        },
      },
      customerId: this.$route.params.id,
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
        this.$router.replace(`/customer/${this.customerId}`);
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
      if(this.customer.firstName && this.customer.lastName && this.customer.birthDate && 
      (this.customer.address.locality && this.customer.address.zipCode && this.customer.address.street && this.customer.address.streetNumber)) {
        return true;
      }
      if(!this.customer.firstName) {
        this.errors.push('Imię jest wymagane!');
      }
      if(!this.customer.lastName) {
        this.errors.push('Nazwisko jest wymagane!');
      }
      if(!this.customer.birthDate) {
        this.errors.push('Data urodzin jest wymagana!');
      }
      if(!this.customer.address.locality || !this.customer.address.zipCode || !this.customer.address.street || !this.customer.address.streetNumber) {
        this.errors.push('Pełny adres zamieszkania jest wymagany!');
      }
    },
    put() {
      const isValid = this.checkForm();
      if(isValid) {
        this.$http.put(`${customerUrl}/${this.customerId}`, this.customer)
        .then(() => {
          this.submitted = true;
          setTimeout(() => {
            this.$router.push('/customers');
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
    getCustomer() {
           this.$http.get(`${customerUrl}/${this.customerId}`)
          .then(response => response.json())
          .then(object => {
              const {firstName, lastName, birthDate, address} = object;
              this.customer.firstName = firstName;
              this.customer.lastName = lastName;
              this.customer.birthDate = moment(birthDate).format("YYYY-MM-DD");
              this.customer.address = address;
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
      this.getCustomer();
  }
};
</script>

<style lang="scss" scoped>
</style>