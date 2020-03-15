<template>
  <div id="editTestDrive" class="main-container--edit">
     <h1 class="main-container--edit__title">Modyfikuj jazdę testową z dnia: {{testDrive.startDrive | format-date}}</h1>
     
     <button class="single-testDrive__button" @click="back"><font-awesome-icon class="plus-icon icon" icon="arrow-left" />Powrót</button>
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
</style>