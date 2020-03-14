<template>
  <div id="editMotorcycleDetails" class="main-container--edit">
     <h1 class="main-container--edit__title">Modyfikuj jazdę testową z dnia: {{testDrive.startDrive | format-date}}</h1>
     
     <button class="single-motorcycleDetails__button" @click="back"><font-awesome-icon class="plus-icon icon" icon="arrow-left" />Powrót</button>
    <form v-if="!submitted" class="form">
      <h2 class="form__title--main">Dane szczegółowe</h2>
      <p class="form__wrapper">
        <label class="form__label" for="startDrive">Data rozpoczęcia:</label>
        <input class="form__input date-input" type="datetime-local" name="startDrive" id="startDrive" required v-model.lazy="testDrive.startDrive" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="endDrive">Data zakończenia:</label>
        <input class="form__input date-input" type="datetime-local" name="endDrive" id="endDrive" required v-model.lazy="testDrive.endDrive" />
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
      <h2 class="after-post__title">Jazda testowa została zmodyfikowana!</h2>
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
import { testDriveUrl } from "@/variables";
import * as moment from "moment/moment";
import InfoModal from "@/components/modal/InfoModal";

export default {
  name: "editTestDrive",
  components: {
    InfoModal
  },
  data() {
    return {
       testDrive: {
           startDrive: '',
           endDrive: '',
           customer: {},
           employee: {},
           motorcycleDetails: {}
      },
      testDriveId: this.$route.params.id,
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
      if(this.testDrive.startDrive) {
        return true;
      }
      if(!this.testDrive.startDrive) {
        this.errors.push('Data rozpoczęcia jazdy jest wymagana!');
      }
    },
    put() {
      const isValid = this.checkForm();
      if(isValid) {
        this.testDrive.startDrive = moment(String(this.testDrive.startDrive)).format('YYYY-MM-DD HH:mm');
        this.testDrive.endDrive = moment(String(this.testDrive.endDrive)).format('YYYY-MM-DD HH:mm');
        this.$http.put(`${testDriveUrl}/${this.testDriveId}`, this.testDrive)
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
    getMotorcycleDetails() {
           this.$http.get(`${testDriveUrl}/${this.testDriveId}`)
          .then(response => response.json())
          .then(object => {
              const {startDrive, endDrive, customer, employee, motorcycleDetails} = object;
              this.testDrive.startDrive = moment(String(startDrive)).format('YYYY-MM-DDTHH:mm');
              this.testDrive.endDrive = moment(String(endDrive)).format('YYYY-MM-DDTHH:mm');
              this.testDrive.customer = customer;
              this.testDrive.employee = employee;
              this.testDrive.motorcycleDetails = motorcycleDetails;
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
      this.getMotorcycleDetails();
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