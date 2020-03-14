<template>
  <div id="editMotorcycleDetails" class="main-container--edit">
     <h1 class="main-container--edit__title">Modyfikuj transakcję z dnia: {{transaction.transactionTime | format-date}}</h1>
     
     <button class="single-motorcycleDetails__button" @click="back"><font-awesome-icon class="plus-icon icon" icon="arrow-left" />Powrót</button>
    <form v-if="!submitted" class="form">
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
      <h2 class="after-post__title">Transakcja została zmodyfikowana!</h2>
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
import * as moment from "moment/moment";
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
      modalHeaderContent: "Uwaga!",
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
  filters: {
    formatDate(value) {
        if(value)
        return moment(String(value)).format("YYYY-MM-DD HH:mm");
    }
  },
  created() {
      this.getTransaction();
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/styles/main.scss";
@keyframes moveYe {
    0% {
        transform: translateX(0px);
    }
    50% {
        transform: translateX(-3px);
    }
    100% {
        transform: translateX(0px);
    }
    
}
.single-motorcycleDetails__button {
  @include default-button($navy-blue);
  margin: 1rem 19rem 2rem 0;
  &:hover > svg {
          animation: moveYe .8s infinite;
      }
}
.main-container--edit {
  width: 100%;
  max-width: 950px;
  padding: 0 1rem 1rem;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  &__title {
    margin: 0 0 4rem 0;
    flex-basis: 100%;
    text-align: center;
  }
}

.cubic-centimeters {
    position: absolute;
    top: -5px;
    left: 175px;
    font-size: 0.7rem;
}

.form {
  width: 100%;
  max-width: 420px;
  text-align: left;
  &__title--address {
      margin-top: 2rem;
  }

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
    position: relative;
  }
  &__input {
    display: block;
    width: 100%;
    padding: 0.6rem;
    border: none;
    border-radius: 0.2rem;
    background: #fff;
    box-shadow: $default-shadow;
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
.join {
 @include default-button($dark-green);
}
</style>