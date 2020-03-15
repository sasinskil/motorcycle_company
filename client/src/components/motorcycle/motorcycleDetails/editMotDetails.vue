<template>
  <div id="editMotorcycleDetails" class="main-container--edit">
     <h1 class="main-container--edit__title">Modyfikuj egzemplarz: {{motorcycleDetails.motorcycleCode}}</h1>
     
     <button class="single-motorcycleDetails__button" @click="back"><font-awesome-icon class="plus-icon icon" icon="arrow-left" />Powrót</button>
    <form v-if="!submitted" class="form">
      <h2 class="form__title--main">Dane szczegółowe</h2>
      <p class="form__wrapper">
        <label class="form__label" for="motDetCode">Kod motocykla:</label>
        <input class="form__input date-input" type="text" disabled name="motDetCode" id="motDetCode" required v-model.lazy="motorcycleDetails.motorcycleCode" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="price">Cena (PLN):</label>
        <input class="form__input date-input" type="number" name="price" id="price" required v-model.lazy="motorcycleDetails.price" />
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
      <h2 class="after-post__title">Egzemplarz został zmodyfikowany!</h2>
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
import { motorcycleDetailsUrl } from "@/variables";
import InfoModal from "@/components/modal/InfoModal";

export default {
  name: "editMotDetails",
  components: {
    InfoModal
  },
  data() {
    return {
       motorcycleDetails: {
           motorcycleCode: '',
           price: '',
           motorcycle: {}
      },
      motorcycleDetailsId: this.$route.params.id,
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
      if(this.motorcycleDetails.price) {
        return true;
      }
      if(!this.motorcycleDetails.price) {
        this.errors.push('Cena jest wymagana!');
      }
    },
    put() {
      const isValid = this.checkForm();
      if(isValid) {
        this.$http.put(`${motorcycleDetailsUrl}/${this.motorcycleDetailsId}`, this.motorcycleDetails)
        .then(() => {
          this.submitted = true;
          setTimeout(() => {
            this.$router.push('/motorcycleDetails');
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
           this.$http.get(`${motorcycleDetailsUrl}/${this.motorcycleDetailsId}`)
          .then(response => response.json())
          .then(object => {
              const {motorcycleCode, price, motorcycle} = object;
              this.motorcycleDetails.motorcycleCode = motorcycleCode;
              this.motorcycleDetails.price = price;
              this.motorcycleDetails.motorcycle = motorcycle;
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
      this.getMotorcycleDetails();
  }
};
</script>

<style lang="scss" scoped>
</style>