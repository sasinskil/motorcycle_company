<template>
  <div class="component-container">
    <div class="searchWrapper">
      <div class="searchWrapper__inputs">
        <p>
          <input
            type="text"
            id="operation"
            name="operation"
            v-model="filteredOperation"
          />
          <label for="operation"
            >Operacja
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
          <input
            type="text"
            id="customer"
            name="customer"
            v-model="filteredCustomer"
          />
          <label for="customer"
            >Klient
            <br />
          </label>
        </p>
        <p>
          <input type="text" id="empl" name="empl" v-model="filteredEmployee" />
          <label for="empl"
            >Pracownik
            <br />
          </label>
        </p>
        <p>
          <input type="text" id="mot" name="mot" v-model="filteredMotorcycle" />
          <label for="mot"
            >Motocykl
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
        to="/addTransaction"
        >Dodaj
        <font-awesome-icon class="icon" icon="plus" />
      </router-link>
      <button
        title="Export"
        class="my-button export"
        @click="exportTableToExcel('transakcje')"
      >
        Export
        <font-awesome-icon class="icon" icon="file-export" />
      </button>
    </div>
    <div class="table-wrapper">
      <table class="table">
        <thead>
          <tr>
            <th>Operacja</th>
            <th>Cena(PLN)</th>
            <th>Data tran.</th>
            <th>Klient</th>
            <th>Pracownik</th>
            <th>Motocykl</th>
          </tr>
        </thead>
        <tbody>
          <router-link
            tag="tr"
            v-for="transaction in filteredTransactions"
            :key="transaction.id"
            :to="`/transaction/${transaction.id}`"
          >
            <td>{{ transaction.operation }}</td>
            <td>{{ transaction.price }}</td>
            <td>{{ transaction.transactionTime }}</td>
            <td>{{ transaction.customer.firstName }}</td>
            <td>{{ transaction.employee.firstName }}</td>
            <td>{{ transaction.motorcycleDetails.motorcycleCode }}</td>
          </router-link>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { transactionUrl } from "@/variables";

export default {
  name: "transactions",
  data() {
    return {
      filteredOperation: "",
      filteredPrice: "",
      filteredCustomer: "",
      filteredEmployee: "",
      filteredMotorcycle: "",
      loading: false,
      transactions: []
    };
  },
  methods: {
    cleanFilteringCriteria() {
      (this.filteredOperation = ""),
        (this.filteredPrice = ""),
        (this.filteredCustomer = ""),
        (this.filteredEmployee = ""),
        (this.filteredMotorcycle = "");
    },
    getFormattedDate() {
      const today = new Date();
      const year = today.getFullYear();
      const month =
        today.getMonth() + 1 < 10
          ? `0${today.getMonth() + 1}`
          : `${today.getMonth() + 1}`;
      const day =
        today.getDate() < 10 ? `0${today.getDate()}` : `${today.getDate()}`;

      let myDateString = `${year}-${month}-${day}`;

      return myDateString;
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
    getTransactions() {
      this.loading = true;
      this.$http
        .get(`${transactionUrl}`)
        .then(resp => resp.json())
        .then(data => {
          this.loading = false;
          this.transactions = data;
        })
        .catch(err => {
          this.loading = false;
          console.log(err);
        });
    }
  },
  created() {
    this.getTransactions();
  },
  computed: {
    filteredTransactions() {
      return this.transactions.filter(transaction => {
        return (
          (!this.filteredOperation ||
            transaction.operation
              .toLowerCase()
              .includes(this.filteredOperation.toLowerCase())) &&
          (!this.filteredPrice ||
            transaction.price >= Number(this.filteredPrice)) &&
          (!this.filteredCustomer ||
            transaction.customer.firstName
              .toLowerCase()
              .includes(this.filteredCustomer.toLowerCase())) &&
          (!this.filteredEmployee ||
            transaction.employee.firstName
              .toLowerCase()
              .includes(this.filteredEmployee.toLowerCase())) &&
          (!this.filteredMotorcycle ||
            transaction.motorcycleDetails.motorcycleCode
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
