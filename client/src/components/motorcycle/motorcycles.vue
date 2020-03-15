<template>
  <div class="component-container">
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
    <div class="action-buttons action-buttons__motorcycles">
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
              <img class="engine__icon" src="@/assets/images/enginefill.svg" alt="">
            </p>
            <p class="motorcycles__more--weight">
              {{ motorcycle.weight }} kg
            <img class="weight__icon" src="@/assets/images/weight.svg" alt="">
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
</style>
