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
          <input type="text" id="locality" name="locality" v-model="filteredLocality">
          <label for="locality">Miejsce zamieszkania
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
      <router-link v-if=" checkIsAdmin" tag="button" title="Add" class="my-button add" to="/addCustomer">Dodaj
        <font-awesome-icon class="icon" icon="plus"/>
      </router-link>
      <button v-if="customers.length > 0" title="Export" class="my-button export" @click="exportTableToExcel('klienci')">Export
        <font-awesome-icon class="icon" icon="file-export"/>
      </button>
    </div>
    <div v-if="customers.length > 0" class="table-wrapper">
      <table class="table">
        <thead>
          <tr>
            <th>Imię</th>
            <th>Nazwisko</th>
            <th>Data ur.</th>
            <th>Miejsca zamieszkania</th>
          </tr>
        </thead>
        <tbody>
          <router-link tag="tr"
            v-for="customer in filteredCustomers"
            :key="customer.id"
            :to="`/customer/${customer.id}`"
          >
            <td>{{customer.firstName}}</td>
            <td>{{customer.lastName}}</td>
            <td>{{customer.birthDate}}</td>
            <td>{{customer.address.locality}}</td>
          </router-link>
        </tbody>
      </table>
    </div>

    <div class="empty-table" v-else>
      <span class="empty-table__header">Brak pozycji</span>
    </div>

  </div>
</template>

<script>
import { customerUrl } from "@/variables";
import { exportTableToExcel } from "@/helpers";

export default {
  name: "customers",
  data() {
    return {
      filteredFirstName: "",
      filteredLastName: "",
      filteredLocality: "",
      loading: false,
      customers: [],
    };
  },
  methods: {
    exportTableToExcel,
    cleanFilteringCriteria() {
      this.filteredFirstName = "";
      this.filteredLastName = "";
      this.filteredLocality = "";
    },
    getCustomers() {
      this.loading = true;
      this.$http
        .get(`${customerUrl}`)
        .then(resp => resp.json())
        .then(data => {
          this.loading = false;
          this.customers = data;
        })
        .catch(err => {
          this.loading = false;
          console.log(err);
        });
    }
  },
  singleCustomer(customerId) {
    this.router.push({ name: 'singleCustomer', params: {id: customerId}});
   },
  created() {
      this.getCustomers();
  },
  computed: {
    filteredCustomers() {
      return this.customers.filter(customer => {
        return (
          (!this.filteredFirstName ||
            customer.firstName
              .toLowerCase()
              .includes(this.filteredFirstName.toLowerCase())) &&
          (!this.filteredLastName ||
            customer.lastName
              .toLowerCase()
              .includes(this.filteredLastName.toLowerCase())) &&
          (!this.filteredLocality ||
            customer.address.locality
              .toLowerCase()
              .includes(this.filteredLocality.toLowerCase()))
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
