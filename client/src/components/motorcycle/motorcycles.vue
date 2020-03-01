<template>
  <div class="motorcycles">
    <div class="searchWrapper">
      <div class="searchWrapper__inputs">
        <p>
          <input
            type="text"
            id="model"
            name="model"
            v-model="filteredModel"
          />
          <label for="model"
            >Model
            <br />
          </label>
        </p>
        <p>
          <input
            type="text"
            id="brand"
            name="brand"
            v-model="filteredBrand"
          />
          <label for="brand"
            >Marka
            <br />
          </label>
        </p>
        <p>
          <input
            type="text"
            id="classif"
            name="classif"
            v-model="filteredClassification"
          />
          <label for="classif"
            >Klasyfikacja
            <br />
          </label>
        </p>
        <p>
          <input
            type="number"
            id="power"
            name="power"
            v-model="filteredPower"
          />
          <label for="power"
            >Moc
            <br />
          </label>
        </p>
      </div>
      <div class="searchWrapper__buttons">
        <button
          title="Clear"
          class="my-button clear"
          @click="cleanFilteringCriteria"
        >
          Wyczyść
          <font-awesome-icon class="icon" icon="eraser" />
        </button>
      </div>
    </div>
    <div class="loader-box" v-if="loading">
      <div class="lds-ring">
        <div></div>
        <div></div>
        <div></div>
        <div></div>
      </div>
    </div>
    <div class="action-buttons">
      <router-link
        v-if="checkIsAdmin"
        tag="button"
        title="Add"
        class="my-button add"
        to="/addMotorcycle"
        >Dodaj
        <font-awesome-icon class="icon" icon="plus" />
      </router-link>
      <router-link
        tag="button"
        title="Motorcycle details"
        class="my-button details"
        to="/motorcycleDetails"
        >Motocykle na stanie
      </router-link>
    </div>

    <ul class="motorcycles__list">
      <li
        v-for="motorcycle in filteredMotorcycles"
        :key="motorcycle.id"
        class="motorcycles__item"
      >
        <div class="motorcycles__wrapper">
          <h2 v-rainbow class="motorcycles__single-title">
            {{ motorcycle.model }}
          </h2>
          <p class="motorcycles__paragraph"><strong>Marka</strong> {{ motorcycle.brand }}</p>
          <p class="motorcycles__paragraph"><strong>Klasyfikacja</strong> {{ motorcycle.classification }}</p>
          <p class="motorcycles__paragraph"><strong>Moc (km)</strong> {{ motorcycle.power }}</p>
          <p class="motorcycles__paragraph"><strong>Pojemność (cm<span class="cubic-centimeters">3</span> )</strong> {{ motorcycle.capacity }}</p>
        </div>
        <div class="motorcycles__more-wrapper">
          <router-link v-if="checkIsAdmin"
            class="motorcycles__single-link"
            :to="`/motorcycle/${motorcycle.id}/edit`"
            >Dowiedz się więcej</router-link
          >
          <span class="motorcycles__more">
            <p class="motorcycles__more--engine">
              {{ motorcycle.engineType }}
              <font-awesome-icon class="icon" icon="car-battery"/>
            </p>
            <p class="motorcycles__more--weight">
              {{ motorcycle.weight }} kg
            <font-awesome-icon class="icon" icon="weight-hanging"/>
            </p>
          </span>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import { motorcycleUrl } from "@/variables";

export default {
  name: "motorcycles",
  data() {
    return {
      filteredModel: "",
      filteredBrand: "",
      filteredClassification: "",
      filteredPower: "",
      loading: false,
      motorcycles: []
    };
  },
  directives: {
    rainbow: {
      bind(el) {
        el.style.background = `linear-gradient(to right,
        #${Math.random()
          .toString()
          .slice(3, 9)},
        #${Math.random()
          .toString()
          .slice(6, 9)}`;
      }
    }
  },
  methods: {
    cleanFilteringCriteria() {
      this.filteredModel = "";
      this.filteredBrand = "";
      this.filteredClassification = "";
      this.filteredPower = "";
    },
    getMotorcycles() {
      this.loading = true;
      this.$http
        .get(`${motorcycleUrl}`)
        .then(resp => resp.json())
        .then(data => {
          this.loading = false;
          this.motorcycles = data;
        })
        .catch(err => {
          this.loading = false;
          console.log(err);
        });
    }
  },
  singleEmployee(employeeId) {
    this.router.push({ name: "singleEmployee", params: { id: employeeId } });
  },
  created() {
    this.getMotorcycles();
  },
  computed: {
    filteredMotorcycles() {
      return this.motorcycles.filter(motorcycle => {
        return (
          (!this.filteredModel ||
            motorcycle.model
              .toLowerCase()
              .includes(this.filteredModel.toLowerCase())) &&
          (!this.filteredBrand ||
            motorcycle.brand
              .toLowerCase()
              .includes(this.filteredBrand.toLowerCase())) &&
          (!this.filteredClassification ||
            motorcycle.classification
              .toLowerCase()
              .includes(this.filteredClassification.toLowerCase())) &&
          (!this.filteredPower ||
            motorcycle.power >= Number(this.filteredPower))
        );
      });
    },
    checkIsAdmin() {
      return this.$store.getters.isAdmin;
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
@import "@/assets/styles/main.scss";

@keyframes lds-ring {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.action-buttons {
  width: 100%;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  padding-bottom: 0.8rem;
}

.clear {
  @include default-button($navy-blue);
}

.back-icon {
  margin: 0 0.5rem 0 0;
}

.icon {
  margin-left: 0.3rem;
}

.add {
  @include default-button($dark-green);
  margin-right: 0.5rem;
}

.details {
    @include default-button($dark-orange);
}

.searchWrapper {
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  width: 100%;
  margin: 0 auto 4rem;
  padding: 1rem;
  border: 1px solid #324960;

  &__inputs {
    width: 100%;
    display: flex;
    justify-content: flex-start;
    align-content: flex-start;
    flex-wrap: wrap;

    p {
      padding: 0.5rem;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
    }
  }

  &__inputs input {
    font-family: "Avenir", Helvetica, Arial, sans-serif;
    font-size: 1rem;
    color: #324960;
  }

  &__buttons {
    width: 100%;
    display: flex;
    justify-content: flex-start;
    align-content: flex-start;
    padding: 0.5rem;
  }
}

.motorcycles {
  width: 100%;
  max-width: 1000px;
  margin: 0 auto;
  padding: 0.5rem;
}

input {
  order: 1;
  margin-top: 0.5rem;
  padding: 0.3rem;
  border: none;
  background: none;
  text-align: left;
  border-bottom: 1px solid #324960f5;
}

input:focus {
  outline: none;
  border-bottom-color: #000000f5;
}

input:focus ~ label {
  color: darken(#324960f5, 80%);
}

label {
  width: 100%;
  text-align: left;
  color: #324960f5;
}

.form-control {
  padding: 0.7rem 1.5rem;
  border-color: #324960f5;
  background-color: #324960f5;
  font-size: 1rem;
  color: #fff;
  box-shadow: 0px 6px 12px rgba(24, 41, 67, 0.2);
}

.myGroup {
  text-transform: uppercase;
}

.myOption {
  text-transform: none;
}

.motorcycles__single-title {
  color: $white;
  margin: 0 0 1rem;
  padding: 3rem 0 4rem;
}

.motorcycles__paragraph {
  margin: 0;
  text-align: left;
  padding: .5rem 1.5rem;
  position: relative;
}

strong {
    display: block;
}

.cubic-centimeters {
    position: absolute;
    top: 4px;
    right: 157px;
    font-size: 0.7rem;
}

.motorcycles__single-link {
  text-decoration: none;
  color: transparent;
  background: transparent;
  cursor: pointer;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
}

.motorcycles__more-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  background: #fff;
  &::before {
    content: "";
    display: block;
    width: 90%;
    height: 0.05rem;
    margin: 0 auto;
    background: #00000020;
    position: absolute;
    bottom: 43px;
    left: 0;
    right: 0;
  }
}
.motorcycles__more {
  width: 100%;
  padding: 0.8rem 1.2rem;
  color: #00000060;
  display: flex;
  justify-content: flex-end;
  align-items: flex-end;
  &--engine {
    color: #00000060;
    padding: 0 0.5rem 0 0;
    margin: 0 .3rem 0 0;
  }
  &--weight {
      margin: 0;
      padding: 0;
  }
}
.motorcycles__list {
  list-style-type: none;
  box-shadow: 0px 6px 16px rgba(24, 41, 67, 0.2);
  padding: 0;
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}
.motorcycles__item {
  position: relative;
  width: 100%;
  max-width: 305px;
  box-shadow: 0px 6px 16px rgba(24, 41, 67, 0.2);
  overflow: hidden;
  margin: 0.7rem;
  transition: transform 0.4s ease-in-out, background 0.4s ease-in-out,
    color 0.4s ease-in-out;
}
.motorcycles__item:hover {
  transform: translateY(-2px);
}
.motorcycles__counter {
  position: absolute;
  bottom: 12px;
  left: 30px;
  &--icon {
    font-size: 20px;
    color: #00000060;
  }
  &--count {
    position: absolute;
    z-index: 5;
    top: -23px;
    left: -19px;
    color: $white;
    text-align: center;
    padding: 0.1rem;
    border-radius: 25px;
    background: #ce3333;
    overflow: hidden;
    line-height: 1.5rem;
    width: 28px;
    height: 28px;
    box-shadow: 0px 6px 16px rgba(24, 41, 67, 0.42);
  }
}

.loader-box {
  position: fixed;
  z-index: 9999;
  top: 95px;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.lds-ring {
  display: inline-block;
  position: relative;
  margin-top: 5rem;
  width: 80px;
  height: 80px;
}
.lds-ring div {
  box-sizing: border-box;
  display: block;
  position: absolute;
  width: 64px;
  height: 64px;
  margin: 8px;
  border: 8px solid #000;
  border-radius: 50%;
  animation: lds-ring 1.2s cubic-bezier(0.5, 0, 0.5, 1) infinite;
  border-color: $white transparent transparent transparent;
}
.lds-ring div:nth-child(1) {
  animation-delay: -0.45s;
}
.lds-ring div:nth-child(2) {
  animation-delay: -0.3s;
}
.lds-ring div:nth-child(3) {
  animation-delay: -0.15s;
}
</style>
