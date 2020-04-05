<template>
  <div class="component-container">
    <div class="searchWrapper">
      <div class="searchWrapper__inputs">
        <p>
          <input type="text" id="customer" name="customer" v-model="filteredCustomer">
          <label for="customer">Klient
            <br>
          </label>
        </p>
        <p>
          <input type="text" id="empl" name="empl" v-model="filteredEmployee">
          <label for="empl">Pracownik
            <br>
          </label>
        </p>
        <p>
          <input type="text" id="mot" name="mot" v-model="filteredMotorcycle">
          <label for="mot">Motocykl
            <br>
          </label>
        </p>
      </div>
      <div class="searchWrapper__buttons">
        <button title="Clear" class="my-button clear" @click="cleanFilteringCriteria">Wyczyść
          <font-awesome-icon class="icon" icon="eraser"/>
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
      <router-link v-if=" checkIsAdmin" tag="button" title="Add" class="my-button add" to="/addTestDrive">Dodaj
        <font-awesome-icon class="icon" icon="plus"/>
      </router-link>
      <button title="Export" class="my-button export" @click="exportTableToExcel('jazdy_testowe')">Export
        <font-awesome-icon class="icon" icon="file-export"/>
      </button>
    </div>
    <div class="table-wrapper">
      <table class="table">
        <thead>
          <tr>
            <th>Data rozp.</th>
            <th>Data zak.</th>
            <th>Klient</th>
            <th>Pracownik</th>
            <th>Motocykl</th>
          </tr>
        </thead>
        <tbody>
          <router-link tag="tr"
            v-for="testDrive in filteredTestDrives"
            :key="testDrive.id"
            :to="`/testDrive/${testDrive.id}`"
          >
            <td>{{testDrive.startDrive}}</td>
            <td>{{testDrive.endDrive}}</td>
            <td>{{testDrive.customer.firstName}}</td>
            <td>{{testDrive.employee.firstName}}</td>
            <td>{{testDrive.motorcycleDetails.motorcycleCode}}</td>
          </router-link>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { testDriveUrl } from "@/variables";
import { exportTableToExcel } from "@/helpers";

export default {
  name: "testDrives",
  data() {
    return {
      filteredCustomer: "",
      filteredEmployee: "",
      filteredMotorcycle: "",
      loading: false,
      testDrives: []
    };
  },
  methods: {
    exportTableToExcel,
    cleanFilteringCriteria() {
      this.filteredCustomer = "",
      this.filteredEmployee =  "",
      this.filteredMotorcycle =  ""
    },
    getTestDrives() {
      this.loading = true;
      this.$http
        .get(`${testDriveUrl}`)
        .then(resp => resp.json())
        .then(data => {
          this.loading = false;
          this.testDrives = data;
        })
        .catch(err => {
          this.loading = false;
          console.log(err);
        });
    }
  },
  created() {
      this.getTestDrives();
  },
  computed: {
    filteredTestDrives() {
      return this.testDrives.filter(testDrive => {
        return (
          (!this.filteredCustomer ||
            testDrive.customer.firstName
              .toLowerCase()
              .includes(this.filteredCustomer.toLowerCase())) &&
          (!this.filteredEmployee ||
            testDrive.employee.firstName
              .toLowerCase()
              .includes(this.filteredEmployee.toLowerCase())) &&
          (!this.filteredMotorcycle ||
            testDrive.motorcycleDetails.motorcycleCode
              .toLowerCase()
              .includes(this.filteredMotorcycle.toLowerCase()))
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
