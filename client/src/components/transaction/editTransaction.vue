<template>
  <div id="editTransaction" class="main-container--edit">
     <h1 class="main-container--edit__title">Modyfikuj transakcję z dnia: {{transaction.transactionTime | format-date-long}}</h1>
     
     <button class="single-transaction__button" @click="back"><font-awesome-icon class="plus-icon icon" icon="arrow-left" />Powrót</button>
    <form class="form">
      <h2 class="form__title--main">Dane szczegółowe</h2>
      <p class="form__wrapper">
        <label class="form__label" for="operation">Operacja:</label>
        <input class="form__input date-input" type="text" name="operation" id="operation" required v-model.lazy="transaction.operation" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="price">Cena:</label>
        <input class="form__input date-input" type="number" name="price" id="price" required v-model.lazy="transaction.price" />
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
      <h2 class="after-post__title">Transakcja została zmodyfikowana
        <span class="after-post__title--pulse">
            <font-awesome-icon
              class="after-post__title--icon"
              icon="check"
            />
          </span>
      </h2>
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
import { transactionUrl } from "@/variables";
import InfoModal from "@/components/modal/InfoModal";

export default {
  name: "editTransaction",
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
           motorcycleDetails: {}
      },
      transactionId: this.$route.params.id,
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
    back(){
        this.$router.go(-1);
    },
    show() {
      this.visible = true;
    },
   checkForm() {
      this.errors = [];
      if(this.transaction.operation && this.transaction.price) {
        return true;
      }
      if(!this.transaction.operation) {
        this.errors.push('Operacja jest wymagana!');
      }
      if(!this.transaction.price) {
        this.errors.push('Cena jest wymagana!');
      }
    },
    put() {
      const isValid = this.checkForm();
      if(isValid) {
        this.$http.put(`${transactionUrl}/${this.transactionId}`, this.transaction)
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
    getTransaction() {
           this.$http.get(`${transactionUrl}/${this.transactionId}`)
          .then(response => response.json())
          .then(object => {
              const {operation, price, transactionTime, employee, customer, motorcycleDetails} = object;
              this.transaction.operation = operation;
              this.transaction.price = price;
              this.transaction.transactionTime = transactionTime;
              this.transaction.employee = employee;
              this.transaction.customer = customer;
              this.transaction.motorcycleDetails = motorcycleDetails;
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
      this.getTransaction();
  }
};
</script>

<style lang="scss" scoped>
</style>