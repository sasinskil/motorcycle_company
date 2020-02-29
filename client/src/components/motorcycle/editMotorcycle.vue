<template>
  <div id="editMotorcycle" class="main-container--edit">
     <h1 class="main-container--edit__title">Modyfikuj motocykl: {{motorcycle.model}}</h1>
     
     <button class="single-motorcycle__button" @click="back"><font-awesome-icon class="plus-icon icon" icon="arrow-left" />Powrót</button>
    <form v-if="!submitted" class="form">
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
        <label class="form__label" for="power">Moc (km):</label>
        <input class="form__input" type="number" name="power" id="power" required v-model.lazy="motorcycle.power" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="capacity">Pojemność silnika (cm <span class="cubic-centimeters">3</span> ):</label>
        <input class="form__input" type="number" placeholder="0.00" step="0.01" name="capacity" id="capacity" required v-model.lazy="motorcycle.capacity" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="engType">Rodzaj silnika:</label>
        <input class="form__input" type="text" name="engType" id="engType" required v-model.lazy="motorcycle.engineType" />
      </p>
      <p>
        <label class="form__label" for="weight">Waga (kg):</label>
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
      <button class="form__send-button join" @click.prevent="put"><font-awesome-icon class="plus-icon icon" icon="check" />Zatwierdź</button>
    </form>

    <div class="after-post" v-if="submitted">
      <h2 class="after-post__title">Motocykl został zmodyfikowany!</h2>
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
  name: "editMotorcycle",
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
      motorcycleId: this.$route.params.id,
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
    put() {
      const isValid = this.checkForm();
      if(isValid) {
        this.$http.put(`${motorcycleUrl}/${this.motorcycleId}`, this.motorcycle)
        .then(() => {
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
    getMotorcycle() {
           this.$http.get(`${motorcycleUrl}/${this.motorcycleId}`)
          .then(response => response.json())
          .then(object => {
              const {model, brand, classification, power, capacity, engineType, weight} = object;
              this.motorcycle.model = model;
              this.motorcycle.brand = brand;
              this.motorcycle.classification = classification;
              this.motorcycle.power = power;
              this.motorcycle.capacity = capacity;
              this.motorcycle.engineType = engineType;
              this.motorcycle.weight = weight;
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
      this.getMotorcycle();
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
.single-motorcycle__button {
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