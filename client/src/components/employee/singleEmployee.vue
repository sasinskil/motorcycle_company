<template>
  <div id="singleEmployee" class="main-container--employee single-employee">
    <button class="single-employee__button" @click="backToList">
      <font-awesome-icon
        class="arrow-left-icon icon-arrow"
        icon="arrow-left"
      />Powrót
    </button>
    <div class="single-employee--box">
      <h1 class="single-employee__title">
        Dane szczegółowe
      </h1>
      <p class="single-employee__paragraph">
        <strong>Imię:</strong> {{ employee.firstName }}
      </p>
      <p class="single-employee__paragraph">
        <strong>Nazwisko:</strong> {{ employee.lastName }}
      </p>
      <p class="single-employee__paragraph">
        <strong>Stanowisko:</strong> {{ employee.position }}
      </p>
      <p class="single-employee__paragraph">
        <strong>Data zatrudnienia:</strong> {{ employee.dateOfEmployment }}
      </p>
      <p class="single-employee__paragraph">
        <strong>Data zwolnienia:</strong> {{ employee.dateOfTermination }}
      </p>
      <p class="single-employee__paragraph">
        <strong>Data urodzin:</strong> {{ employee.birthDate | format-date-short }}
      </p>
      <h2 class="single-employee__title--address">Adres zamieszkania</h2>
      <p class="single-employee__paragraph">
        <strong>Kod pocztowy/Miejscowość:</strong> {{ employee.address.zipCode }} {{ employee.address.locality }}
      </p>
      <p class="single-employee__paragraph">
        <strong>Ulica:</strong> {{ employee.address.street }} {{ employee.address.streetNumber }}
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
import { employeeUrl } from "@/variables";
import InfoModal from "@/components/modal/InfoModal";
import ActionModal from "@/components/modal/ActionModal";
export default {
  name: "singleEmployee",
  components: {
      InfoModal,
      ActionModal
  },
  data() {
    return {
      employeeId: this.$route.params.id,
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
      this.$router.replace(`/employee/${this.employee.id}/edit`);
    },
    doAction() {
               this.$http
          .delete(`${employeeUrl}/${this.employeeId}`)
          .then(() => {
            this.showActionModal = false;

            setTimeout(() => {
                this.$router.push("/employees");
            }, 300);
          })
          .catch(() => {
            this.showActionModal = false;
            this.modalHeaderContent = "Uwaga";
            this.modalBodyContent = "Pracownik nie może zostać usunięty, upewnij się, czy nie brał udziału w jednej z usług salonu!";
            this.showInfoModal = true;
          });
    },
    deleteEmployee() {
        this.modalHeaderContent = "Uwaga";
        this.modalBodyContent = `Czy na pewno chcesz usunąć pracownika ?`;
        this.showActionModal = true;
    },
    backToList() {
      this.$router.go(-1);
    },
    getEmployee() {
      this.$http
        .get(`${employeeUrl}/${this.employeeId}`)
        .then(response => response.json())
        .then(object => {
          this.employee = object;
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
