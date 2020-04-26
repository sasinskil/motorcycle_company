<template>
  <div id="singleService" class="main-container--service single-service">
    <button class="single-service__button" @click="backToList">
      <font-awesome-icon
        class="arrow-left-icon icon-arrow"
        icon="arrow-left"
      />Powrót
    </button>
    <div class="single-service--box">
      <h1 class="single-service__title">
        Dane szczegółowe
      </h1>
      <p class="single-service__paragraph">
        <strong>Operacja:</strong> {{ service.operation }}
      </p>
      <p class="single-service__paragraph">
        <strong>Cena (PLN):</strong> {{ service.price }}
      </p>
      <p class="single-service__paragraph">
        <strong>Data rozpoczęcia serwisu:</strong> {{ service.startWorkingDate }}
      </p>
      <p class="single-service__paragraph">
        <strong>Data zakończenia serwisu:</strong> {{ service.endWorkingDate }}
      </p>
      <h2>Klient</h2>
        <p class="single-service__paragraph">
            <strong>Dane podstawowe:</strong> {{ service.customer.firstName }} {{ service.customer.lastName }}
        </p>
      <h2>Pracownik</h2>
        <p class="single-service__paragraph">
            <strong>Dane podstawowe:</strong> {{ service.employee.firstName }} {{ service.employee.firstName }}: {{ service.employee.position }} 
        </p>
      <h2>Motocykl</h2>
        <p class="single-service__paragraph">
            <strong>Dane podstawowe:</strong> {{ service.motorcycleDetails.motorcycleCode }}: {{ service.motorcycleDetails.motorcycle.model }}
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
import { serviceUrl } from "@/variables";
import InfoModal from "@/components/modal/InfoModal";
import ActionModal from "@/components/modal/ActionModal";
export default {
  name: "singleService",
  components: {
      InfoModal,
      ActionModal
  },
  data() {
    return {
      serviceId: this.$route.params.id,
      service: {
          id: null,
          operation: '',
          price: '',
          startWorkingDate: '',
          endWorkingDate: '',
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
      this.$router.replace(`/service/${this.service.id}/edit`);
    },
    doAction() {
            this.$http
          .delete(`${serviceUrl}/${this.serviceId}`)
          .then(() => {
            this.showActionModal = false;

            setTimeout(() => {
                this.$router.push("/services");
            }, 300);
          })
          .catch(() => {
            this.showActionModal = false;
            this.modalHeaderContent = "Uwaga";
            this.modalBodyContent = "Coś poszło nie tak!";
            this.showInfoModal = true;
          });
    },
    deleteEmployee() {
        this.modalHeaderContent = "Uwaga";
        this.modalBodyContent = `Czy na pewno chcesz usunąć serwis?`;
        this.showActionModal = true;
    },
    backToList() {
      this.$router.go(-1);
    },
    getService() {
      this.$http
        .get(`${serviceUrl}/${this.serviceId}`)
        .then(response => response.json())
        .then(object => {
          this.service = object;
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
      this.getService();
  }
};
</script>

<style lang="scss" scoped>
</style>
