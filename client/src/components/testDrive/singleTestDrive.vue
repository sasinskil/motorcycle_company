<template>
  <div id="singleTestDrive" class="main-container--testDrive single-testDrive">
    <button class="single-testDrive__button" @click="backToList">
      <font-awesome-icon
        class="arrow-left-icon icon-arrow"
        icon="arrow-left"
      />Powrót
    </button>
    <div class="single-testDrive--box">
      <h1 class="single-testDrive__title">
        Dane szczegółowe
      </h1>
      <p class="single-testDrive__paragraph">
        <strong>Data rozpoczęcia:</strong> {{ testDrive.startDrive }}
      </p>
      <p class="single-testDrive__paragraph">
        <strong>Data zakończenia:</strong> {{ testDrive.endDrive }}
      </p>
      <h2>Klient</h2>
        <p class="single-testDrive__paragraph">
            <strong>Dane podstawowe:</strong> {{ testDrive.customer.firstName }} {{ testDrive.customer.lastName }}
        </p>
      <h2>Pracownik</h2>
        <p class="single-testDrive__paragraph">
            <strong>Dane podstawowe:</strong> {{ testDrive.employee.firstName }} {{ testDrive.employee.firstName }}: {{ testDrive.employee.position }} 
        </p>
      <h2>Motocykl</h2>
        <p class="single-testDrive__paragraph">
            <strong>Dane podstawowe:</strong> {{ testDrive.motorcycleDetails.motorcycleCode }}: {{ testDrive.motorcycleDetails.motorcycle.model }}
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
import { testDriveUrl } from "@/variables";
import InfoModal from "@/components/modal/InfoModal";
import ActionModal from "@/components/modal/ActionModal";
export default {
  name: "singleTestDrive",
  components: {
      InfoModal,
      ActionModal
  },
  data() {
    return {
      testDriveId: this.$route.params.id,
      testDrive: {
          id: null,
          startDrive: '',
          endDrive: '',
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
      this.$router.replace(`/testDrive/${this.testDrive.id}/edit`);
    },
    doAction() {
               this.$http
          .delete(`${testDriveUrl}/${this.testDriveId}`)
          .then(() => {
            this.showActionModal = false;

            setTimeout(() => {
                this.$router.push("/testDrives");
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
        this.modalBodyContent = `Czy na pewno chcesz usunąć jazdę testową?`;
        this.showActionModal = true;
    },
    backToList() {
      this.$router.go(-1);
    },
    getEmployee() {
      this.$http
        .get(`${testDriveUrl}/${this.testDriveId}`)
        .then(response => response.json())
        .then(object => {
          this.testDrive = object;
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
      this.getEmployee();
  }
};
</script>

<style lang="scss" scoped>
</style>
