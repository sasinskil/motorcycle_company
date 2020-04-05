<template>
  <div class="component-container">
    <div class="searchWrapper">
      <div class="searchWrapper__inputs">
        <p>
          <input type="text" id="name" name="name" v-model="filteredFirstName">
          <label for="name">Imię
            <br>
          </label>
        </p>
        <p>
          <input type="text" id="lastName" name="lastName" v-model="filteredLastName">
          <label for="lastName">Nazwisko
            <br>
          </label>
        </p>
        <p>
          <input type="text" id="position" name="position" v-model="filteredPosition">
          <label for="position">Stanowisko
            <br>
          </label>
        </p>
        <p>
          <input type="date" id="emplDate" name="emplDate" v-model="filteredEmpDate">
          <label for="emplDate">Data zatrudnienia
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
      <router-link v-if=" checkIsAdmin" tag="button" title="Add" class="my-button add" to="/addEmployee">Dodaj
        <font-awesome-icon class="icon" icon="plus"/>
      </router-link>
      <button title="Export" class="my-button export" @click="exportTableToExcel('pracownicy')">Export
        <font-awesome-icon class="icon" icon="file-export"/>
      </button>
    </div>
    <div class="table-wrapper">
      <table class="table">
        <thead>
          <tr>
            <th>Imię</th>
            <th>Nazwisko</th>
            <th>Stanowisko</th>
            <th>Data zatr.</th>
          </tr>
        </thead>
        <tbody>
          <router-link tag="tr"
            v-for="employee in filteredEmployees"
            :key="employee.id"
            :to="`/employee/${employee.id}`"
          >
            <td>{{employee.firstName}}</td>
            <td>{{employee.lastName}}</td>
            <td>{{employee.position}}</td>
            <td>{{employee.dateOfEmployment}}</td>
          </router-link>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { employeeUrl } from "@/variables";
import { exportTableToExcel } from "@/helpers";

export default {
  name: "employees",
  data() {
    return {
      filteredFirstName: "",
      filteredLastName: "",
      filteredPosition: "",
      filteredEmpDate: "",
      filteredTermDate: "",
      loading: false,
      employees: []
    };
  },
  methods: {
    exportTableToExcel,
    cleanFilteringCriteria() {
      this.filteredFirstName = "";
      this.filteredLastName = "";
      this.filteredPosition = "";
      this.filteredEmpDate = "";
      this.filteredTermDate = "";
    },
    getEmployees() {
      this.loading = true;
      this.$http
        .get(`${employeeUrl}`)
        .then(resp => resp.json())
        .then(data => {
          this.loading = false;
          this.employees = data;
        })
        .catch(err => {
          this.loading = false;
          console.log(err);
        });
    }
  },
  singleEmployee(employeeId) {
    this.router.push({ name: 'singleEmployee', params: {id: employeeId}});
   },
  created() {
      this.getEmployees();
  },
  computed: {
    filteredEmployees() {
      return this.employees.filter(employee => {
        return (
          (!this.filteredFirstName ||
            employee.firstName
              .toLowerCase()
              .includes(this.filteredFirstName.toLowerCase())) &&
          (!this.filteredLastName ||
            employee.lastName
              .toLowerCase()
              .includes(this.filteredLastName.toLowerCase())) &&
          (!this.filteredPosition ||
            employee.position
              .toLowerCase()
              .includes(this.filteredPosition.toLowerCase())) &&
          (!this.filteredEmpDate ||
            employee.dateOfEmployment
              .toLowerCase()
              .includes(this.filteredEmpDate.toLowerCase())) &&
          (!this.filteredTermDate ||
            employee.dateOfTermination
              .toLowerCase()
              .includes(this.filteredTermDate.toLowerCase()))
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
