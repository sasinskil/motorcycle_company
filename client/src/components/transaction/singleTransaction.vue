<template>
  <div id="singleTransaction" class="main-container--transaction single-transaction">
    <button class="single-transaction__button" @click="backToList">
      <font-awesome-icon
        class="arrow-left-icon icon-arrow"
        icon="arrow-left"
      />Powrót
    </button>
    <div class="single-transaction--box">
      <h1 class="single-transaction__title">
        Dane szczegółowe
      </h1>
      <p class="single-transaction__paragraph">
        <strong>Operacja:</strong> {{ transaction.operation }}
      </p>
      <p class="single-transaction__paragraph">
        <strong>Cena (PLN):</strong> {{ transaction.price }}
      </p>
      <p class="single-transaction__paragraph">
        <strong>Data operacji:</strong> {{ transaction.transactionTime }}
      </p>
      <h2>Klient</h2>
        <p class="single-transaction__paragraph">
            <strong>Dane podstawowe:</strong> {{ transaction.customer.firstName }} {{ transaction.customer.lastName }}
        </p>
      <h2>Pracownik</h2>
        <p class="single-transaction__paragraph">
            <strong>Dane podstawowe:</strong> {{ transaction.employee.firstName }} {{ transaction.employee.firstName }}: {{ transaction.employee.position }} 
        </p>
      <h2>Motocykl</h2>
        <p class="single-transaction__paragraph">
            <strong>Dane podstawowe:</strong> {{ transaction.motorcycleDetails.motorcycleCode }}: {{ transaction.motorcycleDetails.motorcycle.model }}
        </p>
    </div>
    <div class="buttons-wrapper">
      <button
        title="Edytuj"
        v-if="checkIsAdmin"
        class="my-button edit"
        @click="edit"
      >
        Edytuj
        <font-awesome-icon class="icon" icon="eraser" />
      </button>
      <button
        title="Usuń"
        v-if="checkIsAdmin"
        class="my-button delete"
        @click="deleteEmployee"
      >
        Usuń
        <font-awesome-icon class="icon" icon="trash-alt" />
      </button>
    </div>
    <InfoModal
      v-if="showInfoModal"
      :headerText="modalHeaderContent"
      :bodyText="modalBodyContent"
      @confirm="close"
    />
    <ActionModal
      v-if="showActionModal"
      :headerText="modalHeaderContent"
      :bodyText="modalBodyContent"
      @action="doAction"
      @cancel="close"
    />
  </div>
</template>

<script>
import { transactionUrl } from "@/variables";
import InfoModal from "@/components/modal/InfoModal";
import ActionModal from "@/components/modal/ActionModal";
export default {
  name: "singleTransaction",
  components: {
      InfoModal,
      ActionModal
  },
  data() {
    return {
      transactionId: this.$route.params.id,
      transaction: {
          id: null,
          operation: '',
          price: '',
          transactionTime: '',
          customer: {
            id: null,
            firstName: '',
            lastName: '',
            birthDate: '',
            address: {
                id: null,
                locality: '',
                street: '',
                streetNumber: '',
                zipCode: '',
            },
          },
          employee: {
            id: null,
            firstName: '',
            lastName: '',
            birthDate: '',
            position: '',
            dateOfEmployment: '',
            dateOfTermination: '',
            address: {
                id: null,
                locality: '',
                street: '',
                streetNumber: '',
                zipCode: '',
            },
          },
          motorcycleDetails: {
                id: null,
                motorcycleCode: '',
                price: '',
                motorcycle: {
                        id: null,
                        model: '',
                        brand: '',
                        classification: '',
                        engineType: '',
                        capacity: '',
                        power: '',
                        weight:'',
                },
          },
      },
      modalHeaderContent: "",
      modalBodyContent: "",
      showInfoModal: false,
      showActionModal: false,
    };
  },
  methods: {
     close() {
      this.showInfoModal = false;
      this.showActionModal = false;
    },
    edit() {
      this.$router.replace(`/transaction/${this.transaction.id}/edit`);
    },
    doAction() {
            this.$http
          .delete(`${transactionUrl}/${this.transactionId}`)
          .then(() => {
            this.showActionModal = false;

            setTimeout(() => {
                this.$router.push("/transactions");
            }, 300);
          })
          .catch(() => {
            this.showActionModal = false;
            this.modalHeaderContent = "Uwaga!";
            this.modalBodyContent = "Coś poszło nie tak!";
            this.showInfoModal = true;
          });
    },
    deleteEmployee() {
        this.modalHeaderContent = "Uwaga!";
        this.modalBodyContent = `Czy na pewno chcesz usunąć transakcje?`;
        this.showActionModal = true;
    },
    backToList() {
      this.$router.go(-1);
    },
    getTransaction() {
      this.$http
        .get(`${transactionUrl}/${this.transactionId}`)
        .then(response => response.json())
        .then(object => {
          this.transaction = object;
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  computed: {
    checkIsAdmin() {
      return this.$store.getters.isAdmin;
    }
  },
  created() {
      this.getTransaction();
  }
};
</script>

<style lang="scss" scoped>
</style>
