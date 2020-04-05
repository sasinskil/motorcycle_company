<template>
  <div class="component-container component-container--motDetails">
    <button class="main-container--add__arrow motDetails" @click="stepBack">
      <font-awesome-icon class="plus-icon icon" icon="arrow-left" />
    </button>
    <div class="searchWrapper">
      <div class="searchWrapper__inputs">
        <p>
          <input type="text" id="motCode" name="motCode" v-model="filteredMotCode" />
          <label for="motCode"
            >Kod motocykla
            <br />
          </label>
        </p>
        <p>
          <input type="text" id="price" name="price" v-model="filteredPrice" />
          <label for="price"
            >Cena
            <br />
          </label>
        </p>
        <p>
          <input type="text" id="model" name="model" v-model="filteredModel" />
          <label for="model"
            >Model
            <br />
          </label>
        </p>
        <p>
          <input type="text" id="brand" name="brand" v-model="filteredBrand" />
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
        to="/addMotDetails"
        >Dodaj
        <font-awesome-icon class="icon" icon="plus" />
      </router-link>
      <button
        title="Export"
        class="my-button export"
        @click="exportTableToExcel('motocykle')"
      >
        Export
        <font-awesome-icon class="icon" icon="file-export" />
      </button>
    </div>
    <div class="table-wrapper">
      <table class="table">
        <thead>
          <tr>
            <th>Kod</th>
            <th>Cena(PLN)</th>
            <th>Model</th>
            <th>Marka</th>
            <th>Klasyfikacja</th>
            <th>Moc(km)</th>
            <th>Sprzedany</th>
          </tr>
        </thead>
        <tbody>
          <router-link
            tag="tr"
            v-for="motorcycleDetail in filteredMotorcycleDetails"
            :key="motorcycleDetail.id"
            :to="`/motorcycleDetails/${motorcycleDetail.id}`"
          >
            <td>{{ motorcycleDetail.motorcycleCode }}</td>
            <td>{{ motorcycleDetail.price }}</td>
            <td>{{ motorcycleDetail.motorcycle.model }}</td>
            <td>{{ motorcycleDetail.motorcycle.brand }}</td>
            <td>{{ motorcycleDetail.motorcycle.classification }}</td>
            <td>{{ motorcycleDetail.motorcycle.power }}</td>
            <td>{{ motorcycleDetail.isSold | checkIsSold}}</td>
          </router-link>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { motorcycleDetailsUrl } from "@/variables";
import { exportTableToExcel } from "@/helpers";

export default {
  name: "motorcycleDetails",
  data() {
    return {
      filteredMotCode: "",
      filteredPrice: "",
      filteredModel: "",
      filteredBrand: "",
      filteredClassification: "",
      filteredPower: "",
      loading: false,
      motorcycleDetails: []
    };
  },
  filters: {
    checkIsSold(value) {
      if (value === false) {
        return `Nie`;
      } else {
        return 'Tak'
      }
    }
  },
  methods: {
    exportTableToExcel,
    stepBack() {
      this.$router.push("/motorcycles");
    },
    cleanFilteringCriteria() {
      this.filteredMotCode = "",
      this.filteredPrice = "";
      this.filteredModel = "";
      this.filteredBrand = "";
      this.filteredClassification = "";
      this.filteredPower = "";
    },
    getMotorcycleDetails() {
      this.loading = true;
      this.$http
        .get(`${motorcycleDetailsUrl}`)
        .then(resp => resp.json())
        .then(data => {
          this.loading = false;
          this.motorcycleDetails = data;
        })
        .catch(err => {
          this.loading = false;
          console.log(err);
        });
    }
  },
  created() {
    this.getMotorcycleDetails();
  },
  computed: {
    filteredMotorcycleDetails() {
      return this.motorcycleDetails.filter(motDetail => {
        return (
          (!this.filteredMotCode ||
            motDetail.motorcycleCode
              .toLowerCase()
              .includes(this.filteredMotCode.toLowerCase())) &&
          (!this.filteredPrice ||
            motDetail.price >= Number(this.filteredPrice)) &&
          (!this.filteredModel ||
            motDetail.motorcycle.model
              .toLowerCase()
              .includes(this.filteredModel.toLowerCase())) &&
          (!this.filteredBrand ||
            motDetail.motorcycle.brand
              .toLowerCase()
              .includes(this.filteredBrand.toLowerCase())) &&
          (!this.filteredClassification ||
            motDetail.motorcycle.classification
              .toLowerCase()
              .includes(this.filteredClassification.toLowerCase())) &&
          (!this.filteredPower ||
            motDetail.motorcycle.power >= Number(this.filteredPower))
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
