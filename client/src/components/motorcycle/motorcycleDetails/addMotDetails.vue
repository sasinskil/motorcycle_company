<template>
  <div id="addMotorcycleDetails" class="main-container--add">
     <h1 class="main-container--add__title">Dodaj nowy motocykl na stan</h1>
     
     <form v-if="!submitted" class="form">
      <h2 class="form__title--main">Dane szczegółowe</h2>
      <p class="form__wrapper">
        <label class="form__label" for="motCode">Kod motocykla:</label>
        <input class="form__input date-input" type="text" name="motCode" id="motCode" required v-model.lazy="motorcycleDetails.motorcycleCode" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="price">Cena (PLN):</label>
        <input class="form__input date-input" type="number" placeholder="0.00" step="0.01" name="price" id="price" required v-model.lazy="motorcycleDetails.price" />
      </p>
      <p class="form__wrapper">
        <label class="form__label" for="motorcycle">Motocykl:</label> 
        <select class="form__input" name="motorcycle" id="motorcycle" required v-model="motorcycleDetails.motorcycle">
          <option v-for="singleMotorcycle in motorcycles" :key="singleMotorcycle.model" :value="singleMotorcycle">{{singleMotorcycle.model}} - {{singleMotorcycle.brand}}</option>
        </select>
      </p>
      <p class="form__errors" v-if="errors.length">
        <span>Popraw następujące błędy:</span>
        <ul>
          <li v-for="error in errors" :key="error">
            {{error}}
          </li>
        </ul>
      </p>
        <button class="form__send-button" @click.prevent="post"><font-awesome-icon class="plus-icon icon" icon="plus" />Dodaj motocykl</button>
        <button class="form__clear-button" @click.prevent="clear"><font-awesome-icon class="plus-icon icon" icon="eraser" />Wyczyść</button>
      </form>

    <div class="after-post" v-if="submitted">
      <h2 class="after-post__title">Motocykl został dodany na stan!</h2>
    </div>

    <div class="preview">
      <h3 class="preview__header">Podgląd</h3>
      <p class="preview__data">
        Kod motocykla:
        <span class="preview__value">{{motorcycleDetails.motorcycleCode}}</span>
      </p>
      <p class="preview__data">
        Cena:
        <span class="preview__value">{{motorcycleDetails.price}}</span>
      </p>
      <p class="preview__data">
        Model:
        <span class="preview__value">{{motorcycleDetails.motorcycle.model}}</span>
      </p>
      <p class="preview__data">
        Marka:
        <span class="preview__value">{{motorcycleDetails.motorcycle.brand}}</span>
      </p>
      <p class="preview__data">
        Klasyfikacja:
        <span class="preview__value">{{motorcycleDetails.motorcycle.classification}}</span>
      </p>
      <p class="preview__data">
        Moc (km):
        <span class="preview__value">{{motorcycleDetails.motorcycle.power}}</span>
      </p>
      <p class="preview__data">
        Pojemność silnika (cm <span class="cubic-centimeters">3</span> ):
        <span class="preview__value">{{motorcycleDetails.motorcycle.capacity}}</span>
      </p>
      <p class="preview__data">
        Rodzaj silnika:
        <span class="preview__value">{{motorcycleDetails.motorcycle.engineType}}</span>
      </p>
      <p class="preview__data">
        Waga (kg):
        <span class="preview__value">{{motorcycleDetails.motorcycle.weight}}</span>
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
import { motorcycleDetailsUrl, motorcycleUrl } from "@/variables";
import InfoModal from "@/components/modal/InfoModal";

export default {
  name: "addMotDetails",
  components: {
    InfoModal
  },
  data() {
    return {
        motorcycleDetails: {
            motorcycleCode: '',
            price: '',
            motorcycle: {},
        },
        motorcycles: [],
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
        this.$router.push('/motorcycleDetails');
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
      if(this.motorcycleDetails.motorcycleCode && this.motorcycleDetails.price && this.motorcycleDetails.motorcycle) {
        return true;
      }

      if(!this.motorcycleDetails.motorcycleCode) {
        this.errors.push('Unikalny kod motocykla jest wymagany!');
      }
      if(!this.motorcycleDetails.price) {
        this.errors.push('Cena jest wymagana!');
      }
      if(!this.motorcycleDetails.motorcycle || Object.keys(this.motorcycleDetails.motorcycle).length === 0) {
        this.errors.push('Motocykl jest wymagany!');
      }
    },
    post() {
      const isValid = this.checkForm();
      if(isValid) {
        this.$http.post(`${motorcycleDetailsUrl}`, this.motorcycleDetails)
        .then(() => {
          this.submitted = true;
          setTimeout(() => {
            this.$router.push('/motorcycleDetails');
          }, 1500);
        })
        .catch(() => {
           this.showModal = true;
           this.errors.push("Podałeś/aś niepoprawne dane, sprawdź ponownie, upewnij się czy podany kod motocykla nie jest już zajęty!");
          })
      } else {
        this.showModal = true;
      }
    },
     clear() {
      this.motorcycleDetails.price = "",
      this.motorcycleDetails.motorcycle = ""
    },
    close() {
      this.showModal = false;
    },
    getMotorcycles() {
      this.$http
        .get(`${motorcycleUrl}`)
        .then(resp => resp.json())
        .then(data => {
          this.motorcycles = data;
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  created() {
      this.getMotorcycles();
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

.cubic-centimeters {
    position: absolute;
    top: -4px;
    left: 162px;
    font-size: 0.7rem;
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
  &__data {
      position: relative;
  }
  &__content {
    display: block;
  }
  &__value {
    color: #2c3e508e;
  }
}
</style>