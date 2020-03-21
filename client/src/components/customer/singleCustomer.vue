<template>
  <div id="singleCustomer" class="main-container--customer single-customer">
    <button class="single-customer__button" @click="backToList">
      <font-awesome-icon
        class="arrow-left-icon icon-arrow"
        icon="arrow-left"
      />Powrót
    </button>
    <div class="single-customer--box">
      <h1 class="single-customer__title">
        Dane szczegółowe
      </h1>
      <p class="single-customer__paragraph">
        <strong>Imię:</strong> {{ customer.firstName }}
      </p>
      <p class="single-customer__paragraph">
        <strong>Nazwisko:</strong> {{ customer.lastName }}
      </p>
      <p class="single-customer__paragraph">
        <strong>Data urodzin:</strong> {{ customer.birthDate | format-date-short }}
      </p>
      <h2 class="single-customer__title--address">Adres zamieszkania</h2>
      <p class="single-customer__paragraph">
        <strong>Kod pocztowy/Miejscowość:</strong> {{ customer.address.zipCode }} {{ customer.address.locality }}
      </p>
      <p class="single-customer__paragraph">
        <strong>Ulica:</strong> {{ customer.address.street }} {{ customer.address.streetNumber }}
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
        @click="deleteCustomer"
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
import { customerUrl } from "@/variables";
import InfoModal from "@/components/modal/InfoModal";
import ActionModal from "@/components/modal/ActionModal";
export default {
  name: "singleCustomer",
  components: {
      InfoModal,
      ActionModal
  },
  data() {
    return {
      customerId: this.$route.params.id,
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
      this.$router.replace(`/customer/${this.customer.id}/edit`);
    },
    doAction() {
               this.$http
          .delete(`${customerUrl}/${this.customerId}`)
          .then(() => {
            this.showActionModal = false;

            setTimeout(() => {
                this.$router.push("/customers");
            }, 300);
          })
          .catch(() => {
            this.showActionModal = false;
            this.modalHeaderContent = "Uwaga!";
            this.modalBodyContent = "Klient nie może zostać usunięty, upewnij się, czy nie korzystał z jednej z usług salonu!";
            this.showInfoModal = true;
          });
    },
    deleteCustomer() {
        this.modalHeaderContent = "Uwaga!";
        this.modalBodyContent = `Czy na pewno chcesz usunąć klienta ?`;
        this.showActionModal = true;
    },
    backToList() {
      this.$router.go(-1);
    },
    getCustomer() {
      this.$http
        .get(`${customerUrl}/${this.customerId}`)
        .then(response => response.json())
        .then(object => {
          this.customer = object;
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
      this.getCustomer();
  }
};
</script>

<style lang="scss" scoped>
</style>
