<template>
  <div class="motorcycleDetails">
    <div class="searchWrapper">
      <div class="searchWrapper__inputs">
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
            <th>Kod motocykla</th>
            <th>Cena(PLN)</th>
            <th>Model</th>
            <th>Marka</th>
            <th>Klasyfikacja</th>
            <th>Moc(km)</th>
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
          </router-link>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { motorcycleDetailsUrl } from "@/variables";

export default {
  name: "motorcycleDetails",
  data() {
    return {
      filteredPrice: "",
      filteredModel: "",
      filteredBrand: "",
      filteredClassification: "",
      filteredPower: "",
      loading: false,
      motorcycleDetails: []
    };
  },
  methods: {
    cleanFilteringCriteria() {
      this.filteredPrice = "";
      this.filteredModel = "";
      this.filteredBrand = "";
      this.filteredClassification = "";
      this.filteredPower = "";
    },
    exportTableToExcel(filename = "") {
      let downloadLink;
      const dataType = "application/vnd.ms-excel";
      const tableSelect = document.querySelector(".table");
      const tableHTML = tableSelect.outerHTML.replace(/ /g, "%20");

      const currentDate = this.getFormattedDate();

      // Specify file name
      filename = filename
        ? `${filename}_${currentDate}.xls`
        : `some_data_${currentDate}.xls`;

      // Create download link element
      downloadLink = document.createElement("a");

      document.body.appendChild(downloadLink);

      if (navigator.msSaveOrOpenBlob) {
        var blob = new Blob(["\ufeff", tableHTML], {
          type: dataType
        });
        navigator.msSaveOrOpenBlob(blob, filename);
      } else {
        // Create a link to the file
        downloadLink.href = "data:" + dataType + ", " + tableHTML;

        // Setting the file name
        downloadLink.download = filename;

        //triggering the function
        downloadLink.click();
      }
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
          (!this.filteredPrice ||
            motDetail.price >= Number(this.filteredPrice))&&
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
  justify-content: space-between;
  align-items: center;
  padding-bottom: 0.8rem;
}

.export {
  @include default-button($dark-blue);
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
}

.table-wrapper {
  width: 100%;
  box-shadow: 0px 6px 16px rgba(24, 41, 67, 0.2);
}

table {
  width: 100%;
  display: block;
  background: #fff;
}
thead {
  display: inline-block;
  width: 100%;
}

thead tr,
tbody tr {
  display: flex;
  justify-content: space-around;
  align-items: center;
}

tbody {
  height: 100%;
  max-height: 450px;
  display: inline-block;
  width: 100%;
  overflow: auto;
}

table td,
table th {
  text-align: center;
  padding: 1rem;
  width: 100%;
}

table td {
  font-size: 0.9rem;
}

table tbody tr:nth-child(odd) {
  background-color: #f5f5f5;
}

table tbody tr {
  cursor: pointer;
}

table tbody tr:hover {
  background-color: #8e8b8b36;
}

table thead tr {
  background: #324960;
}

table thead th {
  padding: 1.3rem;
  font-weight: bold;
  font-size: 15px;
  color: #ffffff;
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

    &--search {
      box-shadow: 0px 6px 16px rgba(24, 41, 67, 0.2);
      font-family: "Avenir", Helvetica, Arial, sans-serif;
      font-size: 15px;
      padding: 0.5rem 2rem;
      cursor: pointer;
      background: none;
      border: 1px solid #4fc08d;
      color: #2c3e50;
      transition: background 0.4s ease-in-out, color 0.4s ease-in-out;
    }

    &--clear {
      box-shadow: 0px 6px 16px rgba(24, 41, 67, 0.2);
      font-family: "Avenir", Helvetica, Arial, sans-serif;
      font-size: 15px;
      padding: 0.5rem 2rem;
      cursor: pointer;
      background: none;
      border: 1px solid #2c3e50;
      color: #2c3e50;
      //margin-left: 1rem;
      transition: background 0.4s ease-in-out, color 0.4s ease-in-out;

      &:hover {
        background: #2c3e50;
        color: #fff;
      }
    }
  }
}

.motorcycleDetails {
  width: 100%;
  max-width: 1000px;
  margin: 0 auto;
  padding: 0.5rem;
}

p {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
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

@media (max-width: 640px) {
  .fl-table thead th {
    padding: 1rem 0.5rem;
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
