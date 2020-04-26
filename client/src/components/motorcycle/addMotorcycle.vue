<template>
  <div id="addMotorcycle" class="main-container--add">
     <h1 class="main-container--add__title">Dodaj nowy motocykl do katalogu</h1>

     <button class="main-container--add__arrow" @click="stepBack">
      <font-awesome-icon class="plus-icon icon" icon="arrow-left" />
    </button>
     
     <form class="form">
      <h2 class="form__title--main">Dane szczegółowe</h2>
      <p class="form__wrapper">
        <label class="form__label" for="model">Model:</label>
        <input class="form__input date-input" type="text" name="model" id="model" required v-model.lazy="motorcycle.model" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="brand">Marka:</label>
        <input class="form__input" type="text" name="brand" id="brand" required v-model.lazy="motorcycle.brand" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="classif">Klasyfikacja:</label> 
        <select class="form__input" name="classif" id="classif" required v-model.lazy="motorcycle.classification">
          <option v-for="classification in classifications" :key="classification">{{classification}}</option>
        </select>
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="power">Moc:</label>
        <input class="form__input" type="number" name="power" id="power" required v-model.lazy="motorcycle.power" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="capacity">Pojemność silnika:</label>
        <input class="form__input" type="number" placeholder="0.00" step="0.01" name="capacity" id="capacity" required v-model.lazy="motorcycle.capacity" />
      </p>
      <p>
        <label class="form__label" for="engType">Rodzaj silnika:</label>
        <input class="form__input" type="text" name="engType" id="engType" required v-model.lazy="motorcycle.engineType">
      </p>
      <p>
        <label class="form__label" for="weight">Waga:</label>
        <input class="form__input" type="number" placeholder="0.00" step="0.01" name="weight" id="weight" required v-model.lazy="motorcycle.weight">
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
      <h2 class="after-post__title">Model został dodany do katalogu
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
        Model:
        <span class="preview__value">{{motorcycle.model}}</span>
      </p>
      <p class="preview__data">
        Marka:
        <span class="preview__value">{{motorcycle.brand}}</span>
      </p>
      <p class="preview__data">
        Klasyfikacja:
        <span class="preview__value">{{motorcycle.classification}}</span>
      </p>
      <p class="preview__data">
        Moc (km):
        <span class="preview__value">{{motorcycle.power}}</span>
      </p>
      <p class="preview__data">
        Pojemność silnika (cm <span class="cubic-centimeters">3</span> ):
        <span class="preview__value">{{motorcycle.capacity}}</span>
      </p>
      <p class="preview__data">
        Rodzaj silnika:
        <span class="preview__value">{{motorcycle.engineType}}</span>
      </p>
      <p class="preview__data">
        Waga (kg):
        <span class="preview__value">{{motorcycle.weight}}</span>
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
import { motorcycleUrl } from "@/variables";
import InfoModal from "@/components/modal/InfoModal";

export default {
  name: "addMotorcycle",
  components: {
    InfoModal
  },
  data() {
    return {
     motorcycle: {
          model: '',
          brand: '',
          classification: '',
          engineType: '',
          capacity: '',
          power: '',
          weight:'',
      },
      classifications: ['Supersport','Cross','Custom','Sport_Adventure_Tourer'],
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
        this.$router.push('/motorcycles');
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
      if(this.motorcycle.model && this.motorcycle.brand && this.motorcycle.classification && this.motorcycle.power && this.motorcycle.capacity && this.motorcycle.weight &&
      this.motorcycle.engineType) {
        return true;
      }
      if(!this.motorcycle.model) {
        this.errors.push('Model jest wymagany!');
      }
      if(!this.motorcycle.brand) {
        this.errors.push('Marka jest wymagana!');
      }
      if(!this.motorcycle.classification) {
        this.errors.push('Klasyfikacja jest wymagana!');
      }
      if(!this.motorcycle.capacity) {
        this.errors.push('Pojemność silnika jest wymagana!');
      }
      if(!this.motorcycle.power) {
        this.errors.push('Moc jest wymagana!');
      }
      if(!this.motorcycle.engineType) {
        this.errors.push('Rodzaj silnika jest wymagany!');
      }
      if(!this.motorcycle.weight) {
        this.errors.push('Waga motocykla jest wymagana!');
      }
    },
    post() {
      const isValid = this.checkForm();
      if(isValid) {
        this.$http.post(`${motorcycleUrl}`, this.motorcycle)
        .then(() => {
          this.clear();
          this.submitted = true;
          setTimeout(() => {
            this.$router.push('/motorcycles');
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
      this.motorcycle.model = "",
      this.motorcycle.brand = "",
      this.motorcycle.classification = "",
      this.motorcycle.power = "",
      this.motorcycle.capacity = "",
      this.motorcycle.weight = "",
      this.motorcycle.engineType = "";
    },
    close() {
      this.showModal = false;
    }
  },
};
</script>

<style lang="scss" scoped>
</style>